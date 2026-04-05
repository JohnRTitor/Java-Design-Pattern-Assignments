package canteen_thread;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CanteenChef {

	public static final int DEFAULT_MAX_OVENS = 4;

	private final CanteenTable table;
	private final Map<String, Integer> cookTime;
	private final int maxOvens;

	private int cookingCount = 0;
	private final Lock cookingLock = new ReentrantLock();

	public CanteenChef(CanteenTable table, Map<String, Integer> cookTime) {
		this(table, cookTime, DEFAULT_MAX_OVENS);
	}

	public CanteenChef(CanteenTable table, Map<String, Integer> cookTime, int maxOvens) {
		this.table = table;
		this.cookTime = cookTime;
		this.maxOvens = maxOvens;
	}

	// Check if cooking can start
	public boolean canCook() {

		cookingLock.lock();
		try {
			int currentTableSize = table.getCurrentSize();
			int tableCapacity = table.getCapacity();

			boolean ovenAvailable = cookingCount < maxOvens;
			boolean tableHasFutureSpace = (currentTableSize + cookingCount) < tableCapacity;

			return ovenAvailable && tableHasFutureSpace;

		} finally {
			cookingLock.unlock();
		}
	}

	public void cookFood(String foodName, StatusListener listener) {

		cookingLock.lock();
		try {
			cookingCount++;
		} finally {
			cookingLock.unlock();
		}

		new Thread(() -> {
			try {

				int time = cookTime.get(foodName);

				for (int i = time; i > 0; i--) {
					listener.updateStatus("Cooking " + foodName + " (" + i + "s)");
					Thread.sleep(1000);
				}

				table.addDish(new Dish(foodName));
				listener.updateStatus(foodName + " placed on table");

			} catch (InterruptedException ignored) {

			} finally {

				cookingLock.lock();
				try {
					cookingCount--;
				} finally {
					cookingLock.unlock();
				}
			}

		}).start();
	}

	public interface StatusListener {
		void updateStatus(String message);
	}
}
