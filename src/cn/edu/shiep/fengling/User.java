package cn.edu.shiep.fengling;

import java.sql.SQLException;

public abstract class User {
    boolean editTeacherAuthority = false;
    boolean editStudentAuthority = false;
    int id;
    String name;
    String password;

    public int getId(){
        return id;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public User(){

    }

    public User(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public abstract void update() throws SQLException;

    public abstract void insert() throws SQLException;

    public abstract void delete() throws SQLException;

    public abstract boolean login() throws SQLException;

}

