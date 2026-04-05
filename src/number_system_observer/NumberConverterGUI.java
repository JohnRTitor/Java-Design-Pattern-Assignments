package number_system_observer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class NumberConverterGUI extends JFrame {

	private JTextField decimalField;
	private JTextField binaryField, octalField, hexField;
	private JCheckBox binCheck, octCheck, hexCheck;
	private JButton convertBtn;

	public NumberConverterGUI() {

		setTitle("Number Converter");
		setSize(420, 280);
		setLayout(new GridLayout(5, 3, 8, 8));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
	}

	private void initComponents() {
		DecimalSubject subject = new DecimalSubject();
		BinaryObserver binObs = new BinaryObserver(subject);
		OctalObserver octObs = new OctalObserver(subject);
		HexObserver hexObs = new HexObserver(subject);

		decimalField = new JTextField();

		binaryField = new JTextField();
		octalField = new JTextField();
		hexField = new JTextField();

		binaryField.setEditable(false);
		octalField.setEditable(false);
		hexField.setEditable(false);

		binCheck = new JCheckBox("Binary");
		binCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (binCheck.isSelected()) {
					subject.attach(binObs);
				} else {
					subject.detach(binObs);
				}
			}
		});

		octCheck = new JCheckBox("Octal");
		octCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (octCheck.isSelected()) {
					subject.attach(octObs);
				} else {
					subject.detach(octObs);
				}
			}
		});

		hexCheck = new JCheckBox("Hexadecimal");
		hexCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hexCheck.isSelected()) {
					subject.attach(hexObs);
				} else {
					subject.detach(hexObs);
				}
			}
		});

		convertBtn = new JButton("Convert");
		convertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num = Integer.parseInt(decimalField.getText());

				subject.setState(num);
				subject.notifyUser();

				binaryField.setText(binCheck.isSelected() ? binObs.getState() : "");

				octalField.setText(octCheck.isSelected() ? octObs.getState() : "");

				hexField.setText(hexCheck.isSelected() ? hexObs.getState() : "");

			}
		});

		add(new JLabel("Decimal Number:"));
		add(decimalField);
		add(new JLabel(""));

		add(binCheck);
		add(binaryField);
		add(new JLabel(""));

		add(octCheck);
		add(octalField);
		add(new JLabel(""));

		add(hexCheck);
		add(hexField);
		add(new JLabel(""));

		add(new JLabel(""));
		add(convertBtn);
		add(new JLabel(""));

	}

	public static void main(String[] args) {
		NumberConverterGUI frame = new NumberConverterGUI();
		frame.setVisible(true);
	}
}