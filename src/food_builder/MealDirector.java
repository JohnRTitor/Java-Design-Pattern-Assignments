package food_builder;

public class MealDirector {
	private MealBuilder mealBuilder;

	public MealDirector(MealBuilder mealbuilder) {
		this.mealBuilder = mealBuilder;
	}

	public void prepareFeast() {
		if (this.mealBuilder == null) {
			return;
		}

		this.mealBuilder.addAppetiser();
		this.mealBuilder.addMainCourse();
		this.mealBuilder.addDessert();
	}
}
