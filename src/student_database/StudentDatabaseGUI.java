package student_database;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

public class StudentDatabaseGUI extends JFrame {

    // ── StudentTb ──────────────────────────────────────────────────────────────
    private JTable studentTable;
    private DefaultTableModel studentModel;
    private File studentFile;

    // ── MarksTb ────────────────────────────────────────────────────────────────
    private JTable marksTable;
    private DefaultTableModel marksModel;
    private File marksFile;

    // Column indices – Student
    private static final int S_ROLL = 0;
    private static final int S_NAME = 1;
    private static final int S_ACTION = 2;

    // Column indices – Marks
    private static final int M_ROLL = 0;
    private static final int M_MARKS = 1;
    private static final int M_ACTION = 2;

    // ──────────────────────────────────────────────────────────────────────────
    public StudentDatabaseGUI() {
        setTitle("Student Database Manager");
        setSize(1050, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        buildUI();
    }

    // ── UI Construction ────────────────────────────────────────────────────────
    private void buildUI() {

        /* ── File bar (top) ── */
        JPanel fileBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        fileBar.setBorder(BorderFactory.createTitledBorder("File Operations"));

        JButton openStudent = new JButton("Open StudentTb File");
        JButton saveStudent = new JButton("Save StudentTb");
        JButton openMarks   = new JButton("Open MarksTb File");
        JButton saveMarks   = new JButton("Save MarksTb");

        fileBar.add(openStudent);
        fileBar.add(saveStudent);
        fileBar.add(new JSeparator(SwingConstants.VERTICAL));
        fileBar.add(openMarks);
        fileBar.add(saveMarks);
        add(fileBar, BorderLayout.NORTH);

        /* ── Split pane: two tables side-by-side ── */
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildStudentPanel(), buildMarksPanel());
        split.setResizeWeight(0.5);
        add(split, BorderLayout.CENTER);

        /* ── Menu bar (bottom) – two rows so nothing gets cut off ── */
        JButton insertStudent = new JButton("(a) Insert Student");
        JButton insertMarks   = new JButton("(b) Insert Marks");
        JButton searchName    = new JButton("(c) Search Name by RollNo");
        JButton searchMarks   = new JButton("(d) Search Marks by RollNo");
        JButton dispStudent   = new JButton("(e) Display StudentTb");
        JButton dispMarks     = new JButton("(f) Display MarksTb");
        JButton dispDetails   = new JButton("(g) Student Details");
        JButton dispResult    = new JButton("(h) Full Result");

        JPanel row1 = new JPanel(new GridLayout(1, 4, 6, 0));
        row1.add(insertStudent);
        row1.add(insertMarks);
        row1.add(searchName);
        row1.add(searchMarks);

        JPanel row2 = new JPanel(new GridLayout(1, 4, 6, 0));
        row2.add(dispStudent);
        row2.add(dispMarks);
        row2.add(dispDetails);
        row2.add(dispResult);

        JPanel menuBar = new JPanel(new GridLayout(2, 1, 0, 4));
        menuBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Menu"),
                BorderFactory.createEmptyBorder(2, 4, 4, 4)));
        menuBar.add(row1);
        menuBar.add(row2);
        add(menuBar, BorderLayout.SOUTH);

        /* ── Listeners ── */
        openStudent.addActionListener(e -> chooseFile("student"));
        saveStudent.addActionListener(e -> saveFile("student"));
        openMarks  .addActionListener(e -> chooseFile("marks"));
        saveMarks  .addActionListener(e -> saveFile("marks"));

        insertStudent.addActionListener(e -> insertStudentDialog());
        insertMarks  .addActionListener(e -> insertMarksDialog());
        searchName   .addActionListener(e -> searchNameDialog());
        searchMarks  .addActionListener(e -> searchMarksDialog());
        dispStudent  .addActionListener(e -> showTable("student"));
        dispMarks    .addActionListener(e -> showTable("marks"));
        dispDetails  .addActionListener(e -> displayDetails());
        dispResult   .addActionListener(e -> displayFullResult());
    }

    private JPanel buildStudentPanel() {
        studentModel = new DefaultTableModel(
                new String[]{"RollNo", "Name", "Action"}, 0) {
            public boolean isCellEditable(int r, int c) { return c == S_ACTION; }
        };
        studentTable = new JTable(studentModel);
        studentTable.getColumn("Action").setCellRenderer(new BtnRenderer());
        studentTable.getColumn("Action").setCellEditor(new BtnEditor(studentTable, studentModel));
        studentTable.getColumn("Action").setPreferredWidth(90);
        studentTable.setRowHeight(28);

        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("StudentTb  (RollNo, Name)"));
        p.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildMarksPanel() {
        marksModel = new DefaultTableModel(
                new String[]{"RollNo", "Marks", "Action"}, 0) {
            public boolean isCellEditable(int r, int c) { return c == M_ACTION; }
        };
        marksTable = new JTable(marksModel);
        marksTable.getColumn("Action").setCellRenderer(new BtnRenderer());
        marksTable.getColumn("Action").setCellEditor(new BtnEditor(marksTable, marksModel));
        marksTable.getColumn("Action").setPreferredWidth(90);
        marksTable.setRowHeight(28);

        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("MarksTb  (RollNo, Marks)"));
        p.add(new JScrollPane(marksTable), BorderLayout.CENTER);
        return p;
    }

    // ── File Operations ────────────────────────────────────────────────────────
    private void chooseFile(String which) {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            if (which.equals("student")) { studentFile = f; loadStudentFile(); }
            else                         { marksFile   = f; loadMarksFile(); }
        }
    }

    private void loadStudentFile() {
        studentModel.setRowCount(0);
        if (studentFile == null || !studentFile.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(studentFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Student s = Student.fromFileString(line);
                studentModel.addRow(new Object[]{s.rollNo, s.name, "Delete"});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading StudentTb: " + e.getMessage());
        }
    }

    private void loadMarksFile() {
        marksModel.setRowCount(0);
        if (marksFile == null || !marksFile.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(marksFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Marks m = Marks.fromFileString(line);
                marksModel.addRow(new Object[]{m.rollNo, m.marks, "Delete"});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading MarksTb: " + e.getMessage());
        }
    }

    private File promptSaveFile(String defaultName) {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File(defaultName));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            // Ensure .txt extension
            if (!f.getName().toLowerCase().endsWith(".txt"))
                f = new File(f.getAbsolutePath() + ".txt");
            return f;
        }
        return null;
    }

    private void saveFile(String which) {
        if (which.equals("student")) {
            if (studentFile == null) {
                studentFile = promptSaveFile("StudentTb.txt");
                if (studentFile == null) return;
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter(studentFile))) {
                for (int i = 0; i < studentModel.getRowCount(); i++) {
                    int roll = Integer.parseInt(studentModel.getValueAt(i, S_ROLL).toString());
                    String name = studentModel.getValueAt(i, S_NAME).toString();
                    pw.println(new Student(roll, name).toFileString());
                }
                JOptionPane.showMessageDialog(this, "StudentTb saved.");
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "Save error: " + e.getMessage()); }
        } else {
            if (marksFile == null) {
                marksFile = promptSaveFile("MarksTb.txt");
                if (marksFile == null) return;
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter(marksFile))) {
                for (int i = 0; i < marksModel.getRowCount(); i++) {
                    int roll  = Integer.parseInt(marksModel.getValueAt(i, M_ROLL).toString());
                    double mk = Double.parseDouble(marksModel.getValueAt(i, M_MARKS).toString());
                    pw.println(new Marks(roll, mk).toFileString());
                }
                JOptionPane.showMessageDialog(this, "MarksTb saved.");
            } catch (Exception e) { JOptionPane.showMessageDialog(this, "Save error: " + e.getMessage()); }
        }
    }

    // ── Menu Operations ────────────────────────────────────────────────────────

    /* (a) Insert a record in StudentTb */
    private void insertStudentDialog() {
        JTextField roll = new JTextField();
        JTextField name = new JTextField();
        Object[] msg = {"Roll No:", roll, "Name:", name};
        if (JOptionPane.showConfirmDialog(this, msg, "Insert Student Record",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int r = Integer.parseInt(roll.getText().trim());
                studentModel.addRow(new Object[]{r, name.getText().trim(), "Delete"});
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Roll No.");
            }
        }
    }

    /* (b) Insert a record in MarksTb */
    private void insertMarksDialog() {
        JTextField roll  = new JTextField();
        JTextField marks = new JTextField();
        Object[] msg = {"Roll No:", roll, "Marks:", marks};
        if (JOptionPane.showConfirmDialog(this, msg, "Insert Marks Record",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int    r  = Integer.parseInt(roll.getText().trim());
                double mk = Double.parseDouble(marks.getText().trim());
                marksModel.addRow(new Object[]{r, mk, "Delete"});
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        }
    }

    /* (c) Search Student Name by RollNo */
    private void searchNameDialog() {
        String input = JOptionPane.showInputDialog(this, "Enter Roll No to search name:", "Search Name", JOptionPane.QUESTION_MESSAGE);
        if (input == null) return;
        try {
            int roll = Integer.parseInt(input.trim());
            for (int i = 0; i < studentModel.getRowCount(); i++) {
                if (Integer.parseInt(studentModel.getValueAt(i, S_ROLL).toString()) == roll) {
                    String name = studentModel.getValueAt(i, S_NAME).toString();
                    JOptionPane.showMessageDialog(this,
                            "Roll No : " + roll + "\nName    : " + name,
                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "No student found with Roll No " + roll + ".");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Roll No.");
        }
    }

    /* (d) Search Marks by RollNo */
    private void searchMarksDialog() {
        String input = JOptionPane.showInputDialog(this, "Enter Roll No to search marks:", "Search Marks", JOptionPane.QUESTION_MESSAGE);
        if (input == null) return;
        try {
            int roll = Integer.parseInt(input.trim());
            for (int i = 0; i < marksModel.getRowCount(); i++) {
                if (Integer.parseInt(marksModel.getValueAt(i, M_ROLL).toString()) == roll) {
                    double mk = Double.parseDouble(marksModel.getValueAt(i, M_MARKS).toString());
                    JOptionPane.showMessageDialog(this,
                            "Roll No : " + roll + "\nMarks   : " + mk,
                            "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "No marks found for Roll No " + roll + ".");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Roll No.");
        }
    }

    /* (e) Display StudentTb  /  (f) Display MarksTb */
    private void showTable(String which) {
        JTable tbl;
        String title;
        if (which.equals("student")) { tbl = studentTable; title = "StudentTb"; }
        else                         { tbl = marksTable;   title = "MarksTb"; }

        DefaultTableModel src = (DefaultTableModel) tbl.getModel();
        if (src.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, title + " is empty."); return;
        }

        // Build display (exclude Action column)
        String[] cols = new String[src.getColumnCount() - 1];
        for (int c = 0; c < cols.length; c++) cols[c] = src.getColumnName(c);
        DefaultTableModel display = new DefaultTableModel(cols, 0);
        for (int r = 0; r < src.getRowCount(); r++) {
            Object[] row = new Object[cols.length];
            for (int c = 0; c < cols.length; c++) row[c] = src.getValueAt(r, c);
            display.addRow(row);
        }

        JTable view = new JTable(display);
        view.setEnabled(false);
        JScrollPane sp = new JScrollPane(view);
        sp.setPreferredSize(new Dimension(400, 250));
        JOptionPane.showMessageDialog(this, sp, "Display " + title, JOptionPane.PLAIN_MESSAGE);
    }

    /* (g) Display Student Details (Roll No, Name, Marks) */
    private void displayDetails() {
        if (studentModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "StudentTb is empty."); return;
        }
        DefaultTableModel detail = new DefaultTableModel(
                new String[]{"Roll No", "Name", "Marks"}, 0);

        for (int i = 0; i < studentModel.getRowCount(); i++) {
            int roll  = Integer.parseInt(studentModel.getValueAt(i, S_ROLL).toString());
            String nm = studentModel.getValueAt(i, S_NAME).toString();
            String mk = findMarks(roll);
            detail.addRow(new Object[]{roll, nm, mk});
        }

        JTable view = new JTable(detail);
        view.setEnabled(false);
        JScrollPane sp = new JScrollPane(view);
        sp.setPreferredSize(new Dimension(450, 250));
        JOptionPane.showMessageDialog(this, sp, "Student Details", JOptionPane.PLAIN_MESSAGE);
    }

    /* (h) Full Result (Roll No, Name, Marks) with Pass/Fail */
    private void displayFullResult() {
        if (studentModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "StudentTb is empty."); return;
        }
        DefaultTableModel result = new DefaultTableModel(
                new String[]{"Roll No", "Name", "Marks", "Result"}, 0);

        for (int i = 0; i < studentModel.getRowCount(); i++) {
            int    roll = Integer.parseInt(studentModel.getValueAt(i, S_ROLL).toString());
            String nm   = studentModel.getValueAt(i, S_NAME).toString();
            String mkStr = findMarks(roll);
            String res;
            try {
                double mk = Double.parseDouble(mkStr);
                res = mk >= 40 ? "PASS" : "FAIL";
            } catch (NumberFormatException e) {
                res = "N/A";
            }
            result.addRow(new Object[]{roll, nm, mkStr, res});
        }

        JTable view = new JTable(result);
        view.setEnabled(false);
        // Colour Pass/Fail
        view.getColumnModel().getColumn(3).setCellRenderer((t, v, s, f, r, c) -> {
            JLabel lbl = new JLabel(v == null ? "" : v.toString(), SwingConstants.CENTER);
            lbl.setOpaque(true);
            lbl.setBackground("PASS".equals(v) ? new Color(180, 255, 180) : new Color(255, 180, 180));
            return lbl;
        });

        JScrollPane sp = new JScrollPane(view);
        sp.setPreferredSize(new Dimension(500, 280));
        JOptionPane.showMessageDialog(this, sp, "Full Result", JOptionPane.PLAIN_MESSAGE);
    }

    /** Look up marks string for a roll no (returns "N/A" if not found). */
    private String findMarks(int roll) {
        for (int j = 0; j < marksModel.getRowCount(); j++) {
            if (Integer.parseInt(marksModel.getValueAt(j, M_ROLL).toString()) == roll)
                return marksModel.getValueAt(j, M_MARKS).toString();
        }
        return "N/A";
    }

    // ── Shared Button Renderer / Editor ───────────────────────────────────────

    static class BtnRenderer extends JPanel implements TableCellRenderer {
        BtnRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
            add(new JButton("Delete"));
        }
        public Component getTableCellRendererComponent(JTable t, Object v,
                boolean s, boolean f, int r, int c) { return this; }
    }

    static class BtnEditor extends DefaultCellEditor {
        private JPanel panel;
        private int row;
        private JTable table;
        BtnEditor(JTable table, DefaultTableModel model) {
            super(new JTextField());
            this.table = table;
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
            JButton del = new JButton("Delete");
            panel.add(del);
            del.addActionListener(e -> {
                // Stop editing first so Swing doesn't try to write back to the deleted row
                if (table.isEditing()) {
                    table.getCellEditor().cancelCellEditing();
                }
                if (row >= 0 && row < model.getRowCount()) {
                    model.removeRow(row);
                }
            });
        }
        public Component getTableCellEditorComponent(JTable t, Object v,
                boolean s, int r, int c) { row = r; return panel; }
        public Object getCellEditorValue() { return "Delete"; }
    }

    // ── Entry Point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDatabaseGUI().setVisible(true));
    }
}
