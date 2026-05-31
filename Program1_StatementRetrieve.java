// ============================================================
// Program 1: Basic JDBC - Connect to database and retrieve data
//            using Statement
// ============================================================

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Program1_StatementRetrieve {

    // --- Database Configuration ---
    static final String URL      = "jdbc:mysql://localhost:3306/college_db";
    static final String USERNAME = "root";       // change if needed
    static final String PASSWORD = ""; // XAMPP default has no password

    public static void main(String[] args) {

        Connection con = null;
        Statement  st  = null;
        ResultSet  rs  = null;

        try {
            // Step 1: Load the JDBC Driver (optional from Java 6+, but good practice)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully.");

            // Step 2: Establish the connection
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established!\n");

            // Step 3: Create a Statement object
            st = con.createStatement();

            // Step 4: Write and execute the SQL query
            String sql = "SELECT * FROM students";
            rs = st.executeQuery(sql);

            // Step 5: Process the ResultSet
            System.out.println("---------- Student Records ----------");
            System.out.printf("%-5s %-15s %-10s %-8s %-25s%n",
                              "ID", "Name", "Branch", "Marks", "Email");
            System.out.println("-----------------------------------------------------");

            while (rs.next()) {
                int    id     = rs.getInt("id");
                String name   = rs.getString("name");
                String branch = rs.getString("branch");
                float  marks  = rs.getFloat("marks");
                String email  = rs.getString("email");

                System.out.printf("%-5d %-15s %-10s %-8.1f %-25s%n",
                                  id, name, branch, marks, email);
            }
            System.out.println("-----------------------------------------------------");

        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: JDBC Driver not found! Add mysql-connector.jar to classpath.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Step 6: Always close resources in finally block (reverse order)
            try { if (rs  != null) rs.close();  } catch (Exception e) { e.printStackTrace(); }
            try { if (st  != null) st.close();  } catch (Exception e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
            System.out.println("\nConnection closed.");
        }
    }
}
