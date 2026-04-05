package student_database;

class Marks {
    int rollNo;
    double marks;

    Marks(int rollNo, double marks) {
        this.rollNo = rollNo;
        this.marks = marks;
    }

    String toFileString() {
        return rollNo + "," + marks;
    }

    static Marks fromFileString(String line) {
        String[] p = line.split(",", 2);
        int rollNo = Integer.parseInt(p[0].trim());
        double marks = p.length > 1 ? Double.parseDouble(p[1].trim()) : 0.0;
        return new Marks(rollNo, marks);
    }
}
