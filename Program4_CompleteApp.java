// ============================================================
// Program 4: Complete Java Application - INSERT, UPDATE, DELETE
//            with a menu-driven interface
// ============================================================

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Program4_CompleteApp {

    static final String URL      = "jdbc:mysql://localhost:3306/college_db";
    static final String USERNAME = "root";
    static final String PASSWORD = ""; // XAMPP default has no password

    static Connection con = null;

    public static void main(String[] args) {
        try {
            // Establish connection once for the whole application
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("==============================================");
            System.out.println("  Student Management System (JDBC Demo)       ");
            System.out.println("==============================================");
            System.out.println("Database connected successfully!\n");

            Scanner sc = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n--- MENU ---");
                System.out.println("1. View all students");
                System.out.println("2. Insert a new student");
                System.out.println("3. Update student marks");
                System.out.println("4. Delete a student");
                System.out.println("5. Search student by name");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1: viewAllStudents();          break;
                    case 2: insertStudent(sc);          break;
                    case 3: updateStudentMarks(sc);     break;
                    case 4: deleteStudent(sc);          break;
                    case 5: searchStudentByName(sc);    break;
                    case 0: System.out.println("Exiting... Goodbye!"); break;
                    default: System.out.println("Invalid choice! Please try again.");
                }

            } while (choice != 0);

            sc.close();

        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: JDBC Driver not found! Add mysql-connector.jar to classpath.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
            System.out.println("Connection closed.");
        }
    }

    // ----------------------------------------------------------
    // 1. VIEW ALL STUDENTS - SELECT with Statement
    // ----------------------------------------------------------
    static void viewAllStudents() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students ORDER BY id");

            System.out.println("\n--- All Students ---");
            System.out.printf("%-5s %-15s %-10s %-8s %-25s%n",
                              "ID", "Name", "Branch", "Marks", "Email");
            System.out.println("-------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-5d %-15s %-10s %-8.1f %-25s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("branch"),
                        rs.getFloat("marks"),
                        rs.getString("email"));
            }
            if (!found) System.out.println("No records found.");
            System.out.println("-------------------------------------------------------");

            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("ERROR fetching students: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------
    // 2. INSERT STUDENT - PreparedStatement
    // ----------------------------------------------------------
    static void insertStudent(Scanner sc) {
        try {
            System.out.print("Enter Name   : ");
            String name = sc.nextLine();

            System.out.print("Enter Branch : ");
            String branch = sc.nextLine();

            System.out.print("Enter Marks  : ");
            float marks = sc.nextFloat();
            sc.nextLine();

            System.out.print("Enter Email  : ");
            String email = sc.nextLine();

            String sql = "INSERT INTO students (name, branch, marks, email) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, branch);
            pst.setFloat (3, marks);
            pst.setString(4, email);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Student inserted successfully! (" + rows + " row affected)");
            }
            pst.close();
        } catch (Exception e) {
            System.out.println("ERROR inserting student: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------
    // 3. UPDATE STUDENT MARKS - PreparedStatement
    // ----------------------------------------------------------
    static void updateStudentMarks(Scanner sc) {
        try {
            System.out.print("Enter student name to update : ");
            String name = sc.nextLine();

            System.out.print("Enter new marks              : ");
            float newMarks = sc.nextFloat();
            sc.nextLine();

            String sql = "UPDATE students SET marks = ? WHERE name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setFloat (1, newMarks);
            pst.setString(2, name);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Marks updated successfully for '" + name + "'!");
            } else {
                System.out.println("No student found with name '" + name + "'.");
            }
            pst.close();
        } catch (Exception e) {
            System.out.println("ERROR updating student: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------
    // 4. DELETE STUDENT - PreparedStatement
    // ----------------------------------------------------------
    static void deleteStudent(Scanner sc) {
        try {
            System.out.print("Enter student name to delete : ");
            String name = sc.nextLine();

            String sql = "DELETE FROM students WHERE name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Student '" + name + "' deleted successfully! (" + rows + " row removed)");
            } else {
                System.out.println("No student found with name '" + name + "'.");
            }
            pst.close();
        } catch (Exception e) {
            System.out.println("ERROR deleting student: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------
    // 5. SEARCH STUDENT BY NAME - PreparedStatement + ResultSet
    // ----------------------------------------------------------
    static void searchStudentByName(Scanner sc) {
        try {
            System.out.print("Enter name to search : ");
            String name = sc.nextLine();

            // Use LIKE for partial matching
            String sql = "SELECT * FROM students WHERE name LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + name + "%");

            ResultSet rs = pst.executeQuery();

            System.out.println("\n--- Search Results for '" + name + "' ---");
            System.out.printf("%-5s %-15s %-10s %-8s %-25s%n",
                              "ID", "Name", "Branch", "Marks", "Email");
            System.out.println("-------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-5d %-15s %-10s %-8.1f %-25s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("branch"),
                        rs.getFloat("marks"),
                        rs.getString("email"));
            }
            if (!found) System.out.println("No student found matching '" + name + "'.");
            System.out.println("-------------------------------------------------------");

            rs.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("ERROR searching student: " + e.getMessage());
        }
    }
}
