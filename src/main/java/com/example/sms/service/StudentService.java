package com.example.sms.service;

import com.example.sms.model.Student;
import com.example.sms.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public boolean existsById(int id) {
        return repository.findById(id) != null;
    }

    public boolean existsByName(String name) {
        // Inefficient for large datasets but simpler for this migration without adding
        // custom query
        // A better approach would be repository.countByName(name) > 0
        return repository.findAll().stream().anyMatch(s -> s.getName().equals(name));
    }

    public Student getStudentById(int id) {
        return repository.findById(id);
    }

    // Add Student
    public void addStudent(String name, int id, double gpa, String year, String department) {
        if (existsById(id)) {
            System.out.println("Student with ID " + id + " already exists!\n");
            return;
        }
        Student student = new Student(id, name, gpa, year, department);
        repository.save(student);
        System.out.println("Student added successfully!\n");
    }

    // Remove Student
    public void removeStudentByID(int id) {
        if (repository.deleteById(id) > 0) {
            System.out.println("Student removed successfully!\n");
        } else {
            System.out.println("Student not found!\n");
        }
    }

    // Update Student
    public void updateStudentByID(int id, String newName, double newGpa, String newYear, String newDepartment) {
        Student student = repository.findById(id);
        if (student != null) {
            if (newName != null)
                student.setName(newName);
            if (newGpa != -1)
                student.setGpa(newGpa);
            if (newYear != null)
                student.setYear(newYear);
            if (newDepartment != null)
                student.setDepartment(newDepartment);
            repository.update(student);
            System.out.println("Student updated successfully!\n");
        } else {
            System.out.println("Student not found!\n");
        }
    }

    // Search Student
    public void searchByID(int id) {
        Student student = repository.findById(id);
        if (student != null) {
            System.out.println("Student found!");
            printHeader();
            printStudent(student);
            System.out.println();
        } else {
            System.out.println("Student not found!\n");
        }
    }

    // List and Sort
    public void listAndSortAllStudents(String sortBy) {
        List<Student> students = repository.findAll();
        if (students.isEmpty()) {
            System.out.println("No students found!\n");
            return;
        }

        switch (sortBy) {
            case "GPA":
                students.sort((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()));
                break;
            case "ID":
                students.sort(Comparator.comparingInt(Student::getId));
                break;
            case "Name":
                students.sort(Comparator.comparing(Student::getName));
                break;
            case "Department":
                students.sort(Comparator.comparing(Student::getDepartment));
                break;
            case "Year":
                students.sort(Comparator.comparingInt(s -> switch (s.getYear()) {
                    case "First" -> 1;
                    case "Second" -> 2;
                    case "Third" -> 3;
                    case "Fourth" -> 4;
                    default -> 0;
                }));
                break;
        }

        System.out.println("\nList of Students:");
        printHeader();
        System.out.println("---------------------------------------------------------------------");
        for (Student student : students) {
            printStudent(student);
        }
        System.out.println();
    }

    // Filter by GPA
    public void filterByGPA(double gpa) {
        List<Student> students = repository.findByGpa(gpa);
        if (students.isEmpty()) {
            System.out.println("No students found with GPA of " + gpa + "!\n");
        } else {
            System.out.println("Students with GPA of " + gpa + ":");
            printHeader();
            System.out.println("------------------------------------------------------------------");
            for (Student student : students) {
                printStudent(student);
            }
            System.out.println();
        }
    }

    // Filter by Year
    public void filterByYear(String year) {
        List<Student> students = repository.findByYear(year);
        if (students.isEmpty()) {
            System.out.println("No students found in " + year + " year!\n");
        } else {
            System.out.println("Students in " + year + " year:");
            printHeader();
            System.out.println("------------------------------------------------------------------");
            for (Student student : students) {
                printStudent(student);
            }
            System.out.println();
        }
    }

    // Filter by Department
    public void filterByDepartment(String department) {
        List<Student> students = repository.findByDepartment(department);
        if (students.isEmpty()) {
            System.out.println("No students found in " + department + " department!\n");
        } else {
            System.out.println("Students in " + department + " department:");
            printHeader();
            System.out.println("------------------------------------------------------------------");
            for (Student student : students) {
                printStudent(student);
            }
            System.out.println();
        }
    }

    // Count Total
    public void countTotalStudents() {
        System.out.println("The Total number of students: " + repository.count());
        System.out.println();
    }

    // Calculate Average GPA
    public void calculateAverageGPA() {
        List<Student> students = repository.findAll();
        if (students.isEmpty()) {
            System.out.println("No students to calculate average GPA.\n");
            return;
        }
        double sum = students.stream().mapToDouble(Student::getGpa).sum();
        System.out.println("The Average GPA: " + String.format("%.2f", sum / students.size()));
        System.out.println();
    }

    // Display Top 5
    public void displayTop5() {
        List<Student> students = repository.findAll();
        students.sort((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa())); // Sort by GPA desc

        System.out.println("------------------------------ TOP 5 PERFORMING STUDENTS ------------------------------\n");
        String[] years = { "First", "Second", "Third", "Fourth" };

        for (String year : years) {
            System.out.println(year + " Year:");
            System.out.printf(" %-23s  |  %-8s  |  %-4s  |  %-6s  |  %-5s%n",
                    "Name", "ID", "GPA", "Year", "Department");
            System.out.println("-------------------------------------------------------------------------");

            int count = 1;
            boolean isFound = false;

            for (Student student : students) {
                if (student.getYear().equals(year) && count <= 5 && student.getGpa() >= 2.0) {
                    isFound = true;
                    System.out.print(" " + (count++) + ". ");
                    printStudent(student);
                }
            }

            if (!isFound)
                System.out.println(" No students found!\n");
            else
                System.out.println();
        }
    }

    // Display Failing
    public void displayFailingStudents() {
        System.out.println("-------------------- FAILING STUDENTS (who have GPA less than 2.0) --------------------\n");
        String[] years = { "First", "Second", "Third", "Fourth" };
        List<Student> students = repository.findAll();

        for (String year : years) {
            System.out.println(year + " Year:");
            printHeader();
            System.out.println("----------------------------------------------------------------------");

            boolean isFailing = false;
            for (Student student : students) {
                if (student.getYear().equals(year) && student.getGpa() < 2.0) {
                    isFailing = true;
                    printStudent(student);
                }
            }

            if (!isFailing)
                System.out.println(" No failing students found!\n");
            else
                System.out.println();
        }
        System.out.println();
    }

    // Generate Summary
    public void generateSummary() {
        if (repository.count() == 0) {
            System.out.println("No students found!\n");
            return;
        }

        System.out.println("\n------------------------------ CLASS PERFORMANCE ------------------------------\n");
        calculateAverageGPA();
        countTotalStudents();
        // countStudentsByYear logic embedded here or called
        countStudentsByYear();
        displayTop5();
        displayFailingStudents();
    }

    public void countStudentsByYear() {
        List<Student> students = repository.findAll();
        int totalFirst = 0, totalSecond = 0, totalThird = 0, totalFourth = 0;
        int successfulFirst = 0, successfulSecond = 0, successfulThird = 0, successfulFourth = 0;

        for (Student student : students) {
            boolean success = student.getGpa() >= 2.0;
            switch (student.getYear()) {
                case "First" -> {
                    totalFirst++;
                    if (success)
                        successfulFirst++;
                }
                case "Second" -> {
                    totalSecond++;
                    if (success)
                        successfulSecond++;
                }
                case "Third" -> {
                    totalThird++;
                    if (success)
                        successfulThird++;
                }
                case "Fourth" -> {
                    totalFourth++;
                    if (success)
                        successfulFourth++;
                }
            }
        }

        System.out.println("Year-wise Student Count:");
        printYearStat("First", totalFirst, successfulFirst);
        printYearStat("Second", totalSecond, successfulSecond);
        printYearStat("Third", totalThird, successfulThird);
        printYearStat("Fourth", totalFourth, successfulFourth);
        System.out.println();
    }

    private void printYearStat(String year, int total, int success) {
        double rate = total == 0 ? 0.0 : (success * 100.0 / total);
        System.out.println(
                " - " + year + " Year: " + total + ", With Success rate of " + String.format("%.3f", rate) + "%");
    }

    private void printHeader() {
        System.out.printf("%-20s  |  %-8s  |  %-4s  |  %-6s  |  %-5s%n",
                "Name", "ID", "GPA", "Year", "Dept");
    }

    private void printStudent(Student student) {
        System.out.printf("%-20s  |  %-8s  |  %-4.2f  |  %-6s  |  %-5s%n",
                student.getName(), student.getId(), student.getGpa(), student.getYear(), student.getDepartment());
    }
}
