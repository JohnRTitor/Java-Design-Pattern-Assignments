package food_builder;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

class Rice implements FoodItem {
	@Override
	public String name() {
		return "Rice";
	}

	@Override
	public int price() {
		return 20;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Veg/Rice.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class FriedRice implements FoodItem {
	@Override
	public String name() {
		return "Fried Rice";
	}

	@Override
	public int price() {
		return 50;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Veg/FriedRice.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class Daal implements FoodItem {
	@Override
	public String name() {
		return "Daal";
	}

	@Override
	public int price() {
		return 20;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Veg/Daal.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class Sukto implements FoodItem {
	@Override
	public String name() {
		return "Sukto";
	}

	@Override
	public int price() {
		return 20;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Veg/Sukto.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class Chatni implements FoodItem {
	@Override
	public String name() {
		return "Chatni";
	}

	@Override
	public int price() {
		return 30;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Veg/Chatni.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class PaneerPakora implements FoodItem {
	@Override
	public String name() {
		return "Paneer Pakora";
	}

	@Override
	public int price() {
		return 40;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Veg/PaneerPakora.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}

class ChilliPaneer implements FoodItem {
	@Override
	public String name() {
		return "Chilli Paneer";
	}

	@Override
	public int price() {
		return 80;
	}

	@Override
	public void drawImage(Graphics g, int x, int y) {
		Image img = new ImageIcon(getClass().getResource("/FoodBuilder/Images/Veg/ChilliPaneer.jpg")).getImage();

		g.drawImage(img, x, y, 90, 90, null);
		g.drawString(this.name(), x, y + 110);
	}
}
