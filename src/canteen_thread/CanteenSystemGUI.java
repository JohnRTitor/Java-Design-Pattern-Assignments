package canteen_thread;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class CanteenSystemGUI extends JFrame implements CanteenChef.StatusListener {

	private JComboBox<String> foodDropdown;
	private JLabel statusLabel = new JLabel("System Running");

	private JPanel tablePanel;

	private final CanteenTable table;
	private final CanteenChef chef;

	private final Map<String, Integer> cookTime = Map.of("Biryani", 10, "Pizza", 8, "Burger", 6, "Fried Rice", 7);

	private final Map<String, Integer> eatTime = Map.of("Biryani", 7, "Pizza", 5, "Burger", 4, "Fried Rice", 6);

	public CanteenSystemGUI() {

		setTitle("Canteen Management System");
		setSize(550, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		table = new CanteenTable();
		chef = new CanteenChef(table, cookTime);

		setLayout(new BorderLayout(10, 10));
		add(createTopPanel(), BorderLayout.NORTH);
		add(createTablePanel(), BorderLayout.CENTER);
		add(createStatusBar(), BorderLayout.SOUTH);

		setVisible(true);
	}

	// ================= UI =================

	private JPanel createTopPanel() {

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panel.setBorder(new TitledBorder("Order Food"));

		foodDropdown = new JComboBox<>(new String[] { "Select Food", "Biryani", "Pizza", "Burger", "Fried Rice" });

		JButton orderBtn = new JButton("Order");
		JButton clearBtn = new JButton("Clear Table");

		orderBtn.addActionListener(e -> placeOrder());

		clearBtn.addActionListener(e -> {
			table.clearTable();
			refreshTableUI();
			updateStatus("Table cleared");
		});

		panel.add(foodDropdown);
		panel.add(orderBtn);
		panel.add(clearBtn);

		return panel;
	}

	private JPanel createTablePanel() {

		tablePanel = new JPanel(new GridLayout(table.getCapacity(), 1, 5, 5));

		tablePanel.setBorder(new TitledBorder("Table (Capacity = " + table.getCapacity() + ")"));

		refreshTableUI();
		return tablePanel;
	}

	private JPanel createStatusBar() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		panel.add(statusLabel, BorderLayout.WEST);
		return panel;
	}

	// ================= ORDER =================

	private void placeOrder() {

		String food = (String) foodDropdown.getSelectedItem();

		if (food.equals("Select Food")) {
			JOptionPane.showMessageDialog(this, "Please select food.");
			return;
		}

		if (!chef.canCook()) {
			JOptionPane.showMessageDialog(this, "❌ Cannot start cooking.\n\nMaximum limit reached.",
					"Cooking Limit Reached", JOptionPane.WARNING_MESSAGE);
			return;
		}

		chef.cookFood(food, this);
	}

	// ================= TABLE UI =================

	private void refreshTableUI() {

		SwingUtilities.invokeLater(() -> {

			tablePanel.removeAll();

			List<Dish> dishes = table.getSnapshot();

			for (int i = 0; i < table.getCapacity(); i++) {

				JPanel row = new JPanel(new BorderLayout());

				row.setBorder(new LineBorder(Color.GRAY));

				if (i < dishes.size()) {

					Dish dish = dishes.get(i);

					JLabel label = new JLabel("  " + dish.getName());

					JButton consumeBtn = new JButton("Consume");

					consumeBtn.addActionListener(e -> new Thread(() -> consumeDish(dish, consumeBtn)).start());

					row.add(label, BorderLayout.CENTER);
					row.add(consumeBtn, BorderLayout.EAST);

				} else {
					row.add(new JLabel("  Empty"), BorderLayout.CENTER);
				}

				tablePanel.add(row);
			}

			tablePanel.revalidate();
			tablePanel.repaint();
		});
	}

	// ================= CONSUME =================

	private void consumeDish(Dish dish, JButton button) {

		try {

			button.setEnabled(false);

			int time = eatTime.get(dish.getName());

			for (int i = time; i > 0; i--) {

				int t = i;

				SwingUtilities.invokeLater(() -> button.setText("Consuming (" + t + "s)"));

				Thread.sleep(1000);
			}

			table.removeDish(dish);
			updateStatus(dish.getName() + " consumed");

			refreshTableUI();

		} catch (InterruptedException ignored) {
		}
	}

	// ================= STATUS =================

	@Override
	public void updateStatus(String msg) {

		SwingUtilities.invokeLater(() -> statusLabel.setText(msg));

		refreshTableUI();
	}

	// ================= MAIN =================

	public static void main(String[] args) {
		SwingUtilities.invokeLater(CanteenSystemGUI::new);
	}
}
