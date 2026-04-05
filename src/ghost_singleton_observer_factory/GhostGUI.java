package ghost_singleton_observer_factory;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GhostGUI extends JFrame {

	private JTextField nameField;
	private JTextArea outputArea;

	public GhostGUI() {

		setTitle("Haunted House");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter Name:"));

		nameField = new JTextField(10);
		panel.add(nameField);

		JButton enterBtn = new JButton("Enter");
		panel.add(enterBtn);

		add(panel, BorderLayout.NORTH);

		outputArea = new JTextArea();
		outputArea.setEditable(false);
		add(new JScrollPane(outputArea), BorderLayout.CENTER);

		HauntedHouse house = HauntedHouse.getInstance();

		house.addGhost(GhostFactory.createGhost("Poltergeist"));
		house.addGhost(GhostFactory.createGhost("Wraith"));
		house.addGhost(GhostFactory.createGhost("Phantom"));

		enterBtn.addActionListener(e -> {

			String name = nameField.getText();

			if (name.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Enter a name!");
				return;
			}

			String result = house.notifyGhosts(name);
			outputArea.setText(result);
		});
	}

	public static void main(String[] args) {
		new GhostGUI().setVisible(true);
	}
}
