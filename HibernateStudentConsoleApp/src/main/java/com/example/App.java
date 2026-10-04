package com.example;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static StudentDAO studentDAO;

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {

            studentDAO = new StudentDAO(sessionFactory);

            showWelcome();

            boolean running = true;

            while (running) {
                showMenu();
                int choice = readInt("Enter your choice: ");

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> updateStudent();
                    case 3 -> searchStudent();
                    case 4 -> viewAllStudents();
                    case 5 -> deleteStudent();
                    case 6 -> {
                        running = false;
                        System.out.println();
                        System.out.println("Thank you for using Student Management System!");
                    }
                    default -> printError("Invalid choice. Please select 1 to 6.");
                }
            }

        } catch (Exception e) {
            System.out.println();
            printError("Unable to start application.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void showWelcome() {
        System.out.println();
        System.out.println("==============================================================");
        System.out.println("              STUDENT MANAGEMENT SYSTEM                       ");
        System.out.println("              Hibernate + MySQL + Maven                      ");
        System.out.println("==============================================================");
        System.out.println("              Database connection established                 ");
        System.out.println("==============================================================");
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("--------------------------------------------------------------");
        System.out.println("                         MAIN MENU                            ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("  1. Add Student");
        System.out.println("  2. Update Student");
        System.out.println("  3. Search Student");
        System.out.println("  4. View All Students");
        System.out.println("  5. Delete Student");
        System.out.println("  6. Exit");
        System.out.println("--------------------------------------------------------------");
    }

    private static void addStudent() {
        System.out.println();
        System.out.println("====================== ADD STUDENT ===========================");

        int id = readPositiveInt("Student ID: ");

        if (studentDAO.getStudent(id) != null) {
            printError("Student ID " + id + " already exists.");
            return;
        }

        String name = readRequired("Student Name: ");
        String email = readEmail("Email: ");
        String course = readRequired("Course: ");

        Student student = new Student(id, name, email, course);

        if (studentDAO.addStudent(student)) {
            printSuccess("Student added successfully.");
            System.out.println(student);
        } else {
            printError("Student could not be added.");
        }
    }

    private static void updateStudent() {
        System.out.println();
        System.out.println("===================== UPDATE STUDENT =========================");

        int id = readPositiveInt("Enter Student ID: ");
        Student existing = studentDAO.getStudent(id);

        if (existing == null) {
            printError("Student with ID " + id + " was not found.");
            return;
        }

        System.out.println();
        System.out.println("Current record:");
        System.out.println(existing);
        System.out.println();
        System.out.println("Enter new values:");

        String name = readRequired("New Name: ");
        String email = readEmail("New Email: ");
        String course = readRequired("New Course: ");

        Student updated = new Student(id, name, email, course);

        if (studentDAO.updateStudent(updated)) {
            printSuccess("Student updated successfully.");
            System.out.println(studentDAO.getStudent(id));
        } else {
            printError("Student could not be updated.");
        }
    }

    private static void searchStudent() {
        System.out.println();
        System.out.println("===================== SEARCH STUDENT =========================");

        int id = readPositiveInt("Enter Student ID: ");
        Student student = studentDAO.getStudent(id);

        if (student != null) {
            printSuccess("Student found.");
            System.out.println(student);
        } else {
            printError("No student found with ID " + id + ".");
        }
    }

    private static void viewAllStudents() {
        System.out.println();
        System.out.println("===================== ALL STUDENTS ===========================");

        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.printf(
            "%-5s | %-25s | %-32s | %s%n",
            "ID", "NAME", "EMAIL", "COURSE"
        );
        System.out.println(
            "-----+---------------------------+----------------------------------+------------------------------"
        );

        for (Student student : students) {
            System.out.println(student);
        }

        System.out.println();
        System.out.println("Total students: " + students.size());
    }

    private static void deleteStudent() {
        System.out.println();
        System.out.println("===================== DELETE STUDENT ========================");

        int id = readPositiveInt("Enter Student ID: ");
        Student student = studentDAO.getStudent(id);

        if (student == null) {
            printError("No student found with ID " + id + ".");
            return;
        }

        System.out.println("Student to delete:");
        System.out.println(student);

        String confirmation = readRequired("Type YES to confirm deletion: ");

        if (!confirmation.equalsIgnoreCase("YES")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        if (studentDAO.deleteStudent(id)) {
            printSuccess("Student deleted successfully.");
        } else {
            printError("Student could not be deleted.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                printError("Please enter a valid number.");
            }
        }
    }

    private static int readPositiveInt(String message) {
        while (true) {
            int value = readInt(message);

            if (value > 0) {
                return value;
            }

            printError("ID must be greater than 0.");
        }
    }

    private static String readRequired(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            printError("This field cannot be empty.");
        }
    }

    private static String readEmail(String message) {
        Pattern pattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );

        while (true) {
            String email = readRequired(message);

            if (pattern.matcher(email).matches()) {
                return email;
            }

            printError("Please enter a valid email address.");
        }
    }

    private static void printSuccess(String message) {
        System.out.println();
        System.out.println("[SUCCESS] " + message);
    }

    private static void printError(String message) {
        System.out.println();
        System.out.println("[ERROR] " + message);
    }
}
