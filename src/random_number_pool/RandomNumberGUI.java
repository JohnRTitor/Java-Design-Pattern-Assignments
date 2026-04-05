package random_number_pool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RandomNumberGUI extends JFrame {

	private ArrayList<Integer> allNumbers = new ArrayList<>();
	private RandomNumberPool pool;
	private File currentFile = null;

	private JTextArea fileContentArea;
	private JTextArea poolDisplayArea;
	private JTextField poolSizeField;
	private JLabel statusLabel;
	private JLabel filePathLabel;

	public RandomNumberGUI() {
		setTitle("Random Number Pool Manager");
		setSize(850, 620);
		setMinimumSize(new Dimension(750, 520));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(8, 8));

		// ── TOP PANEL ──
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.setBorder(BorderFactory.createTitledBorder("Controls"));

		// Row 1: File controls
		JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
		JButton changeFileBtn = new JButton("Change File");
		row1.add(changeFileBtn);
		topPanel.add(row1);

		// Row 2: Pool controls
		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
		row2.add(new JLabel("Pool Size:"));
		poolSizeField = new JTextField("3", 4);
		row2.add(poolSizeField);
		JButton distributeBtn = new JButton("Distribute to Pool");
		JButton changePoolBtn = new JButton("Change Pool Size");
		row2.add(distributeBtn);
		row2.add(changePoolBtn);
		topPanel.add(row2);

		// Row 3: File path display
		JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
		row3.add(new JLabel("Current File:"));
		filePathLabel = new JLabel("No file selected");
		filePathLabel.setForeground(Color.GRAY);
		row3.add(filePathLabel);
		topPanel.add(row3);

		add(topPanel, BorderLayout.NORTH);

		// ── CENTER: Two text areas ──
		JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

		fileContentArea = new JTextArea();
		fileContentArea.setEditable(false);
		fileContentArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		JScrollPane leftScroll = new JScrollPane(fileContentArea);
		leftScroll.setBorder(BorderFactory.createTitledBorder("File Contents"));

		poolDisplayArea = new JTextArea();
		poolDisplayArea.setEditable(false);
		poolDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		JScrollPane rightScroll = new JScrollPane(poolDisplayArea);
		rightScroll.setBorder(BorderFactory.createTitledBorder("Pool Distribution"));

		centerPanel.add(leftScroll);
		centerPanel.add(rightScroll);
		add(centerPanel, BorderLayout.CENTER);

		// ── BOTTOM: Status ──
		statusLabel = new JLabel("  Select a file to get started.");
		statusLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(3, 6, 3, 6)));
		add(statusLabel, BorderLayout.SOUTH);

		// ── ACTIONS ──
		changeFileBtn.addActionListener(e -> changeFile());
		distributeBtn.addActionListener(e -> distributeToPool());
		changePoolBtn.addActionListener(e -> changePoolSize());

		setLocationRelativeTo(null);
		setVisible(true);
		promptOpenFile();
	}

	private void promptOpenFile() {
		int choice = JOptionPane.showConfirmDialog(this,
				"Please select a random_numbers.txt file to begin.\nClick Cancel to exit.", "Select File",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (choice == JOptionPane.OK_OPTION) {
			changeFile();
			if (currentFile == null) {
				JOptionPane.showMessageDialog(this, "No file selected. Exiting.");
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}

	private void changeFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select File");
		chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files (*.txt)", "txt"));
		if (currentFile != null)
			chooser.setCurrentDirectory(currentFile.getParentFile());
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			currentFile = chooser.getSelectedFile();
			loadFileContents(currentFile);
		}
	}

	private void loadFileContents(File file) {
		allNumbers.clear();
		StringBuilder sb = new StringBuilder();
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextInt()) {
				int num = scanner.nextInt();
				allNumbers.add(num);
				sb.append(num).append("\n");
			}
			fileContentArea.setText(sb.toString());
			poolDisplayArea.setText("");
			filePathLabel.setText(file.getAbsolutePath());
			filePathLabel.setForeground(new Color(0, 128, 0));
			statusLabel.setText("  Loaded " + allNumbers.size() + " numbers from: " + file.getName());
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(this, "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void distributeToPool() {
		if (allNumbers.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please select a file first!", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			int size = Integer.parseInt(poolSizeField.getText().trim());
			if (size <= 0)
				throw new NumberFormatException();
			pool = new RandomNumberPool(size);
			pool.distributeNumbers(allNumbers);
			displayPool();
			statusLabel.setText("  Distributed " + allNumbers.size() + " numbers into " + size + " pool objects.");
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Enter a valid pool size.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void changePoolSize() {
		if (pool == null || allNumbers.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Distribute to pool first!", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			int newSize = Integer.parseInt(poolSizeField.getText().trim());
			if (newSize <= 0)
				throw new NumberFormatException();
			pool.setPoolSize(newSize);
			pool.distributeNumbers(allNumbers);
			displayPool();
			statusLabel.setText("  Pool resized to " + newSize + ". Re-distributed " + allNumbers.size() + " numbers.");
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Enter a valid pool size.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void displayPool() {
		StringBuilder sb = new StringBuilder();
		ArrayList<RandomNumber> list = pool.getPool();
		for (int i = 0; i < list.size(); i++) {
			sb.append("── RandomNumber Object #").append(i + 1).append(" ──\n");
			for (int num : list.get(i).getNumbers())
				sb.append("   ").append(num).append("\n");
			sb.append("\n");
		}
		poolDisplayArea.setText(sb.toString());
		poolDisplayArea.setCaretPosition(0);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(RandomNumberGUI::new);
	}
}