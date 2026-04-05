package employee_observer;

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
		return new Employee(p[0], Integer.parseInt(p[1].trim()), p.length > 2 ? p[2] : "", p.length > 3 ? p[3] : "",
				p.length > 4 ? p[4] : "", p.length > 5 ? Double.parseDouble(p[5].trim()) : 0.0);
	}
}