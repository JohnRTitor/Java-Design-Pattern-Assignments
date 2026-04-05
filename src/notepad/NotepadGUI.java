package notepad;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

class NotepadGUI extends JFrame implements ActionListener {

	private JTextArea textArea;
	private File currentFile;

	public NotepadGUI() {
		setTitle("Notepad");
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
	}

	public void initComponents() {
		initMenu();
		initTextArea();
		promptOpenFileOnFirstRun();
	}

	/**
	 * On first run, ask the user if they'd like to open an existing file.
	 */
	private void promptOpenFileOnFirstRun() {
		int choice = JOptionPane.showConfirmDialog(this, "Would you like to open an existing file?", "Open File",
				JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			openFile();
		}
	}

	public void initMenu() {
		JMenuBar mb = new JMenuBar();

		JMenu fileMn = new JMenu("File");

		JMenuItem fileNewItem = new JMenuItem("New");
		JMenuItem fileNewWindowItem = new JMenuItem("New Window");
		JMenuItem fileOpenItem = new JMenuItem("Open...");
		JMenuItem fileSaveItem = new JMenuItem("Save");
		JMenuItem fileSaveAsItem = new JMenuItem("Save as...");
		JMenuItem filePageSetupItem = new JMenuItem("Page Setup");
		JMenuItem filePrintItem = new JMenuItem("Print");
		JMenuItem fileExitItem = new JMenuItem("Exit");

		fileNewItem.addActionListener(this);
		fileNewWindowItem.addActionListener(this);
		fileOpenItem.addActionListener(this);
		fileSaveItem.addActionListener(this);
		fileSaveAsItem.addActionListener(this);
		fileExitItem.addActionListener(this);

		fileMn.add(fileNewItem);
		fileMn.add(fileNewWindowItem);
		fileMn.add(fileOpenItem);
		fileMn.add(fileSaveItem);
		fileMn.add(fileSaveAsItem);
		fileMn.add(filePageSetupItem);
		fileMn.add(filePrintItem);
		fileMn.add(fileExitItem);

		JMenu editMn = new JMenu("Edit");

		JMenuItem editUndoItem = new JMenuItem("Undo");
		JMenuItem editCutItem = new JMenuItem("Cut");
		JMenuItem editCopyItem = new JMenuItem("Copy");
		JMenuItem editPasteItem = new JMenuItem("Paste");
		JMenuItem editDeleteItem = new JMenuItem("Delete");
		JMenuItem editSearchWithBingItem = new JMenuItem("Search with Bing");
		JMenuItem editFindItem = new JMenuItem("Find");
		JMenuItem editFindNextItem = new JMenuItem("Find Next");
		JMenuItem editFindPrevItem = new JMenuItem("Find Previous");
		JMenuItem editReplaceItem = new JMenuItem("Replace");
		JMenuItem editGoToItem = new JMenuItem("Go to");
		JMenuItem editSelectAllItem = new JMenuItem("Select All");
		JMenuItem editTimeDateItem = new JMenuItem("Time/Date");

		editMn.add(editUndoItem);
		editMn.add(editCutItem);
		editMn.add(editCopyItem);
		editMn.add(editPasteItem);
		editMn.add(editDeleteItem);
		editMn.add(editSearchWithBingItem);
		editMn.add(editFindItem);
		editMn.add(editFindNextItem);
		editMn.add(editFindPrevItem);
		editMn.add(editReplaceItem);
		editMn.add(editGoToItem);
		editMn.add(editSelectAllItem);
		editMn.add(editTimeDateItem);

		JMenu formatMn = new JMenu("Format");

		JMenuItem formatWordWrapItem = new JMenuItem("Word Wrap");
		JMenuItem formatFontItem = new JMenuItem("Font...");

		formatMn.add(formatWordWrapItem);
		formatMn.add(formatFontItem);

		JMenu viewMn = new JMenu("View");

		JMenu viewZoomSubMenu = new JMenu("Zoom");
		JMenuItem zoomInItem = new JMenuItem("Zoom In");
		JMenuItem zoomOutItem = new JMenuItem("Zoom Out");
		JMenuItem restoreDefaultItem = new JMenuItem("Restore Default Zoom");
		viewZoomSubMenu.add(zoomInItem);
		viewZoomSubMenu.add(zoomOutItem);
		viewZoomSubMenu.add(restoreDefaultItem);

		JMenuItem statusBarItem = new JMenuItem("Status Bar");

		viewMn.add(viewZoomSubMenu);
		viewMn.add(statusBarItem);

		JMenu helpMn = new JMenu("Help");

		JMenuItem viewHelpItem = new JMenuItem("View Help");
		JMenuItem sendFeedbackItem = new JMenuItem("Send Feedback");
		JMenuItem aboutNotepadItem = new JMenuItem("About Notepad");

		helpMn.add(viewHelpItem);
		helpMn.add(sendFeedbackItem);
		helpMn.add(aboutNotepadItem);

		mb.add(fileMn);
		mb.add(editMn);
		mb.add(formatMn);
		mb.add(viewMn);
		mb.add(helpMn);

		add(mb, BorderLayout.NORTH);
	}

	public void initTextArea() {
		textArea = new JTextArea();
		add(new JScrollPane(textArea));
	}

	// ──────────────────────────────────────────────
	// File operations
	// ──────────────────────────────────────────────

	private void newFile() {
		if (!confirmDiscardChanges())
			return;
		textArea.setText("");
		currentFile = null;
		setTitle("Notepad");
	}

	private void openFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Open File");
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				String content = new String(Files.readAllBytes(file.toPath()));
				textArea.setText(content);
				currentFile = file;
				setTitle("Notepad - " + file.getName());
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Failed to open file:\n" + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void saveFile() {
		if (currentFile == null) {
			saveFileAs();
		} else {
			writeToFile(currentFile);
		}
	}

	private void saveFileAs() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Save As");
		if (currentFile != null) {
			chooser.setSelectedFile(currentFile);
		}
		int result = chooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			// Append .txt if no extension provided
			if (!file.getName().contains(".")) {
				file = new File(file.getAbsolutePath() + ".txt");
			}
			// Warn before overwriting
			if (file.exists()) {
				int confirm = JOptionPane.showConfirmDialog(this,
						file.getName() + " already exists.\nDo you want to replace it?", "Confirm Save As",
						JOptionPane.YES_NO_OPTION);
				if (confirm != JOptionPane.YES_OPTION)
					return;
			}
			writeToFile(file);
			currentFile = file;
		}
	}

	private void writeToFile(File file) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(textArea.getText());
			setTitle("Notepad - " + file.getName());
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Failed to save file:\n" + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Returns true if it's safe to proceed (user confirmed or no unsaved content).
	 */
	private boolean confirmDiscardChanges() {
		if (textArea.getText().isEmpty())
			return true;
		int choice = JOptionPane.showConfirmDialog(this, "Do you want to save changes?", "Notepad",
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			saveFile();
			return true;
		}
		return choice == JOptionPane.NO_OPTION;
	}

	// ──────────────────────────────────────────────

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		switch (cmd) {
		case "New":
			newFile();
			break;
		case "New Window":
			new NotepadGUI().setVisible(true);
			break;
		case "Open...":
			openFile();
			break;
		case "Save":
			saveFile();
			break;
		case "Save as...":
			saveFileAs();
			break;
		case "Exit":
			if (confirmDiscardChanges())
				System.exit(0);
			break;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			NotepadGUI n1 = new NotepadGUI();
			n1.setVisible(true);
		});
	}
}