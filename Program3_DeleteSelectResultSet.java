// ============================================================
// Program 3: JDBC - DELETE and SELECT operations using ResultSet
// ============================================================

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Program3_DeleteSelectResultSet {

    static final String URL      = "jdbc:mysql://localhost:3306/college_db";
    static final String USERNAME = "root";
    static final String PASSWORD = ""; // XAMPP default has no password

    public static void main(String[] args) {

        Connection        con = null;
        Statement         st  = null;
        PreparedStatement pst = null;
        ResultSet         rs  = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection established!\n");

            // -------------------------------------------------------
            // PART A: SELECT using ResultSet (show records BEFORE delete)
            // -------------------------------------------------------
            System.out.println("===== Records BEFORE Delete =====");
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM students");
            printResultSet(rs);
            rs.close();
            st.close();

            // -------------------------------------------------------
            // PART B: DELETE using PreparedStatement
            // -------------------------------------------------------
            // Delete student with name = 'Arjun'
            String deleteSQL = "DELETE FROM students WHERE name = ?";
            pst = con.prepareStatement(deleteSQL);
            pst.setString(1, "Arjun");

            int rowsDeleted = pst.executeUpdate();
            System.out.println("DELETE -> Rows deleted: " + rowsDeleted);
            if (rowsDeleted > 0) {
                System.out.println("Student 'Arjun' deleted successfully!\n");
            } else {
                System.out.println("No student named 'Arjun' found.\n");
            }
            pst.close();

            // -------------------------------------------------------
            // PART C: SELECT again (show records AFTER delete)
            // -------------------------------------------------------
            System.out.println("===== Records AFTER Delete =====");
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM students ORDER BY id");
            printResultSet(rs);

            // -------------------------------------------------------
            // BONUS: ResultSet Navigation - scrollable cursor example
            // -------------------------------------------------------
            System.out.println("\n===== Scrollable ResultSet Demo =====");
            // TYPE_SCROLL_INSENSITIVE allows moving cursor back and forth
            Statement scrollSt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            ResultSet scrollRs = scrollSt.executeQuery("SELECT * FROM students ORDER BY id");

            // Move to last row
            if (scrollRs.last()) {
                System.out.println("Last row  -> ID: " + scrollRs.getInt("id") +
                                   ", Name: " + scrollRs.getString("name"));
            }
            // Move to first row
            if (scrollRs.first()) {
                System.out.println("First row -> ID: " + scrollRs.getInt("id") +
                                   ", Name: " + scrollRs.getString("name"));
            }
            // Move to absolute row 2
            if (scrollRs.absolute(2)) {
                System.out.println("Row #2    -> ID: " + scrollRs.getInt("id") +
                                   ", Name: " + scrollRs.getString("name"));
            }

            scrollRs.close();
            scrollSt.close();

        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: JDBC Driver not found!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs  != null) rs.close();  } catch (Exception e) { e.printStackTrace(); }
            try { if (pst != null) pst.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (st  != null) st.close();  } catch (Exception e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
            System.out.println("\nConnection closed.");
        }
    }

    // Helper method to print ResultSet nicely
    static void printResultSet(ResultSet rs) throws Exception {
        System.out.printf("%-5s %-15s %-10s %-8s %-25s%n",
                          "ID", "Name", "Branch", "Marks", "Email");
        System.out.println("-----------------------------------------------------");
        while (rs.next()) {
            System.out.printf("%-5d %-15s %-10s %-8.1f %-25s%n",
                              rs.getInt("id"),
                              rs.getString("name"),
                              rs.getString("branch"),
                              rs.getFloat("marks"),
                              rs.getString("email"));
        }
        System.out.println("-----------------------------------------------------\n");
    }
}
