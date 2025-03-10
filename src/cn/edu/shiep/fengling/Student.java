package cn.edu.shiep.fengling;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Student extends User {
    String major;
    String enrollmentYear;
    String Class;
    ArrayList<Task> tasks;

    public String getMajor() {
        return major;
    }

    public String getEnrollmentYear() {
        return enrollmentYear;
    }

    public String getStudentClass(){
        return Class;
    }

    public Student(int id, String password) {
        super(id,password);
    }

    public Student(int id, String name, String password, String major, String enrollmentYear, String Class) {
        super(id,name,password);
        this.major = major;
        this.enrollmentYear = enrollmentYear;
        this.Class = Class;
    }

    public Student() {

    }

    @Override
    public void update() throws SQLException {
        Main.instance.updateStudent(this);
    }

    @Override
    public void insert() throws SQLException {
        Main.instance.addStudent(this);
    }

    @Override
    public void delete() throws SQLException {
        Main.instance.deleteStudent(this);
    }

    @Override
    public boolean login() throws SQLException {
        return Main.instance.loginStudent(this);
    }

    public static Student getStudent(int id) throws SQLException {
        return Main.instance.getStudentById(id);
    }

    public static ArrayList<Student> getAllStudents() throws SQLException {
        return Main.instance.queryStudentList();
    }

    public static void importFromExcel(String filePath) throws IOException, SQLException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue();
                String password = row.getCell(2).getStringCellValue();
                String major = row.getCell(3).getStringCellValue();
                String enrollmentYear = row.getCell(4).getStringCellValue();
                String studentClass = row.getCell(5).getStringCellValue();

                Student student = new Student(id, name, password, major, enrollmentYear, studentClass);

                if (Main.instance.getStudentById(id) != null) {
                    student.update();
                } else {
                    student.insert();
                }
            }
        }
    }
}

