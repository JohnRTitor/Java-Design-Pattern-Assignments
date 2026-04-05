package food_builder;

public interface MealBuilder {
	public void addAppetiser();

	public void addMainCourse();

	public void addDessert();

	public Meal getMeal();
}
