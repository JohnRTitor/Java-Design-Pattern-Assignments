package studentdao_result_singleton;

import java.util.Random;

/**
 * ResultQuery — demonstrates round-robin getInstance() and getMarks().
 *
 * Loops 12 times (> MAX_INSTANCES=5) so the round-robin wraps around and we see
 * all 5 instances reused.
 */
public class ResultQuery {

	private static final int QUERY_COUNT = 12;

	public static void main(String[] args) {

		// Roll numbers that exist in ResultDB.txt
		int[] validRolls = { 101, 102, 103, 104, 105, 106, 107, 108, 109, 110 };
		Random rng = new Random();

		System.out.println("=".repeat(60));
		System.out.println("         ResultQuery — Round-Robin DAO Demo");
		System.out.println("=".repeat(60));
		System.out.println();

		for (int i = 1; i <= QUERY_COUNT; i++) {
			StudentDAO.initialize("ResultDB.txt");
			// Get instance via round-robin
			StudentDAO dao = StudentDAO.getInstance();

			// Pick a random roll number
			int rollNo = validRolls[rng.nextInt(validRolls.length)];

			// Query marks
			int marks = dao.getMarks(rollNo);

			System.out.printf("Query #%-2d  |  Instance: %-35s  |  RollNo: %d  |  Marks: %s%n", i, dao.toString(),
					rollNo, marks == -1 ? "NOT FOUND" : String.valueOf(marks));
		}

		System.out.println();
		System.out.println("=".repeat(60));
		System.out.println("Notice: Instance IDs cycle 1→2→3→4→5→1→2… (round-robin)");
		System.out.println("=".repeat(60));
	}
}
