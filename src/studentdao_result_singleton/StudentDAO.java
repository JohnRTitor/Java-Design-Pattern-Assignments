package studentdao_result_singleton;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDAO - Multiton (Max 5 instances) Data loaded once using
 * initialize(filePath)
 */
public class StudentDAO {

	// Pool configuration
	private static final int MAX_INSTANCES = 5;
	private static final StudentDAO[] pool = new StudentDAO[MAX_INSTANCES];
	private static int counter = 0;

	// Shared result data
	private static Result[] results;
	private static boolean initialized = false;

	// Instance identity
	private final int instanceId;

	private StudentDAO(int id) {
		this.instanceId = id;
	}

	// 🔹 Initialize method (called from GUI after JFileChooser)
	public static synchronized void initialize(String filePath) {

		if (initialized)
			return; // prevent reloading

		List<Result> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty())
					continue;

				String[] parts = line.split("\\s+");
				int rollNo = Integer.parseInt(parts[0]);
				int marks = Integer.parseInt(parts[1]);

				list.add(new Result(rollNo, marks));
			}

			System.out.println("[StudentDAO] Loaded " + list.size() + " records.");

		} catch (IOException e) {
			System.err.println("Error reading file: " + e.getMessage());
		}

		results = list.toArray(new Result[0]);

		// Create pool
		for (int i = 0; i < MAX_INSTANCES; i++) {
			pool[i] = new StudentDAO(i + 1);
		}

		initialized = true;
	}

	// 🔹 Round-robin instance provider
	public static synchronized StudentDAO getInstance() {

		if (!initialized)
			throw new IllegalStateException("StudentDAO not initialized. Call initialize() first.");

		StudentDAO instance = pool[counter % MAX_INSTANCES];
		counter++;
		return instance;
	}

	// 🔹 Business method
	public int getMarks(int rollNo) {

		if (results == null)
			return -1;

		for (Result r : results) {
			if (r.getRollNo() == rollNo)
				return r.getMarks();
		}
		return -1;
	}

	public int getInstanceId() {
		return instanceId;
	}

	@Override
	public String toString() {
		return "StudentDAO@Instance-" + instanceId + "[" + Integer.toHexString(System.identityHashCode(this)) + "]";
	}
}
