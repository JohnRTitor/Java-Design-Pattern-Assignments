package notification_service_factory;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class NotifierGUI extends JFrame {
	public NotifierGUI() {
		setTitle("Notification Sender");
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		initComponents();
	}

	void initComponents() {
		String[] options = { "SMS", "Email", "Buzzer" };
		JComboBox dropdown = new JComboBox(options);
		add(dropdown);

		JButton sendBtn = new JButton("Notify");

		NotifierGUI GUIFrame = this;

		sendBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedValue = (String) dropdown.getSelectedItem();

				Notification n1 = NotificationFactory.getNotification(selectedValue);
				if (n1 == null) {
					JOptionPane.showMessageDialog(GUIFrame, "Wrong value selected.");
					return;
				}

				n1.notifyUser();
				JOptionPane.showMessageDialog(GUIFrame, selectedValue + " sent successfully");
			}

		});

		add(sendBtn);
	}

	public static void main(String[] args) {
		NotifierGUI ng = new NotifierGUI();
		ng.setVisible(true);
	}

}
