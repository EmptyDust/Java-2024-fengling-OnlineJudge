# 🚀 Online Judge System

<div align="center">
  
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-007396?style=for-the-badge&logo=java&logoColor=white)
![Apache POI](https://img.shields.io/badge/Apache_POI-D22128?style=for-the-badge&logo=apache&logoColor=white)

</div>

## 📝 Description

The Online Judge System is a comprehensive educational platform designed for creating, managing, and evaluating programming assignments and exams. This system facilitates interaction between teachers and students, allowing teachers to create problem sets and papers while enabling students to submit their solutions for automatic evaluation.

## ✨ Features

### For Administrators
- 👨‍💼 User management (teachers and students)
- 📊 System-wide monitoring and configuration

### For Teachers
- 📝 Create and manage various types of problems (multiple choice, fill-in-the-blank, true/false)
- 📚 Organize problems into papers/exams
- ⏱️ Set timing constraints for exams
- 🔍 Review student submissions and performance
- 📋 Import student data from Excel files

### For Students
- 📄 View assigned papers and problems
- ✍️ Submit solutions to assigned problems
- 📊 Track progress and view scores
- 🧪 Practice with available problem sets

## 🔧 Technology Stack

- **Backend**: Java
- **Database**: MySQL
- **UI Framework**: Java Swing
- **External Libraries**: 
  - Apache POI (for Excel file operations)
  - MySQL Connector/J

## 📋 Database Structure

The system uses a MySQL database with the following structure:

| Table    | Description                                                                               |
| -------- | ----------------------------------------------------------------------------------------- |
| teachers | Stores teacher information including ID, name, password, and major                        |
| students | Contains student details like ID, name, password, major, enrollment year, and class       |
| problems | Stores all problems with various attributes including type, content, options, and answers |
| papers   | Represents exams/assignments with problems, scores, duration, and status                  |
| task     | Links students to papers and tracks their grades                                          |
| answer   | Records student responses to specific problems                                            |

## 🚀 Installation

### Prerequisites
- JDK 21 or higher
- MySQL 8.0 or higher
- MySQL Connector/J driver

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/emptydust/Java-2024-fengling-OnlineJudge.git
   cd Java-2024-fengling-OnlineJudge
   ```

2. **Set up the database**
   - Create a MySQL database
   - Run the SQL scripts in the project's SQL directory to set up the schema

3. **Configure database connection**
   - Update the connection parameters in the DatabaseManager class

4. **Build and run the project**
   - Using an IDE like IntelliJ IDEA or Eclipse
   - Or via command line with javac/java

## 💻 Usage

### Administrator
1. Launch the application and log in as an administrator
2. Use the manager interface to manage teachers and students

### Teacher
1. Log in with teacher credentials
2. Create problems via the Problem Management interface
3. Organize problems into papers, set timing and access restrictions
4. Review student submissions and assign grades

### Student
1. Log in with student credentials
2. View available papers
3. Complete assignments within the specified time frame
4. Review grades and feedback

## 👥 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## 📞 Contact

For any questions or feedback, please contact [fenglingyexing@gmail.com](mailto:fenglingyexing@gmail.com).

---

<div align="center">
  <sub>Built with ❤️ by the fengling team</sub>
</div>
