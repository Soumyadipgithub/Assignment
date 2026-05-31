# 📘 JDBC Assignment — Student Database (Java + MySQL)

> A complete Java JDBC project demonstrating database connectivity, CRUD operations using `Statement`, `PreparedStatement`, and `ResultSet`.

---

## 📋 Table of Contents
1. [What This Project Does](#what-this-project-does)
2. [Prerequisites](#prerequisites)
3. [Step 1 — Install XAMPP](#step-1--install-xampp)
4. [Step 2 — Download MySQL Connector JAR](#step-2--download-mysql-connector-jar)
5. [Step 3 — Setup the Database](#step-3--setup-the-database)
6. [Step 4 — Compile & Run Programs](#step-4--compile--run-programs)
7. [Expected Output](#expected-output)
8. [File Descriptions](#file-descriptions)
9. [Troubleshooting](#troubleshooting)

---

## 🎯 What This Project Does

This project contains **4 Java programs** that connect to a MySQL database and perform:

| Program | Operations Covered |
|---|---|
| Program 1 | Connect to DB + Read data using `Statement` |
| Program 2 | Insert 2 students + Update marks using `PreparedStatement` |
| Program 3 | Delete a student + Show records before/after using `ResultSet` |
| Program 4 | Full menu-driven app (Insert, Update, Delete, Search, View) |

The database stores **Indian student records** — Koyel, Priya, Krishna, Dipali, Arjun, etc.

---

## ✅ Prerequisites

Before starting, make sure you have these installed:

| Software | Purpose | Version |
|---|---|---|
| Java JDK | To compile and run Java programs | 8 or higher |
| XAMPP | Runs MySQL server locally | Latest |
| MySQL Workbench | To manage the database with GUI | Latest |
| MySQL Connector/J | JDBC Driver JAR file | 9.x |

---

## Step 1 — Install XAMPP

> XAMPP gives you a MySQL server running locally on your computer.

1. Download XAMPP from: **https://www.apachefriends.org/download.html**
2. Install it (default settings are fine)
3. Open **XAMPP Control Panel**
4. Click **Start** next to **MySQL**
5. The MySQL row should turn **green** ✅

> ⚠️ Keep XAMPP running every time you use this project!

---

## Step 2 — Download MySQL Connector JAR

> This is the JDBC driver that lets Java talk to MySQL.

1. Go to: **https://dev.mysql.com/downloads/connector/j/**
2. Under **"Select Operating System"** choose: `Platform Independent`
3. Download the **ZIP file** (example: `mysql-connector-j-9.7.0.zip`)
4. Click **"No thanks, just start my download"** (skip the login)
5. **Right-click the downloaded ZIP → Extract All**
6. Inside the extracted folder, find this file:
   ```
   mysql-connector-j-9.7.0.jar
   ```
7. **Copy** that `.jar` file and paste it into this project folder:
   ```
   <wherever you saved this project folder>
   ```

Your project folder should now look like:
```
assign/
├── mysql-connector-j-9.7.0.jar   ← you just added this
├── db_setup.sql
├── Program1_StatementRetrieve.java
├── Program2_PreparedStatementInsertUpdate.java
├── Program3_DeleteSelectResultSet.java
├── Program4_CompleteApp.java
└── README.md
```

---

## Step 3 — Setup the Database

> This creates the database, table, and inserts starting student records.

1. Open **MySQL Workbench**
2. On the home screen, click the **"XAMPP Local"** box (root @ 127.0.0.1:3306)
3. If no password prompt appears, just click **OK** (XAMPP has no password by default)
4. In the query editor, **copy and paste** the contents of `db_setup.sql`:

```sql
CREATE DATABASE IF NOT EXISTS college_db;

USE college_db;

DROP TABLE IF EXISTS students;

CREATE TABLE IF NOT EXISTS students (
    id        INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL,
    branch    VARCHAR(50),
    marks     FLOAT,
    email     VARCHAR(100)
);

INSERT INTO students (name, branch, marks, email) VALUES
('Koyel',   'CSE',  88.5, 'koyel@college.edu'),
('Priya',   'ECE',  75.0, 'priya@college.edu'),
('Krishna', 'MECH', 65.3, 'krishna@college.edu');
```

5. Press **Ctrl + Shift + Enter** to run all statements
6. In the left panel under **Schemas**, right-click → **Refresh All**
7. You should see **`college_db`** appear ✅

---

## Step 4 — Compile & Run Programs

> Open **PowerShell** (or Command Prompt) in the project folder.

**How to open PowerShell in the project folder:**
- Open the project folder in File Explorer
- Click the address bar at the top, type `powershell`, press Enter

**Important:** Replace `mysql-connector-j-9.7.0.jar` with the exact filename of your downloaded JAR if it's different.

---

### ▶ Run Program 1 — Statement + Retrieve

```powershell
javac -cp ".;mysql-connector-j-9.7.0.jar" Program1_StatementRetrieve.java
java  -cp ".;mysql-connector-j-9.7.0.jar" Program1_StatementRetrieve
```

---

### ▶ Run Program 2 — PreparedStatement INSERT + UPDATE

```powershell
javac -cp ".;mysql-connector-j-9.7.0.jar" Program2_PreparedStatementInsertUpdate.java
java  -cp ".;mysql-connector-j-9.7.0.jar" Program2_PreparedStatementInsertUpdate
```

---

### ▶ Run Program 3 — DELETE + SELECT + ResultSet

```powershell
javac -cp ".;mysql-connector-j-9.7.0.jar" Program3_DeleteSelectResultSet.java
java  -cp ".;mysql-connector-j-9.7.0.jar" Program3_DeleteSelectResultSet
```

---

### ▶ Run Program 4 — Complete Menu App

```powershell
javac -cp ".;mysql-connector-j-9.7.0.jar" Program4_CompleteApp.java
java  -cp ".;mysql-connector-j-9.7.0.jar" Program4_CompleteApp
```

> Program 4 is interactive — a menu will appear. Type `1` to view students, `2` to insert, etc. Type `0` to exit.

---

## 📊 Expected Output

### Program 1
```
Driver loaded successfully.
Database connection established!

---------- Student Records ----------
ID    Name            Branch     Marks    Email
-----------------------------------------------------
1     Koyel           CSE        88.5     koyel@college.edu
2     Priya           ECE        75.0     priya@college.edu
3     Krishna         MECH       65.3     krishna@college.edu
-----------------------------------------------------

Connection closed.
```

### Program 2
```
Connection established!

INSERT 1 -> Rows affected: 1
INSERT 2 -> Rows affected: 1

Both records inserted successfully!

UPDATE -> Rows affected: 1
Koyel's record updated successfully!

Connection closed.
```

### Program 3
```
Connection established!

===== Records BEFORE Delete =====
ID    Name       Branch  Marks    Email
----------------------------------------------------
1     Koyel      CSE     95.0     koyel@college.edu
2     Priya      ECE     75.0     priya@college.edu
3     Krishna    MECH    65.3     krishna@college.edu
4     Dipali     IT      91.5     dipali@college.edu
5     Arjun      CIVIL   55.0     arjun@college.edu
----------------------------------------------------

DELETE -> Rows deleted: 1
Student 'Arjun' deleted successfully!

===== Records AFTER Delete =====
...Arjun is removed...

===== Scrollable ResultSet Demo =====
Last row  -> ID: 4, Name: Dipali
First row -> ID: 1, Name: Koyel
Row #2    -> ID: 2, Name: Priya

Connection closed.
```

### Program 4
```
==============================================
  Student Management System (JDBC Demo)
==============================================
Database connected successfully!

--- MENU ---
1. View all students
2. Insert a new student
3. Update student marks
4. Delete a student
5. Search student by name
0. Exit
Enter your choice:
```

---

## 📁 File Descriptions

| File | Description |
|---|---|
| `db_setup.sql` | SQL script to create database, table, and insert 3 Indian student records. Run this once in MySQL Workbench. |
| `Program1_StatementRetrieve.java` | Connects to DB using JDBC. Uses `Statement` to run a SELECT query and prints all students using `ResultSet`. |
| `Program2_PreparedStatementInsertUpdate.java` | Uses `PreparedStatement` to INSERT two new students (Dipali, Arjun) and UPDATE Koyel's marks. |
| `Program3_DeleteSelectResultSet.java` | Shows records before DELETE, deletes Arjun, shows records after. Also demos scrollable `ResultSet` navigation. |
| `Program4_CompleteApp.java` | Full menu-driven Java application. User can interactively view, insert, update, delete, and search students. |
| `mysql-connector-j-9.7.0.jar` | MySQL JDBC Driver. Must be downloaded separately and placed in this folder. |

---

## 🔧 Troubleshooting

### ❌ Error: `ClassNotFoundException: com.mysql.cj.jdbc.Driver`
**Cause:** The JAR file is not in the classpath  
**Fix:** Make sure the JAR file is in the same folder and your command includes `-cp ".;mysql-connector-j-9.7.0.jar"`

---

### ❌ Error: `Communications link failure`
**Cause:** MySQL is not running  
**Fix:** Open XAMPP Control Panel → Click **Start** next to MySQL

---

### ❌ Error: `Access denied for user 'root'@'localhost'`
**Cause:** Wrong password  
**Fix:** XAMPP uses an empty password. In each Java file, make sure:
```java
static final String PASSWORD = "";  // empty string, not "your_password"
```

---

### ❌ Error: `Unknown database 'college_db'`
**Cause:** You haven't run the SQL setup script yet  
**Fix:** Open MySQL Workbench → run the contents of `db_setup.sql`

---

### ❌ `javac` is not recognized
**Cause:** Java JDK is not installed or not added to PATH  
**Fix:**
1. Download JDK from: https://www.oracle.com/java/technologies/downloads/
2. Install it
3. Search "Environment Variables" in Windows → Edit PATH → Add the JDK `bin` folder path (e.g., `C:\Program Files\Java\jdk-21\bin`)

---

## 🎓 JDBC Key Concepts (For Exams)

### 5 Steps of JDBC
```
1. Load Driver      → Class.forName("com.mysql.cj.jdbc.Driver")
2. Get Connection   → DriverManager.getConnection(url, user, pass)
3. Create Statement → con.createStatement() or con.prepareStatement(sql)
4. Execute Query    → executeQuery() for SELECT, executeUpdate() for INSERT/UPDATE/DELETE
5. Close Resources  → rs.close(), st.close(), con.close()
```

### Statement vs PreparedStatement
| Feature | Statement | PreparedStatement |
|---|---|---|
| SQL Injection Safe | ❌ No | ✅ Yes |
| Pre-compiled | ❌ No | ✅ Yes |
| Performance (repeated) | Slower | Faster |
| Parameterized | ❌ No | ✅ Yes (`?` placeholders) |

### ResultSet Types
| Type | Movement |
|---|---|
| `TYPE_FORWARD_ONLY` | Forward only (default) |
| `TYPE_SCROLL_INSENSITIVE` | Forward + backward, no live updates |
| `TYPE_SCROLL_SENSITIVE` | Forward + backward, reflects DB changes |

---

*Made for college JDBC assignment — Java + MySQL + XAMPP*
