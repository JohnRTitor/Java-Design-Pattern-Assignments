package employee_without_pattern;

class Employee {
	String name;
	int age;
	String address;
	String company;
	String department;
	double salary;

	Employee(String name, int age, String address, String company, String department, double salary) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.company = company;
		this.department = department;
		this.salary = salary;
	}

	String toFileString() {
		return name + "," + age + "," + address + "," + company + "," + department + "," + salary;
	}

	static Employee fromFileString(String line) {
		String[] p = line.split(",");
		String name = p[0];
		int age = Integer.parseInt(p[1].trim());
		String address = p[2];
		String company = p.length > 3 ? p[3] : "";
		String department = p.length > 4 ? p[4] : "";
		double salary = p.length > 5 ? Double.parseDouble(p[5].trim()) : 0.0;
		return new Employee(name, age, address, company, department, salary);
	}
}
