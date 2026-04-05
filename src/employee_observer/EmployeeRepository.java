package employee_observer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ── Observer interface ────────────────────────────────────────────────────────
interface EmployeeObserver {
	void onEmployeesChanged(List<Employee> employees);

	void onStatusMessage(String message);
}

// ── Subject ───────────────────────────────────────────────────────────────────
class EmployeeRepository {

	private final List<Employee> employees = new ArrayList<>();
	private final List<EmployeeObserver> observers = new ArrayList<>();
	private File currentFile;

	// ── Observer management ───────────────────────────────────────────────────

	void addObserver(EmployeeObserver o) {
		observers.add(o);
	}

	void removeObserver(EmployeeObserver o) {
		observers.remove(o);
	}

	private void notifyObservers() {
		List<Employee> view = Collections.unmodifiableList(employees);
		for (EmployeeObserver o : observers)
			o.onEmployeesChanged(view);
	}

	private void notifyStatus(String msg) {
		for (EmployeeObserver o : observers)
			o.onStatusMessage(msg);
	}

	// ── File operations ───────────────────────────────────────────────────────

	void setFile(File file) {
		this.currentFile = file;
		loadFromFile();
	}

	void loadFromFile() {
		employees.clear();
		if (currentFile == null || !currentFile.exists()) {
			notifyObservers();
			return;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(currentFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.trim().isEmpty())
					employees.add(Employee.fromFileString(line));
			}
			notifyObservers();
		} catch (Exception e) {
			notifyStatus("Error loading file: " + e.getMessage());
		}
	}

	void saveToFile() {
		if (currentFile == null)
			return;
		try (PrintWriter pw = new PrintWriter(new FileWriter(currentFile))) {
			for (Employee emp : employees)
				pw.println(emp.toFileString());
			notifyStatus("Saved successfully.");
		} catch (Exception e) {
			notifyStatus("Save error: " + e.getMessage());
		}
	}

	// ── CRUD operations ───────────────────────────────────────────────────────

	void addEmployee(Employee emp) {
		employees.add(emp);
		notifyObservers();
	}

	void deleteEmployee(int index) {
		if (index >= 0 && index < employees.size()) {
			employees.remove(index);
			notifyObservers();
		}
	}

	void promoteEmployee(int index, String newDepartment, double raise) {
		Employee emp = employees.get(index);
		emp.salary += raise;
		if (newDepartment != null && !newDepartment.isEmpty())
			emp.department = newDepartment;

		notifyObservers();
		notifyStatus("Employee promoted!\nNew Salary: " + emp.salary
				+ (newDepartment != null && !newDepartment.isEmpty() ? "\nNew Department: " + emp.department : ""));
	}

	// ── Query helpers ─────────────────────────────────────────────────────────

	List<Employee> getAll() {
		return Collections.unmodifiableList(employees);
	}

	double totalSalary(String company, String department) {
		return employees.stream().filter(e -> matches(e.company, company))
				.filter(e -> matches(e.department, department)).mapToDouble(e -> e.salary).sum();
	}

	long count(String company, String department) {
		return employees.stream().filter(e -> matches(e.company, company))
				.filter(e -> matches(e.department, department)).count();
	}

	private boolean matches(String field, String filter) {
		return filter == null || filter.isEmpty() || field.equalsIgnoreCase(filter);
	}
}
