package food_builder;

import java.util.ArrayList;
import java.util.List;

public class Meal {
	private List<FoodItem> items = new ArrayList<FoodItem>();

	public void addItem(FoodItem item) {
		items.add(item);
	}

	public List<FoodItem> getItems() {
		return items;
	}

	public String[] getItemNames() {
		String[] names = new String[items.size()];

		for (int i = 0; i < items.size(); i++) {
			names[i] = items.get(i).name();
		}

		return names;
	}

	public int getPrice() {
		int total = 0;

		for (FoodItem item : items) {
			total += item.price();
		}

		return total;
	}

}
