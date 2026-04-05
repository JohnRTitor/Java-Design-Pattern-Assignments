package food_builder;

public class VegMealBuilder implements MealBuilder {
	private Meal meal = new Meal();

	@Override
	public void addAppetiser() {
		this.meal.addItem(new PaneerPakora());
	}

	@Override
	public void addMainCourse() {
		this.meal.addItem(new Rice());
		this.meal.addItem(new Daal());
		this.meal.addItem(new Sukto());
		this.meal.addItem(new ChilliPaneer());
	}

	@Override
	public void addDessert() {
		this.meal.addItem(new Rosogolla());
		this.meal.addItem(new Firni());
		this.meal.addItem(new Pepsi());
	}

	@Override
	public Meal getMeal() {
		return meal;
	}

}
