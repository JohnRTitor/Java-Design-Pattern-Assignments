package random_number_pool;

import java.util.ArrayList;

class RandomNumberPool {
	private ArrayList<RandomNumber> pool = new ArrayList<>();
	private int poolSize;

	public RandomNumberPool(int poolSize) {
		this.poolSize = poolSize;
	}

	public void distributeNumbers(ArrayList<Integer> numbers) {
		pool.clear();
		for (int i = 0; i < poolSize; i++)
			pool.add(new RandomNumber());
		for (int i = 0; i < numbers.size(); i++)
			pool.get(i % poolSize).addNumber(numbers.get(i));
	}

	public void setPoolSize(int newSize) {
		this.poolSize = newSize;
	}

	public ArrayList<RandomNumber> getPool() {
		return pool;
	}

	public int getPoolSize() {
		return poolSize;
	}
}
