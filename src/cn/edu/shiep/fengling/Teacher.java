package cn.edu.shiep.fengling;

import java.sql.SQLException;
import java.util.ArrayList;

public class Teacher extends User {
    String major;

    public String getMajor() {
        return major;
    }

    public Teacher(int id, String password){
        super(id,password);
    }

    public Teacher(int id, String name, String password, String major) {
        super(id,name,password);
        this.major = major;
        editStudentAuthority = true;
    }

    @Override
    public void update() throws SQLException {
        Main.instance.updateTeacher(this);
    }

    @Override
    public void insert() throws SQLException {
        Main.instance.addTeacher(this);
    }

    @Override
    public void delete() throws SQLException {
        Main.instance.deleteTeacher(this);
    }

    @Override
    public boolean login() throws SQLException {
        return Main.instance.loginTeacher(this);
    }

    public static Teacher getTeacher(int id) throws SQLException {
        return Main.instance.getTeacherById(id);
    }

    public static ArrayList<Teacher> getAllTeacher() throws SQLException {
        return Main.instance.queryTeachersList();
    }
}
