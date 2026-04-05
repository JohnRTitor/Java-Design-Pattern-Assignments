package complex_number;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class ComplexNumberGUI extends JFrame {

	JTextArea textArea;
	JButton btnLoad, btnAdd;

	ArrayList<ComplexNumber> list = new ArrayList<>();

	public ComplexNumberGUI() {

		setTitle("Complex Number File Reader");
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		JScrollPane scroll = new JScrollPane(textArea);

		btnLoad = new JButton("Load Complex Numbers");
		btnAdd = new JButton("Add All Numbers");

		JPanel panel = new JPanel();
		panel.add(btnLoad);
		panel.add(btnAdd);

		add(scroll, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);

		btnLoad.addActionListener(e -> loadFromFile());
		btnAdd.addActionListener(e -> addAllNumbers());
	}

	// ----------- Load File Using JFileChooser -----------
	private void loadFromFile() {

		list.clear();
		textArea.setText("");

		JFileChooser fileChooser = new JFileChooser();

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {

			File selectedFile = fileChooser.getSelectedFile();

			try {
				BufferedReader br = new BufferedReader(new FileReader(selectedFile));
				String line;

				while ((line = br.readLine()) != null) {

					String[] parts = line.trim().split("\\s+");

					int real = Integer.parseInt(parts[0]);
					int img = Integer.parseInt(parts[1]);

					list.add(new ComplexNumber(real, img));
				}

				br.close();

				textArea.append("Complex Numbers from File:\n\n");

				for (ComplexNumber cn : list) {
					textArea.append(cn + "\n");
				}

			} catch (Exception e) {
				textArea.setText("Error reading file!");
			}

		} else {
			textArea.setText("File selection cancelled.");
		}
	}

	// ----------- Add All Numbers -----------
	private void addAllNumbers() {

		if (list.isEmpty()) {
			textArea.append("\nLoad numbers first!\n");
			return;
		}

		ComplexNumber sum = new ComplexNumber();

		for (ComplexNumber cn : list) {
			sum.add(cn);
		}

		textArea.append("\nSum of All Complex Numbers:\n");
		textArea.append(sum + "\n");
	}

	public static void main(String[] args) {
		new ComplexNumberGUI().setVisible(true);
	}
}
