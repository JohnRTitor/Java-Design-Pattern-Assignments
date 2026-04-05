package factorial;

class FactUtil {

	// Single instance
	private static FactUtil instance = null;

	// Private constructor - prevents direct instantiation
	private FactUtil() {
	}

	// Returns the single instance
	static FactUtil getInstance() {
		if (instance == null) {
			instance = new FactUtil();
		}
		return instance;
	}

	// Computes n!
	int factorial(int n) {
		if (n < 0)
			return -1; // undefined
		if (n == 0)
			return 1;
		int result = 1;
		for (int i = 1; i <= n; i++) {
			result *= i;
		}
		return result;
	}
}
