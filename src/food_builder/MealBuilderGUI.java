package food_builder;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MealBuilderGUI extends JFrame {
	private MealPanel mealPanel;

	public MealBuilderGUI() {
		setTitle("Meal Builder Pattern");
		setSize(700, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		initComponents();
	}

	public void initComponents() {
		JPanel topPanel = new JPanel();
		JButton vegBtn = new JButton("Veg Meal");
		JButton nonVegBtn = new JButton("Non-Veg Meal");

		topPanel.add(vegBtn);
		topPanel.add(nonVegBtn);
		add(topPanel);

		mealPanel = new MealPanel();
		mealPanel.setBackground(Color.WHITE);
		add(mealPanel);

		vegBtn.addActionListener(e -> {
			MealBuilder builder = new VegMealBuilder();
			MealDirector director = new MealDirector(builder);
			director.prepareFeast();
			mealPanel.setMeal(builder.getMeal());
		});

		nonVegBtn.addActionListener(e -> {
			MealBuilder builder = new NonVegMealBuilder();
			MealDirector director = new MealDirector(builder);
			director.prepareFeast();
			mealPanel.setMeal(builder.getMeal());
		});
	}

	public static void main(String args[]) {
		new MealBuilderGUI().setVisible(true);
	}
}
