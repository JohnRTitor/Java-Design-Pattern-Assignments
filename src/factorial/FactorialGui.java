package factorial;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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

class FactorialGui extends JFrame {

	private ArrayList<Integer> numbers = new ArrayList<>();
	private JTextField indexField;
	private JTextArea outputArea;
	private JLabel fileLabel;

	FactorialGui() {
		setTitle("Factorial Calculator");
		setSize(400, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// ── Top Panel ───────────────────────────
		JPanel top = new JPanel(new GridLayout(4, 1, 4, 4));
		top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		fileLabel = new JLabel("No file selected");
		top.add(fileLabel);

		top.add(new JLabel("Enter index i:"));

		indexField = new JTextField();
		top.add(indexField);

		JButton calcBtn = new JButton("Calculate Factorial");
		top.add(calcBtn);

		add(top, BorderLayout.NORTH);

		// ── Center ──────────────────────────────
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		add(new JScrollPane(outputArea), BorderLayout.CENTER);

		calcBtn.addActionListener(e -> calculate());

		setLocationRelativeTo(null);
		setVisible(true);

		loadNumbers();
	}

	private void loadNumbers() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select numbers.txt file");

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			try (BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {

				numbers.clear(); // clear old data if reloaded
				String line;

				while ((line = br.readLine()) != null) {
					line = line.trim();
					if (!line.isEmpty())
						numbers.add(Integer.parseInt(line));
				}

				fileLabel.setText("Loaded: " + fileChooser.getSelectedFile().getName() + " -> " + numbers.toString());

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(this, "No file selected. Please restart and choose a file.");
		}
	}

	private void calculate() {
		try {
			int i = Integer.parseInt(indexField.getText().trim());
			if (i < 1 || i > numbers.size()) {
				outputArea.append("Index out of range. Use 1 to " + numbers.size() + ".\n");
				return;
			}
			int n = numbers.get(i - 1);
			FactUtil util = FactUtil.getInstance();
			int result = util.factorial(n);
			outputArea.append("i=" + i + "  n=" + n + "  factorial(" + n + ") = " + result + "\n");
		} catch (NumberFormatException ex) {
			outputArea.append("Please enter a valid integer.\n");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(FactorialGui::new);
	}
}
