-- ============================================================
-- Run this SQL script in MySQL Workbench FIRST before any Java program
-- ============================================================

-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS college_db;

-- Step 2: Switch to the database
USE college_db;

-- Step 3: Drop table if already exists (for fresh setup)
DROP TABLE IF EXISTS students;

-- Step 4: Create the students table
CREATE TABLE IF NOT EXISTS students (
    id        INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(100) NOT NULL,
    branch    VARCHAR(50),
    marks     FLOAT,
    email     VARCHAR(100)
);

-- Step 5: Insert 3 initial Indian student records
INSERT INTO students (name, branch, marks, email) VALUES
('Koyel',   'CSE',  88.5, 'koyel@college.edu'),
('Priya',   'ECE',  75.0, 'priya@college.edu'),
('Krishna', 'MECH', 65.3, 'krishna@college.edu');
