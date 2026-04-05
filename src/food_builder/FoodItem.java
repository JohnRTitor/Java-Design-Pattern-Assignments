package food_builder;

import java.awt.Graphics;

public interface FoodItem {
	String name();

	int price();

	void drawImage(Graphics g, int x, int y);
}
