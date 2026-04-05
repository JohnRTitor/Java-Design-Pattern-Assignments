package employee_without_pattern;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

class EmployeeSystemGUI extends JFrame {

	private JTable table;
	private DefaultTableModel model;
	private File currentFile;

	// Column indices
	private static final int COL_NAME = 0;
	private static final int COL_AGE = 1;
	private static final int COL_ADDRESS = 2;
	private static final int COL_COMPANY = 3;
	private static final int COL_DEPARTMENT = 4;
	private static final int COL_SALARY = 5;
	private static final int COL_ACTION = 6;

	public EmployeeSystemGUI() {
		setTitle("Employee Record Manager");
		setSize(1000, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		initComponents();
	}

	private void initComponents() {
		// --- Top panel: file ops ---
		JPanel top = new JPanel();
		JButton changeFile = new JButton("Change File");
		JButton save = new JButton("Save");
		top.add(changeFile);
		top.add(save);
		add(top, BorderLayout.NORTH);

		// --- Table ---
		model = new DefaultTableModel(
				new String[] { "Name", "Age", "Address", "Company", "Department", "Salary", "Action" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return c == COL_ACTION;
			}
		};

		table = new JTable(model);
		table.getColumn("Action").setCellRenderer(new ActionRenderer());
		table.getColumn("Action").setCellEditor(new ActionEditor());
		table.getColumn("Action").setPreferredWidth(160);

		add(new JScrollPane(table), BorderLayout.CENTER);

		// --- Bottom panel: add + stats ---
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JButton addBtn = new JButton("Add Employee");
		JButton promoteBtn = new JButton("Promote Employee");
		JButton totalSalaryBtn = new JButton("Total Salary");
		JButton countBtn = new JButton("Employee Count");

		bottom.add(addBtn);
		bottom.add(promoteBtn);
		bottom.add(totalSalaryBtn);
		bottom.add(countBtn);
		add(bottom, BorderLayout.SOUTH);

		// --- Listeners ---
		changeFile.addActionListener(e -> chooseFile());
		save.addActionListener(e -> saveToFile());
		addBtn.addActionListener(e -> openAddDialog());
		promoteBtn.addActionListener(e -> promoteDialog());
		totalSalaryBtn.addActionListener(e -> totalSalaryDialog());
		countBtn.addActionListener(e -> countDialog());

		chooseFile();
	}

	// ---- File Operations ----

	private void chooseFile() {
		JFileChooser fc = new JFileChooser();
		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			currentFile = fc.getSelectedFile();
			loadFromFile();
		}
	}

	private void loadFromFile() {
		model.setRowCount(0);
		if (currentFile == null || !currentFile.exists())
			return;
		try (BufferedReader br = new BufferedReader(new FileReader(currentFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty())
					continue;
				Employee emp = Employee.fromFileString(line);
				model.addRow(new Object[] { emp.name, emp.age, emp.address, emp.company, emp.department, emp.salary,
						"Delete" });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage());
		}
	}

	private void saveToFile() {
		if (currentFile == null)
			return;

		try (PrintWriter pw = new PrintWriter(new FileWriter(currentFile))) {

			for (int i = 0; i < model.getRowCount(); i++) {

				String name = model.getValueAt(i, COL_NAME).toString();
				int age = Integer.parseInt(model.getValueAt(i, COL_AGE).toString());
				String address = model.getValueAt(i, COL_ADDRESS).toString();
				String company = model.getValueAt(i, COL_COMPANY).toString();
				String department = model.getValueAt(i, COL_DEPARTMENT).toString();
				double salary = Double.parseDouble(model.getValueAt(i, COL_SALARY).toString());

				Employee emp = new Employee(name, age, address, company, department, salary);
				pw.println(emp.toFileString());
			}

			JOptionPane.showMessageDialog(this, "Saved successfully.");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Save error: " + e.getMessage());
		}
	}

	// ---- Add Employee ----

