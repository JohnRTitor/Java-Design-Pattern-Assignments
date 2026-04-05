package paint_app;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MyFrame extends JFrame implements ActionListener {

	MyDrawPanel drawPanel = new MyDrawPanel();

	public MyFrame() {
		setTitle("Paint");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
	}

	public void initComponents() {
		JMenuBar mb = new JMenuBar();
		JMenu fileMn = new JMenu("File");
		JMenuItem newMI = new JMenuItem("New");
		JMenuItem openMI = new JMenuItem("Open");
		JMenuItem saveMI = new JMenuItem("Save");
		JMenuItem exitMI = new JMenuItem("Exit");
		exitMI.addActionListener(this);

		fileMn.add(newMI);
		fileMn.add(openMI);
		fileMn.add(saveMI);
		fileMn.add(exitMI);
		mb.add(fileMn);

		JMenu editMn = new JMenu("Edit");
		mb.add(editMn);

		setJMenuBar(mb);

		JPanel btnPanel = new JPanel();

		JButton lineBtn = new JButton("Line");
		lineBtn.addActionListener(this);
		btnPanel.add(lineBtn);

		JButton circleBtn = new JButton("Circle");
		circleBtn.addActionListener(this);
		btnPanel.add(circleBtn);

		JButton rectangleBtn = new JButton("Rectangle");
		rectangleBtn.addActionListener(this);
		btnPanel.add(rectangleBtn);

		add(btnPanel, BorderLayout.NORTH);
		add(drawPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equalsIgnoreCase("Line")) {
			drawPanel.setTool("Line");
		} else if (cmd.equalsIgnoreCase("Circle")) {
			drawPanel.setTool("Circle");
		} else if (cmd.equalsIgnoreCase("Rectangle")) {
			drawPanel.setTool("Rectangle");
		} else if (cmd.equalsIgnoreCase("Exit")) {
			System.exit(0);
		}
	}

	public static void main(String args[]) {
		MyFrame f1 = new MyFrame();
		f1.setVisible(true);
	}
}