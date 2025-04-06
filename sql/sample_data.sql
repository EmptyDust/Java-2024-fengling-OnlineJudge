-- Sample Data Generation Script for Online Judge System
USE mydb;

-- ======= SAMPLE TEACHERS =======
INSERT INTO teachers (id, name, password, major) VALUES
(1002, 'Professor Johnson', '123456', 'Mathematics'),
(1003, 'Dr. Williams', '123456', 'Computer Science'),
(1004, 'Dr. Brown', '123456', 'Software Engineering'),
(1005, 'Professor Davis', '123456', 'Data Science'),
(1006, 'Dr. Miller', '123456', 'Artificial Intelligence'),
(1007, 'Professor Wilson', '123456', 'Information Systems'),
(1008, 'Dr. Taylor', '123456', 'Cybersecurity');

-- ======= SAMPLE STUDENTS =======
INSERT INTO students (id, name, password, major, enrollmentYear, class) VALUES
(2002, 'Jane Smith', '123456', 'Computer Science', '2022', 'CS-01'),
(2003, 'Michael Johnson', '123456', 'Data Science', '2022', 'DS-01'),
(2004, 'Emily Brown', '123456', 'Software Engineering', '2021', 'SE-02'),
(2005, 'David Wilson', '123456', 'Computer Science', '2023', 'CS-03'),
(2006, 'Sarah Davis', '123456', 'Artificial Intelligence', '2021', 'AI-01'),
(2007, 'James Miller', '123456', 'Cybersecurity', '2022', 'CS-02'),
(2008, 'Emma Taylor', '123456', 'Information Systems', '2023', 'IS-01'),
(2009, 'Daniel Anderson', '123456', 'Computer Science', '2022', 'CS-01'),
(2010, 'Olivia Thomas', '123456', 'Software Engineering', '2021', 'SE-01'),
(2011, 'William Jackson', '123456', 'Cybersecurity', '2023', 'CS-03'),
(2012, 'Sophia White', '123456', 'Data Science', '2022', 'DS-01'),
(2013, 'Benjamin Harris', '123456', 'Computer Science', '2021', 'CS-02'),
(2014, 'Isabella Martin', '123456', 'Artificial Intelligence', '2023', 'AI-01'),
(2015, 'Mason Thompson', '123456', 'Information Systems', '2022', 'IS-01');

-- ======= SAMPLE PROBLEMS =======
-- Programming problems
INSERT INTO problems (id, title, status, content, type, answer) VALUES
(2, 'Factorial Calculation', 'open', 'Calculate factorial of n', 'Programming', 'public int factorial(int n) {\n    if (n <= 1) return 1;\n    return n * factorial(n-1);\n}'),
(3, 'FizzBuzz', 'open', 'Implement FizzBuzz', 'Programming', 'for (int i = 1; i <= n; i++) {\n    if (i % 15 == 0) System.out.println("FizzBuzz");\n    else if (i % 3 == 0) System.out.println("Fizz");\n    else if (i % 5 == 0) System.out.println("Buzz");\n    else System.out.println(i);\n}'),
(4, 'String Reverse', 'open', 'Reverse a string', 'Programming', 'public String reverse(String s) {\n    return new StringBuilder(s).reverse().toString();\n}');

-- Multiple choice questions
INSERT INTO problems (id, title, status, content, type, optionA, optionB, optionC, optionD, answer) VALUES
(5, 'Java Features', 'open', 'Java is...', 'Choice', 'Object-Oriented', 'Functional', 'Procedural', 'All of the above', 'A'),
(6, 'Data Structures', 'open', 'Which is not a collection?', 'Choice', 'ArrayList', 'HashMap', 'String', 'HashSet', 'C'),
(7, 'Database Concepts', 'open', 'SQL stands for?', 'Choice', 'Structured Query Language', 'Simple Query Language', 'Sequential Query Language', 'Standard Query Language', 'A'),
(8, 'Algorithm Complexity', 'open', 'QuickSort average case?', 'Choice', 'O(n)', 'O(n log n)', 'O(n²)', 'O(log n)', 'B');

-- True/False questions
INSERT INTO problems (id, title, status, content, type, optionA, optionB, answer) VALUES
(9, 'Java Statement', 'open', 'Java is compiled to bytecode', 'TF', 'True', 'False', 'A'),
(10, 'OOP Concept', 'open', 'Encapsulation hides data', 'TF', 'True', 'False', 'A'),
(11, 'Database Statement', 'open', 'SQL is case-sensitive', 'TF', 'True', 'False', 'B'),
(12, 'Network Statement', 'open', 'HTTP is stateless', 'TF', 'True', 'False', 'A');

-- ======= SAMPLE PAPERS =======
INSERT INTO papers (id, title, content, author, date, time, duration, status) VALUES
(1, 'Java Basics Quiz', 'Test on fundamental Java concepts', '1001', CURDATE(), CURTIME(), 60, 'open'),
(2, 'Data Structures Exam', 'Comprehensive exam on data structures', '1003', CURDATE(), CURTIME(), 120, 'open'),
(3, 'Algorithms Test', 'Mid-term test on algorithms', '1002', CURDATE(), CURTIME(), 90, 'open'),
(4, 'Database Principles', 'Quiz on database fundamentals', '1005', CURDATE(), CURTIME(), 45, 'open'),
(5, 'Programming Practice', 'Practical programming assessment', '1004', CURDATE(), CURTIME(), 150, 'open');

