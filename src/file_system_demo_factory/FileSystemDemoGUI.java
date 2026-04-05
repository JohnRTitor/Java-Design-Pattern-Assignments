package file_system_demo_factory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * GUI client that uses FileFactory to create files. Factory Pattern: client
 * delegates object creation to FileFactory.
 */
class FileSystemDemoGUI extends JFrame {

	private Directory root = new Directory("Root", "admin");

	private JPanel fileListPanel;
	private JLabel fileCountLabel;

	private final List<JCheckBox> checkBoxes = new ArrayList<>();
	private final List<FileSystemComponent> fileRefs = new ArrayList<>();

	private JTextArea statusArea;

	FileSystemDemoGUI() {
		setTitle("File System Simulator");
		setSize(860, 640);
		setMinimumSize(new Dimension(700, 500));
		setLayout(new BorderLayout(5, 5));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(buildTopPanel(), BorderLayout.NORTH);
		add(buildCenterPanel(), BorderLayout.CENTER);
		add(buildBottomPanel(), BorderLayout.SOUTH);

		setVisible(true);
	}

	// ── TOP: file creation ─────────────────────────────────────────────────
	private JPanel buildTopPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
		panel.setBorder(new TitledBorder("Add New File"));

		String[] types = { "Directory", "Text", "Multimedia" };
		JComboBox<String> typeBox = new JComboBox<>(types);
		JTextField nameField = new JTextField(10);
		JTextField sizeField = new JTextField(5);
		JTextField ownerField = new JTextField(7);
		ownerField.setText("root");
		JButton addBtn = new JButton("Add File");

		panel.add(new JLabel("Type:"));
		panel.add(typeBox);
		panel.add(new JLabel("Name:"));
		panel.add(nameField);
		panel.add(new JLabel("Size (KB):"));
		panel.add(sizeField);
		panel.add(new JLabel("Owner:"));
		panel.add(ownerField);
		panel.add(addBtn);

		addBtn.addActionListener(e -> {
			String type = (String) typeBox.getSelectedItem();
			String name = nameField.getText().trim();
			String owner = ownerField.getText().trim();

			if (name.isEmpty()) {
				setStatus("Error: File name cannot be empty.");
				return;
			}

			int size = 0;
			if (!type.equals("Directory")) {
				try {
					size = Integer.parseInt(sizeField.getText().trim());
					if (size < 0)
						throw new NumberFormatException();
				} catch (NumberFormatException ex) {
					setStatus("Error: Size must be a non-negative integer.");
					return;
				}
			}

			FileSystemComponent file = FileFactory.createFile(type, name, size, owner);
			root.add(file);
			addFileRow(file);
			updateFileCount();
			setStatus("✔ Added " + type + ": \"" + name + "\"" + (type.equals("Directory") ? "" : " (" + size + " KB)")
					+ "  Owner: " + owner);
			nameField.setText("");
			sizeField.setText("");
		});

