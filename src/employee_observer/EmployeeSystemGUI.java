package employee_observer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.List;

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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * Concrete Observer.
 *
 * Registers itself with EmployeeRepository (the Subject). When data changes the
 * repository calls onEmployeesChanged() and the table refreshes automatically —
 * the GUI never touches the employee list directly.
 */
class EmployeeSystemGUI extends JFrame implements EmployeeObserver {

	private JTable table;
	private DefaultTableModel model;

	private static final int COL_NAME = 0;
	private static final int COL_AGE = 1;
	private static final int COL_ADDRESS = 2;
	private static final int COL_COMPANY = 3;
	private static final int COL_DEPARTMENT = 4;
	private static final int COL_SALARY = 5;
	private static final int COL_ACTION = 6;

	private final EmployeeRepository repository = new EmployeeRepository();

	public EmployeeSystemGUI() {
		setTitle("Employee Record Manager");
		setSize(1000, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		repository.addObserver(this); // subscribe to the subject

		initComponents();
	}

	// ── EmployeeObserver ──────────────────────────────────────────────────────

	@Override
	public void onEmployeesChanged(List<Employee> employees) {
		model.setRowCount(0);
		for (Employee emp : employees) {
			model.addRow(
					new Object[] { emp.name, emp.age, emp.address, emp.company, emp.department, emp.salary, "Delete" });
		}
	}

	@Override
	public void onStatusMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	// ── UI construction ───────────────────────────────────────────────────────

	private void initComponents() {
		JPanel top = new JPanel();
		JButton changeFile = new JButton("Change File");
		JButton save = new JButton("Save");
		top.add(changeFile);
		top.add(save);
		add(top, BorderLayout.NORTH);

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

		changeFile.addActionListener(e -> chooseFile());
		save.addActionListener(e -> repository.saveToFile());
		addBtn.addActionListener(e -> openAddDialog());
		promoteBtn.addActionListener(e -> promoteDialog());
		totalSalaryBtn.addActionListener(e -> totalSalaryDialog());
		countBtn.addActionListener(e -> countDialog());

		chooseFile();
	}

	// ── File chooser ──────────────────────────────────────────────────────────

	private void chooseFile() {
		JFileChooser fc = new JFileChooser();
		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			repository.setFile(fc.getSelectedFile());
	}

	// ── Add Employee ──────────────────────────────────────────────────────────

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

				repository.addEmployee(new Employee(name.getText().trim(), ageVal, address.getText().trim(),
						company.getText().trim(), department.getText().trim(), sal));

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid age or salary value.");
			}
		}
	}

	// ── Promote Employee ──────────────────────────────────────────────────────

	private void promoteDialog() {
		List<Employee> all = repository.getAll();
		if (all.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No employees to promote.");
			return;
		}

		String[] names = new String[all.size()];
		for (int i = 0; i < all.size(); i++) {
			Employee e = all.get(i);
			names[i] = e.name + " (" + e.department + ", " + e.company + ")";
		}

		JComboBox<String> combo = new JComboBox<>(names);
		JTextField newDept = new JTextField();
		JTextField raiseField = new JTextField("0");

		Object[] msg = { "Select Employee:", combo, "New Department (leave blank to keep):", newDept,
				"Salary Raise Amount:", raiseField };

		if (JOptionPane.showConfirmDialog(this, msg, "Promote Employee",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			try {
				double raise = Double.parseDouble(raiseField.getText().trim());
				repository.promoteEmployee(combo.getSelectedIndex(), newDept.getText().trim(), raise);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Invalid raise amount.");
			}
		}
	}

	// ── Total Salary ──────────────────────────────────────────────────────────

	private void totalSalaryDialog() {
		JTextField companyField = new JTextField();
		JTextField deptField = new JTextField();
		Object[] msg = { "Company (leave blank for all):", companyField, "Department (leave blank for all):",
				deptField };
		if (JOptionPane.showConfirmDialog(this, msg, "Total Salary",
				JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
			return;

		String filterCompany = companyField.getText().trim();
		String filterDept = deptField.getText().trim();
		double total = repository.totalSalary(filterCompany, filterDept);
		long count = repository.count(filterCompany, filterDept);
		String label = buildFilterLabel(filterCompany, filterDept);

		JOptionPane.showMessageDialog(this,
				"Total Salary" + label + ": " + String.format("%.2f", total) + "\n(Across " + count + " employee(s))");
	}

	// ── Employee Count ────────────────────────────────────────────────────────

	private void countDialog() {
		JTextField companyField = new JTextField();
		JTextField deptField = new JTextField();
		Object[] msg = { "Company (leave blank for all):", companyField, "Department (leave blank for all):",
				deptField };
		if (JOptionPane.showConfirmDialog(this, msg, "Employee Count",
				JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
			return;

		String filterCompany = companyField.getText().trim();
		String filterDept = deptField.getText().trim();
		long count = repository.count(filterCompany, filterDept);
		String label = buildFilterLabel(filterCompany, filterDept);

		JOptionPane.showMessageDialog(this, "Total Employees" + label + ": " + count);
	}

	// ── Helpers ───────────────────────────────────────────────────────────────

	private String buildFilterLabel(String company, String dept) {
		if (!company.isEmpty() && !dept.isEmpty())
			return " in " + company + " / " + dept;
		if (!company.isEmpty())
			return " in company '" + company + "'";
		if (!dept.isEmpty())
			return " in department '" + dept + "'";
		return " (all)";
	}

	// ── Table Button Renderer & Editor ────────────────────────────────────────

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
				repository.deleteEmployee(row);
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

	// ── Entry point ───────────────────────────────────────────────────────────

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new EmployeeSystemGUI().setVisible(true));
	}
}