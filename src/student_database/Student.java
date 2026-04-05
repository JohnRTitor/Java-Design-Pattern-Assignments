package student_database;

class Student {
    int rollNo;
    String name;

    Student(int rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    String toFileString() {
        return rollNo + "," + name;
    }

    static Student fromFileString(String line) {
        String[] p = line.split(",", 2);
        int rollNo = Integer.parseInt(p[0].trim());
        String name = p.length > 1 ? p[1].trim() : "";
        return new Student(rollNo, name);
    }
}
