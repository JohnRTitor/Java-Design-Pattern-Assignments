package canteen_thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CanteenTable {

	public static final int DEFAULT_CAPACITY = 5;

	private final int capacity;
	private final List<Dish> dishes = new ArrayList<>();
	private final Lock lock = new ReentrantLock();

	public CanteenTable() {
		this(DEFAULT_CAPACITY);
	}

	public CanteenTable(int capacity) {
		this.capacity = capacity;
	}

	public void addDish(Dish dish) {
		lock.lock();
		try {
			if (dishes.size() < capacity) {
				dishes.add(dish);
			}
		} finally {
			lock.unlock();
		}
	}

	public void removeDish(Dish dish) {
		lock.lock();
		try {
			dishes.remove(dish);
		} finally {
			lock.unlock();
		}
	}

	public void clearTable() {
		lock.lock();
		try {
			dishes.clear();
		} finally {
			lock.unlock();
		}
	}

	public int getCurrentSize() {
		lock.lock();
		try {
			return dishes.size();
		} finally {
			lock.unlock();
		}
	}

	public int getCapacity() {
		return capacity;
	}

	public List<Dish> getSnapshot() {
		lock.lock();
		try {
			return new ArrayList<>(dishes);
		} finally {
			lock.unlock();
		}
	}
}
