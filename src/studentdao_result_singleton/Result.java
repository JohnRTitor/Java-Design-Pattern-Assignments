package studentdao_result_singleton;

/**
 * Simple data model representing one row from ResultDB.txt.
 */
public class Result {
	private int rollNo;
	private int marks;

	public Result(int rollNo, int marks) {
		this.rollNo = rollNo;
		this.marks = marks;
	}

	public int getRollNo() {
		return rollNo;
	}

	public int getMarks() {
		return marks;
	}

	@Override
	public String toString() {
		return "Result{rollNo=" + rollNo + ", marks=" + marks + "}";
	}
}