	private void openAddDialog() {
		JTextField name = new JTextField();
		JTextField age = new JTextField();
		JTextField address = new JTextField();
		JTextField company = new JTextField();
		JTextField department = new JTextField();
		JTextField salary = new JTextField();

		Object[] msg = { "Name:", name, "Age:", age, "Address:", address, "Company:", company, "Department:",
				department, "Salary:", salary };

		if (JOptionPane.showConfirmDialog(this, msg, "Add Employee",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			try {
				double sal = salary.getText().trim().isEmpty() ? 0 : Double.parseDouble(salary.getText().trim());
				int ageVal = Integer.parseInt(age.getText().trim());

				model.addRow(new Object[] { name.getText(), ageVal, address.getText(), company.getText(),
						department.getText(), sal, "Delete" });

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid salary value.");
			}
		}
	}

	// ---- Promote Employee ----

	private void promoteDialog() {
		if (model.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "No employees to promote.");
			return;
		}

		// Build list of names for selection
		String[] names = new String[model.getRowCount()];
		for (int i = 0; i < model.getRowCount(); i++) {
			names[i] = model.getValueAt(i, COL_NAME) + " (" + model.getValueAt(i, COL_DEPARTMENT) + ", "
					+ model.getValueAt(i, COL_COMPANY) + ")";
		}

		JComboBox<String> combo = new JComboBox<>(names);
		JTextField newDept = new JTextField();
		JTextField raiseField = new JTextField("0");

		Object[] msg = { "Select Employee:", combo, "New Department (leave blank to keep):", newDept,
				"Salary Raise Amount:", raiseField };

		if (JOptionPane.showConfirmDialog(this, msg, "Promote Employee",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			int row = combo.getSelectedIndex();
			try {
				double raise = Double.parseDouble(raiseField.getText().trim());
				double current = Double.parseDouble(model.getValueAt(row, COL_SALARY).toString());
				model.setValueAt(current + raise, row, COL_SALARY);

				String dept = newDept.getText().trim();
				if (!dept.isEmpty()) {
					model.setValueAt(dept, row, COL_DEPARTMENT);
				}

				JOptionPane.showMessageDialog(this, "Employee promoted!\nNew Salary: " + (current + raise)
						+ (dept.isEmpty() ? "" : "\nNew Department: " + dept));
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid raise amount.");
			}
		}
	}

	// ---- Total Salary ----

	private void totalSalaryDialog() {
		JTextField companyField = new JTextField();
		JTextField deptField = new JTextField();
		Object[] msg = { "Company (leave blank for all):", companyField, "Department (leave blank for all):",
				deptField };

		if (JOptionPane.showConfirmDialog(this, msg, "Total Salary",
				JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
			return;

		String filterCompany = companyField.getText().trim().toLowerCase();
		String filterDept = deptField.getText().trim().toLowerCase();

		double total = 0;
		int count = 0;
		for (int i = 0; i < model.getRowCount(); i++) {
			String comp = model.getValueAt(i, COL_COMPANY).toString().toLowerCase();
			String dept = model.getValueAt(i, COL_DEPARTMENT).toString().toLowerCase();
			boolean matchComp = filterCompany.isEmpty() || comp.equals(filterCompany);
			boolean matchDept = filterDept.isEmpty() || dept.equals(filterDept);
			if (matchComp && matchDept) {
				total += Double.parseDouble(model.getValueAt(i, COL_SALARY).toString());
				count++;
			}
		}

		String label = buildFilterLabel(filterCompany, filterDept);
		JOptionPane.showMessageDialog(this,
				"Total Salary" + label + ": " + String.format("%.2f", total) + "\n(Across " + count + " employee(s))");
	}

	// ---- Employee Count ----

	private void countDialog() {
		JTextField companyField = new JTextField();
		JTextField deptField = new JTextField();
		Object[] msg = { "Company (leave blank for all):", companyField, "Department (leave blank for all):",
				deptField };

		if (JOptionPane.showConfirmDialog(this, msg, "Employee Count",
				JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
			return;

		String filterCompany = companyField.getText().trim().toLowerCase();
		String filterDept = deptField.getText().trim().toLowerCase();

		int count = 0;
		for (int i = 0; i < model.getRowCount(); i++) {
			String comp = model.getValueAt(i, COL_COMPANY).toString().toLowerCase();
			String dept = model.getValueAt(i, COL_DEPARTMENT).toString().toLowerCase();
			boolean matchComp = filterCompany.isEmpty() || comp.equals(filterCompany);
			boolean matchDept = filterDept.isEmpty() || dept.equals(filterDept);
			if (matchComp && matchDept)
				count++;
		}

		String label = buildFilterLabel(filterCompany, filterDept);
		JOptionPane.showMessageDialog(this, "Total Employees" + label + ": " + count);
	}

	private String buildFilterLabel(String company, String dept) {
		if (!company.isEmpty() && !dept.isEmpty())
			return " in " + company + " / " + dept;
		if (!company.isEmpty())
			return " in company '" + company + "'";
		if (!dept.isEmpty())
			return " in department '" + dept + "'";
		return " (all)";
	}

	// ---- Table Button Renderer & Editor ----

	class ActionRenderer extends JPanel implements TableCellRenderer {
		ActionRenderer() {
			setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
			add(new JButton("Delete"));
		}

		public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
			return this;
		}
	}

	class ActionEditor extends DefaultCellEditor {
		private JPanel panel;
		private int row;

		ActionEditor() {
			super(new JTextField());
			panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
			JButton del = new JButton("Delete");
			panel.add(del);
			del.addActionListener(e -> {
				model.removeRow(row);
				fireEditingStopped();
			});
		}

		public Component getTableCellEditorComponent(JTable t, Object v, boolean s, int r, int c) {
			row = r;
			return panel;
		}

		public Object getCellEditorValue() {
			return "Delete";
		}
	}

	public static void main(String[] args) {
		EmployeeSystemGUI frame = new EmployeeSystemGUI();
		frame.setVisible(true);
	}
}
