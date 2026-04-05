package food_builder;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

class Rosogolla implements FoodItem {

	@Override
	public String name() {
		return "Rosogolla";
	}

	@Override
	public int price() {
		return 20;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Dessert/Rosogolla.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class Brownie implements FoodItem {

	@Override
	public String name() {
		return "Brownie";
	}

	@Override
	public int price() {
		return 70;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Dessert/Brownie.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class Firni implements FoodItem {

	@Override
	public String name() {
		return "Firni";
	}

	@Override
	public int price() {
		return 90;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Dessert/Firni.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class Pepsi implements FoodItem {

	@Override
	public String name() {
		return "Pepsi";
	}

	@Override
	public int price() {
		return 30;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Dessert/Pepsi.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class Chocobar implements FoodItem {

	@Override
	public String name() {
		return "Chocobar";
	}

	@Override
	public int price() {
		return 30;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Dessert/Chocobar.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}
