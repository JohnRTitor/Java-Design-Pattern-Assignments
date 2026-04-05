package food_builder;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

class ChickenChap implements FoodItem {

	@Override
	public String name() {
		return "Chicken Chap";
	}

	@Override
	public int price() {
		return 100;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/NonVeg/ChickenChap.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class ChickenPakora implements FoodItem {

	@Override
	public String name() {
		return "Chicken Pakora";
	}

	@Override
	public int price() {
		return 50;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/NonVeg/ChickenPakora.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class ChickenRoll implements FoodItem {

	@Override
	public String name() {
		return "Chicken Roll";
	}

	@Override
	public int price() {
		return 70;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/NonVeg/ChickenRoll.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class ChickenBiriyani implements FoodItem {

	@Override
	public String name() {
		return "Chicken Biriyani";
	}

	@Override
	public int price() {
		return 200;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/NonVeg/ChickenBiriyani.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class MuttonBiriyani implements FoodItem {

	@Override
	public String name() {
		return "Mutton Biriyani";
	}

	@Override
	public int price() {
		return 200;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/NonVeg/MuttonBiriyani.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}
