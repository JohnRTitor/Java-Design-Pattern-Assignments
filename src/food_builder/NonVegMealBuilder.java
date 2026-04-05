package food_builder;

public class NonVegMealBuilder implements MealBuilder {
	private Meal meal = new Meal();

	@Override
	public void addAppetiser() {
		this.meal.addItem(new ChickenPakora());
	}

	@Override
	public void addMainCourse() {
		this.meal.addItem(new MuttonBiriyani());
		this.meal.addItem(new ChickenChap());
	}

	@Override
	public void addDessert() {
		this.meal.addItem(new Rosogolla());
		this.meal.addItem(new Brownie());
		this.meal.addItem(new Chocobar());
	}

	@Override
	public Meal getMeal() {
		return meal;
	}
}