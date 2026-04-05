package random_number_pool;

import java.util.ArrayList;

class RandomNumber {
	private ArrayList<Integer> numbers = new ArrayList<>();

	public void addNumber(int n) {
		numbers.add(n);
	}

	public ArrayList<Integer> getNumbers() {
		return numbers;
	}
}