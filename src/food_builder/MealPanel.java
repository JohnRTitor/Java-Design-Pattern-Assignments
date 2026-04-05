package food_builder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MealPanel extends JPanel {
	private Meal meal;

	public MealPanel() {
		setPreferredSize(new Dimension(650, 250));
		setBackground(Color.WHITE);
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
		repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (meal == null)
			return;

		int x = 20, y = 20;
		for (FoodItem item : meal.getItems()) {
			item.drawImage(g, x, y);
			x += 120;
		}

		g.setColor(Color.BLACK);
		g.drawString("Total Price: ₹" + meal.getPrice(), 20, 200);
	}
}
