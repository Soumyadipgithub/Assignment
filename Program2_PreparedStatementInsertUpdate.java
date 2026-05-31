// ============================================================
// Program 2: JDBC using PreparedStatement - INSERT and UPDATE
// ============================================================

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Program2_PreparedStatementInsertUpdate {

    static final String URL      = "jdbc:mysql://localhost:3306/college_db";
    static final String USERNAME = "root";
    static final String PASSWORD = ""; // XAMPP default has no password

    public static void main(String[] args) {

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established!\n");

            // -------------------------------------------------------
            // PART A: INSERT using PreparedStatement
            // -------------------------------------------------------
            // The '?' are placeholders; values are set safely (prevents SQL injection)
            String insertSQL = "INSERT INTO students (name, branch, marks, email) " +
                               "VALUES (?, ?, ?, ?)";

            PreparedStatement pstInsert = con.prepareStatement(insertSQL);

            // Insert Row 1 - Dipali
            pstInsert.setString(1, "Dipali");
            pstInsert.setString(2, "IT");
            pstInsert.setFloat (3, 91.5f);
            pstInsert.setString(4, "dipali@college.edu");

            int rowsInserted1 = pstInsert.executeUpdate();
            System.out.println("INSERT 1 -> Rows affected: " + rowsInserted1);

            // Insert Row 2 (reuse the same PreparedStatement - efficient!)
            pstInsert.setString(1, "Arjun");
            pstInsert.setString(2, "CIVIL");
            pstInsert.setFloat (3, 55.0f);
            pstInsert.setString(4, "arjun@college.edu");

            int rowsInserted2 = pstInsert.executeUpdate();
            System.out.println("INSERT 2 -> Rows affected: " + rowsInserted2);

            pstInsert.close();
            System.out.println("\nBoth records inserted successfully!\n");

            // -------------------------------------------------------
            // PART B: UPDATE using PreparedStatement
            // -------------------------------------------------------
            // Update the marks of 'Koyel' (id = 1) to 95.0
            String updateSQL = "UPDATE students SET marks = ?, branch = ? WHERE name = ?";

            PreparedStatement pstUpdate = con.prepareStatement(updateSQL);
            pstUpdate.setFloat (1, 95.0f);
            pstUpdate.setString(2, "CSE");
            pstUpdate.setString(3, "Koyel");

            int rowsUpdated = pstUpdate.executeUpdate();
            System.out.println("UPDATE -> Rows affected: " + rowsUpdated);

            if (rowsUpdated > 0) {
                System.out.println("Koyel's record updated successfully!");
            } else {
                System.out.println("No matching record found to update.");
            }

            pstUpdate.close();

        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: JDBC Driver not found!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
            System.out.println("\nConnection closed.");
        }
    }
}
