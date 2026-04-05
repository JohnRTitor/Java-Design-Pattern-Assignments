package knife_factory;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

class KnifeGUI extends JFrame {

	private JComboBox<String> materialBox;
	private JComboBox<String> shapeBox;
	private JTextArea outputArea;

	KnifeGUI() {
		setTitle("Knife Order System");
		setSize(450, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// ── Top: input ────────────────────────────────────────────────────────
		JPanel top = new JPanel(new GridLayout(3, 2, 5, 5));
		top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		top.add(new JLabel("Material:"));
		materialBox = new JComboBox<>(new String[] { "Steel", "Iron", "Titanium" });
		top.add(materialBox);

		top.add(new JLabel("Shape:"));
		shapeBox = new JComboBox<>(new String[] { "Curved", "Straight" });
		top.add(shapeBox);

		JButton orderBtn = new JButton("Place Order");
		JButton clearBtn = new JButton("Clear");
		top.add(orderBtn);
		top.add(clearBtn);

		add(top, BorderLayout.NORTH);

		// ── Center: output ────────────────────────────────────────────────────
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		add(new JScrollPane(outputArea), BorderLayout.CENTER);

		// ── Actions ───────────────────────────────────────────────────────────
		orderBtn.addActionListener(e -> placeOrder());
		clearBtn.addActionListener(e -> outputArea.setText(""));

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void placeOrder() {
		String material = (String) materialBox.getSelectedItem();
		String shape = (String) shapeBox.getSelectedItem();

		// Create knife via factory
		KnifeStore store = new KnifeStore();

		// Capture steps by creating knife directly for display
		Knife knife = null;
		switch (material) {
		case "Steel":
			knife = shape.equals("Curved") ? new SteelCurvedKnife() : new SteelStraightKnife();
			break;
		case "Iron":
			knife = shape.equals("Curved") ? new IronCurvedKnife() : new IronStraightKnife();
			break;
		case "Titanium":
			knife = shape.equals("Curved") ? new TitaniumCurvedKnife() : new TitaniumStraightKnife();
			break;
		}

		if (knife == null)
			return;

		outputArea.append("--- Order: " + knife.getName() + " ---\n");
		outputArea.append("Sharpening...\n");
		outputArea.append("Polishing...\n");
		outputArea.append("Packing...\n");
		outputArea.append(knife.getName() + " is ready!\n\n");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(KnifeGUI::new);
	}
}
