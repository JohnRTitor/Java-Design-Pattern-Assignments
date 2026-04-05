package studentdao_result_singleton;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ResultQueryGUI extends JFrame {

	private static final int[] VALID_ROLLS = { 101, 102, 103, 104, 105, 106, 107, 108, 109, 110 };
	private static final Random RNG = new Random();

	private DefaultTableModel tableModel;
	private JLabel statusLabel;
	private int queryCount = 0;

	public ResultQueryGUI() {

		// 🔹 Choose file using JFileChooser
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select ResultDB.txt");

		int result = chooser.showOpenDialog(this);

		if (result != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(this, "No file selected. Application will exit.");
			System.exit(0);
		}

		File selectedFile = chooser.getSelectedFile();

		// 🔹 Initialize DAO with selected file
		StudentDAO.initialize(selectedFile.getAbsolutePath());

		setTitle("Student Result Query");
		setSize(700, 450);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Top Panel
		JPanel topPanel = new JPanel();
		JButton run1 = new JButton("Run 1 Query");
		JButton run5 = new JButton("Run 5 Queries");
		JButton run12 = new JButton("Run 12 Queries");
		JButton clear = new JButton("Clear");

		topPanel.add(run1);
		topPanel.add(run5);
		topPanel.add(run12);
		topPanel.add(clear);

		add(topPanel, BorderLayout.NORTH);

		// Table
		String[] columns = { "#", "DAO Instance", "Roll No", "Marks" };
		tableModel = new DefaultTableModel(columns, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		JTable table = new JTable(tableModel);
		add(new JScrollPane(table), BorderLayout.CENTER);

		// Status Label
		statusLabel = new JLabel("Total queries: 0");
		add(statusLabel, BorderLayout.SOUTH);

		// Button Actions
		run1.addActionListener(e -> runQueries(1));
		run5.addActionListener(e -> runQueries(5));
		run12.addActionListener(e -> runQueries(12));

		clear.addActionListener(e -> {
			tableModel.setRowCount(0);
			queryCount = 0;
			statusLabel.setText("Total queries: 0");
		});

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void runQueries(int n) {
		for (int i = 0; i < n; i++) {
			queryCount++;

			StudentDAO dao = StudentDAO.getInstance();

			int rollNo = VALID_ROLLS[RNG.nextInt(VALID_ROLLS.length)];
			int marks = dao.getMarks(rollNo);

			tableModel.addRow(new Object[] { queryCount, dao.toString(), rollNo, marks == -1 ? "Not Found" : marks });
		}

		statusLabel.setText("Total queries: " + queryCount);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(ResultQueryGUI::new);
	}
}
