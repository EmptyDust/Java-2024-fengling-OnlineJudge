package cn.edu.shiep.fengling;

import java.sql.SQLException;
import java.util.Objects;

public class Manager extends User {
    public Manager(int id, String password) {
        this.id = id;
        this.password = password;
        this.name = "admin";
        editStudentAuthority = true;
        editTeacherAuthority = true;
    }

    public Manager() {
        this.name = "admin";
    }

    @Override
    public void update() throws SQLException {

    }

    @Override
    public void insert() throws SQLException {

    }

    @Override
    public void delete() throws SQLException {

    }

    @Override
    public boolean login() throws SQLException {
        return Objects.equals(id, 0) && Objects.equals(password, "pwd");
    }

}
