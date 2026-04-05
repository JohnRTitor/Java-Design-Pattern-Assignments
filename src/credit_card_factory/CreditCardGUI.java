package credit_card_factory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class CreditCardGUI extends JFrame {

	private DefaultTableModel tableModel;
	private JTextField numberField, expiryField, holderField;
	private JLabel statusLabel;

	public CreditCardGUI() {
		setTitle("Credit Card Validator");
		setSize(760, 520);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(4, 4));

		add(buildInputPanel(), BorderLayout.NORTH);
		add(buildTable(), BorderLayout.CENTER);
		add(buildStatusBar(), BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		setVisible(true);

		// Prompt user to load a file on startup
		loadFromFile();
	}

	// ── Top: manual entry + file load ─────────────────────────────────────────
	private JPanel buildInputPanel() {
		JPanel p = new JPanel(new GridLayout(2, 1, 2, 2));

		// Row 1: manual entry fields
		JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
		row1.setBorder(BorderFactory.createTitledBorder("Manual Entry"));
		numberField = new JTextField(18);
		expiryField = new JTextField(6);
		holderField = new JTextField(14);
		JButton validateBtn = new JButton("Validate & Add");

		row1.add(new JLabel("Card No:"));
		row1.add(numberField);
		row1.add(new JLabel("Expiry:"));
		row1.add(expiryField);
		row1.add(new JLabel("Holder:"));
		row1.add(holderField);
		row1.add(validateBtn);

		// Row 2: file controls
		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
		row2.setBorder(BorderFactory.createTitledBorder("File"));
		JButton loadBtn = new JButton("Load from File...");
		JButton clearBtn = new JButton("Clear Table");
		row2.add(loadBtn);
		row2.add(clearBtn);

		p.add(row1);
		p.add(row2);

		validateBtn.addActionListener(e -> validateManual());
		loadBtn.addActionListener(e -> loadFromFile());
		clearBtn.addActionListener(e -> {
			tableModel.setRowCount(0);
			statusLabel.setText("Cleared.");
		});

		return p;
	}

	// ── Center: results table ─────────────────────────────────────────────────
	private JScrollPane buildTable() {
		String[] cols = { "Status", "Card Type", "Card Number", "Expiry", "Holder" };
		tableModel = new DefaultTableModel(cols, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		JTable table = new JTable(tableModel);
		table.setRowHeight(22);
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
		table.setFont(new Font("Monospaced", Font.PLAIN, 12));
		return new JScrollPane(table);
	}

	// ── Bottom: status bar ────────────────────────────────────────────────────
	private JPanel buildStatusBar() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusLabel = new JLabel("Ready.");
		p.add(statusLabel);
		return p;
	}

	// ── Load from file using JFileChooser ─────────────────────────────────────
	private void loadFromFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Card Data File");
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files (*.txt)", "txt"));

		int result = fileChooser.showOpenDialog(this);
		if (result != JFileChooser.APPROVE_OPTION) {
			statusLabel.setText("File selection cancelled.");
			return;
		}

		File selectedFile = fileChooser.getSelectedFile();
		tableModel.setRowCount(0);

		List<CardFileReader.Result> results = CardFileReader.readFile(selectedFile.getAbsolutePath());
		int valid = 0, invalid = 0;
		for (CardFileReader.Result r : results) {
			if (r.isValid()) {
				CreditCard c = r.card;
				tableModel.addRow(new Object[] { "VALID", c.getCardType(), c.getCardNumber(), c.getExpiryDate(),
						c.getHolderName() });
				valid++;
			} else {
				tableModel.addRow(new Object[] { "INVALID", "-", r.raw, "-", "-" });
				invalid++;
			}
		}
		statusLabel.setText("Loaded " + results.size() + " records from \"" + selectedFile.getName() + "\" | Valid: "
				+ valid + " | Invalid: " + invalid);
	}

	// ── Manual validation ─────────────────────────────────────────────────────
	private void validateManual() {
		String number = numberField.getText().trim();
		String expiry = expiryField.getText().trim();
		String holder = holderField.getText().trim();
		if (number.isEmpty()) {
			statusLabel.setText("Enter a card number.");
			return;
		}

		CreditCard card = CreditCardFactory.create(number, expiry, holder);
		if (card != null) {
			tableModel.addRow(new Object[] { "VALID", card.getCardType(), card.getCardNumber(), card.getExpiryDate(),
					card.getHolderName() });
			statusLabel.setText("Added: " + card.getCardType());
		} else {
			tableModel.addRow(new Object[] { "INVALID", "-", number, expiry, holder });
			statusLabel.setText("Invalid card number: " + number);
		}
		numberField.setText("");
		expiryField.setText("");
		holderField.setText("");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(CreditCardGUI::new);
	}
}