-- Link problems to papers
UPDATE papers SET problemList = '1&2&3', scoreList = '30&30&40' WHERE id = 1;
UPDATE papers SET problemList = '4&5&6&7', scoreList = '25&25&25&25' WHERE id = 2;
UPDATE papers SET problemList = '8&9&10', scoreList = '40&30&30' WHERE id = 3;
UPDATE papers SET problemList = '7&11&12', scoreList = '40&30&30' WHERE id = 4;
UPDATE papers SET problemList = '1&2&3&4', scoreList = '25&25&25&25' WHERE id = 5;

-- ======= SAMPLE TASKS =======
INSERT INTO task (id, paperID, studentID, grade) VALUES
(1, 1, 2001, 85),
(2, 1, 2002, 92),
(3, 1, 2003, 78),
(4, 2, 2004, 88),
(5, 2, 2005, 75),
(6, 3, 2006, 95),
(7, 3, 2007, 82),
(8, 4, 2008, 90),
(9, 4, 2009, 85),
(10, 5, 2010, 79),
(11, 5, 2011, 91),
(12, 1, 2012, 84),
(13, 2, 2013, 88),
(14, 3, 2014, 76),
(15, 4, 2015, 93);

-- Update tasks with problem answers
UPDATE task SET problemAnswer = 'System.out.println("Hello World")&public int factorial(int n) {return n <= 1 ? 1 : n * factorial(n-1);}' WHERE id = 1;
UPDATE task SET problemAnswer = 'System.out.println("Hello World")&public int factorial(int n) {int result=1; for(int i=1;i<=n;i++) result*=i; return result;}' WHERE id = 2;
UPDATE task SET problemAnswer = 'System.out.println("Hello World")&// Incomplete solution' WHERE id = 3;
-- Add more problemAnswer updates as needed

-- ======= SAMPLE ANSWERS =======
INSERT INTO answer (id, paperID, problemID, studentID, content, stdAnswer) VALUES
(1, 1, 1, 2001, 'System.out.println("Hello World");', 'System.out.println("Hello World")'),
(2, 1, 2, 2001, 'public int factorial(int n) {return n <= 1 ? 1 : n * factorial(n-1);}', 'public int factorial(int n) {\n    if (n <= 1) return 1;\n    return n * factorial(n-1);\n}'),
(3, 1, 3, 2001, 'for(int i=1;i<=n;i++){if(i%15==0)System.out.println("FizzBuzz");else if(i%3==0)System.out.println("Fizz");else if(i%5==0)System.out.println("Buzz");else System.out.println(i);}', 'for (int i = 1; i <= n; i++) {\n    if (i % 15 == 0) System.out.println("FizzBuzz");\n    else if (i % 3 == 0) System.out.println("Fizz");\n    else if (i % 5 == 0) System.out.println("Buzz");\n    else System.out.println(i);\n}'),
(4, 2, 4, 2004, 'public String reverse(String s) {return new StringBuilder(s).reverse().toString();}', 'public String reverse(String s) {\n    return new StringBuilder(s).reverse().toString();\n}'),
(5, 2, 5, 2004, 'A', 'A'),
(6, 2, 6, 2004, 'C', 'C'),
(7, 2, 7, 2004, 'A', 'A'),
(8, 3, 8, 2006, 'B', 'B'),
(9, 3, 9, 2006, 'A', 'A'),
(10, 3, 10, 2006, 'A', 'A'),
(11, 4, 7, 2008, 'A', 'A'),
(12, 4, 11, 2008, 'B', 'B'),
(13, 4, 12, 2008, 'A', 'A'),
(14, 5, 1, 2010, 'System.out.println("Hello World!");', 'System.out.println("Hello World")'),
(15, 5, 2, 2010, 'public int factorial(int n) {int result=1; for(int i=1;i<=n;i++) result*=i; return result;}', 'public int factorial(int n) {\n    if (n <= 1) return 1;\n    return n * factorial(n-1);\n}'),
(16, 5, 3, 2010, 'for(int i=1;i<=n;i++){if(i%3==0 && i%5==0)System.out.println("FizzBuzz");else if(i%3==0)System.out.println("Fizz");else if(i%5==0)System.out.println("Buzz");else System.out.println(i);}', 'for (int i = 1; i <= n; i++) {\n    if (i % 15 == 0) System.out.println("FizzBuzz");\n    else if (i % 3 == 0) System.out.println("Fizz");\n    else if (i % 5 == 0) System.out.println("Buzz");\n    else System.out.println(i);\n}'),
(17, 5, 4, 2010, 'public String reverse(String s) {String result = ""; for(int i=s.length()-1; i>=0; i--) result += s.charAt(i); return result;}', 'public String reverse(String s) {\n    return new StringBuilder(s).reverse().toString();\n}');
