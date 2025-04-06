-- MySQL Database Setup Script for Online Judge System

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

-- Drop tables if they exist to ensure clean setup
DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS papers;
DROP TABLE IF EXISTS problems;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS teachers;

-- Create teachers table
CREATE TABLE teachers (
    id INT(11) PRIMARY KEY,
    name VARCHAR(25) NOT NULL,
    password VARCHAR(30),
    major VARCHAR(30)
);

-- Create students table
CREATE TABLE students (
    id INT(11) PRIMARY KEY,
    name VARCHAR(25),
    password VARCHAR(30),
    major VARCHAR(20),
    enrollmentYear VARCHAR(20),
    class VARCHAR(20),
    papersID VARCHAR(1000)
);

-- Create problems table
CREATE TABLE problems (
    id INT(11) PRIMARY KEY,
    title VARCHAR(25),
    status VARCHAR(25),
    content VARCHAR(25),
    optionA VARCHAR(100),
    optionB VARCHAR(100),
    optionC VARCHAR(100),
    optionD VARCHAR(100),
    optionE VARCHAR(100),
    optionF VARCHAR(100),
    optionG VARCHAR(100),
    optionH VARCHAR(100),
    type VARCHAR(100),
    answer VARCHAR(1000)
);

-- Create papers table
CREATE TABLE papers (
    id INT(11) PRIMARY KEY,
    title VARCHAR(25),
    content VARCHAR(500),
    author VARCHAR(25),
    date DATE,
    time TIME,
    problemList VARCHAR(1000),
    scoreList VARCHAR(1000),
    duration INT(11),
    status VARCHAR(30)
);

-- Create task table
CREATE TABLE task (
    id INT(11) PRIMARY KEY,
    paperID INT(11),
    studentID INT(11),
    grade INT(30),
    problemAnswer VARCHAR(1000),
    FOREIGN KEY (paperID) REFERENCES papers(id) ON DELETE CASCADE,
    FOREIGN KEY (studentID) REFERENCES students(id) ON DELETE CASCADE
);

-- Create answer table
CREATE TABLE answer (
    id INT(11) PRIMARY KEY,
    paperID INT(11),
    problemID INT(11),
    studentID INT(11),
    content VARCHAR(100),
    stdAnswer VARCHAR(100),
    FOREIGN KEY (paperID) REFERENCES papers(id) ON DELETE CASCADE,
    FOREIGN KEY (problemID) REFERENCES problems(id) ON DELETE CASCADE,
    FOREIGN KEY (studentID) REFERENCES students(id) ON DELETE CASCADE
);

-- Insert default admin account (ID: 0, password: pwd)
INSERT INTO teachers (id, name, password, major) 
VALUES (0, 'admin', 'pwd', 'Administration');

-- Sample data for testing (optional)
INSERT INTO teachers (id, name, password, major) 
VALUES (1001, 'Professor Smith', '123456', 'Computer Science');

INSERT INTO students (id, name, password, major, enrollmentYear, class) 
VALUES (2001, 'John Doe', '123456', 'Computer Science', '2022', 'CS-01');

INSERT INTO problems (id, title, status, content, type, answer) 
VALUES (1, 'Hello World', 'open', 'Print Hello World', 'Programming', 'System.out.println("Hello World")');