		return panel;
	}

	// ── CENTER: file list with checkboxes ──────────────────────────────────
	private JPanel buildCenterPanel() {
		fileListPanel = new JPanel();
		fileListPanel.setLayout(new BoxLayout(fileListPanel, BoxLayout.Y_AXIS));
		fileListPanel.setBackground(Color.WHITE);

		JScrollPane scroll = new JScrollPane(fileListPanel);
		scroll.setBorder(null);

		fileCountLabel = new JLabel("  Total files: 0");
		fileCountLabel.setFont(fileCountLabel.getFont().deriveFont(Font.BOLD, 13f));
		fileCountLabel.setForeground(new Color(0, 80, 160));
		fileCountLabel.setBorder(new EmptyBorder(4, 6, 4, 0));

		statusArea = new JTextArea(2, 10);
		statusArea.setEditable(false);
		statusArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		statusArea.setBackground(new Color(245, 245, 245));
		statusArea.setBorder(new EmptyBorder(4, 6, 4, 6));

		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.setBorder(new TitledBorder("Files in Root"));
		wrapper.add(fileCountLabel, BorderLayout.NORTH);
		wrapper.add(scroll, BorderLayout.CENTER);
		wrapper.add(statusArea, BorderLayout.SOUTH);
		return wrapper;
	}

	// ── BOTTOM: actions on selected files ──────────────────────────────────
	private JPanel buildBottomPanel() {
		// Row 1: permissions
		JPanel permRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
		JCheckBox ownerReadCB = new JCheckBox("Owner Read", true);
		JCheckBox ownerWriteCB = new JCheckBox("Owner Write", true);
		JCheckBox otherReadCB = new JCheckBox("Other Read", false);
		JCheckBox otherWriteCB = new JCheckBox("Other Write", false);
		JButton permBtn = new JButton("Apply Permissions");

		permRow.add(new JLabel("Permissions:"));
		permRow.add(ownerReadCB);
		permRow.add(ownerWriteCB);
		permRow.add(otherReadCB);
		permRow.add(otherWriteCB);
		permRow.add(permBtn);

		// Row 2: selection + delete + show
		JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
		JButton selAllBtn = new JButton("Select All");
		JButton clrAllBtn = new JButton("Clear All");
		JButton deleteBtn = new JButton("Delete Selected");
		JButton showBtn = new JButton("Show Structure");
		deleteBtn.setForeground(Color.RED);

		actionRow.add(selAllBtn);
		actionRow.add(clrAllBtn);
		actionRow.add(deleteBtn);
		actionRow.add(showBtn);

		// Stack rows
		JPanel bottom = new JPanel(new GridLayout(2, 1, 0, 0));
		bottom.setBorder(new TitledBorder("Actions on Selected Files"));
		bottom.add(permRow);
		bottom.add(actionRow);

		// ── Listeners ──────────────────────────────────────────────────────
		permBtn.addActionListener(e -> {
			boolean oR = ownerReadCB.isSelected();
			boolean oW = ownerWriteCB.isSelected();
			boolean otR = otherReadCB.isSelected();
			boolean otW = otherWriteCB.isSelected();
			List<String> changed = new ArrayList<>();
			for (int i = 0; i < checkBoxes.size(); i++) {
				if (checkBoxes.get(i).isSelected()) {
					fileRefs.get(i).changePermission(oR, oW, otR, otW);
					changed.add(fileRefs.get(i).getName());
				}
			}
			setStatus(changed.isEmpty() ? "No files selected."
					: "✔ Permissions applied to: " + String.join(", ", changed) + "  Owner[" + (oR ? "r" : "-")
							+ (oW ? "w" : "-") + "] " + "Others[" + (otR ? "r" : "-") + (otW ? "w" : "-") + "]");
		});

		deleteBtn.addActionListener(e -> {
			List<String> deleted = new ArrayList<>();
			List<Integer> toRemove = new ArrayList<>();
			for (int i = 0; i < checkBoxes.size(); i++) {
				if (checkBoxes.get(i).isSelected()) {
					deleted.add(fileRefs.get(i).getName());
					root.remove(fileRefs.get(i).getName());
					toRemove.add(i);
				}
			}
			if (deleted.isEmpty()) {
				setStatus("No files selected.");
				return;
			}
			for (int i = toRemove.size() - 1; i >= 0; i--) {
				int idx = toRemove.get(i);
				fileListPanel.remove(idx);
				checkBoxes.remove(idx);
				fileRefs.remove(idx);
			}
			fileListPanel.revalidate();
			fileListPanel.repaint();
			updateFileCount();
			setStatus("✔ Deleted: " + String.join(", ", deleted));
		});

		showBtn.addActionListener(e -> {
			String s = root.display("");
			setStatus(s.isEmpty() ? "(Root is empty)" : s);
		});

		selAllBtn.addActionListener(e -> checkBoxes.forEach(cb -> cb.setSelected(true)));
		clrAllBtn.addActionListener(e -> checkBoxes.forEach(cb -> cb.setSelected(false)));

		return bottom;
	}

	// ── Helpers ────────────────────────────────────────────────────────────
	private void addFileRow(FileSystemComponent file) {
		String icon = switch (file.getType()) {
		case "Directory" -> "📁";
		case "Text" -> "📄";
		case "Multimedia" -> "🎬";
		default -> "📎";
		};
		String label = icon + "  " + file.getName() + "   [" + file.getType() + "]"
				+ (file.getType().equals("Directory") ? "" : "   " + file.getSize() + " KB") + "   Owner: "
				+ file.getOwnerId() + "   Created: " + file.getCreateDate();

		JCheckBox cb = new JCheckBox(label);
		cb.setBackground(Color.WHITE);
		cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, cb.getPreferredSize().height));
		checkBoxes.add(cb);
		fileRefs.add(file);
		fileListPanel.add(cb);
		fileListPanel.revalidate();
		fileListPanel.repaint();
	}

	private void updateFileCount() {
		fileCountLabel.setText("  Total files: " + fileRefs.size());
	}

	private void setStatus(String msg) {
		statusArea.setText(msg);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(FileSystemDemoGUI::new);
	}
}
