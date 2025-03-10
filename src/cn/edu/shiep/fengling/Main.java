package cn.edu.shiep.fengling;

import cn.edu.shiep.fengling.frame.*;

import java.sql.SQLException;

public class Main {
    public static DatabaseManager instance;
    public static LoginFrame loginFrame;
    public static ManagerFrame managerFrame;
    public static PaperFrame paperFrame;
    public static UserFrame userFrame;
    public static ProblemManageFrame problemManageFrame;
    public static User user;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //0 pwd
        instance = new DatabaseManager();
        loginFrame = new LoginFrame();
        managerFrame = new ManagerFrame();
        paperFrame = new PaperFrame();
        userFrame = new UserFrame();
        problemManageFrame = new ProblemManageFrame();
        loginFrame.setVisible(true);
    }

    public static void setUser(User user) throws SQLException {
        Main.user = user;
        userFrame.setUser(user);
        paperFrame.setUser(user);
        updatePaperTable();
        updateProblemTable();
        managerFrame.setUser(user);
            updateTaskTable();
    }

    public static void updateProblemTable() throws SQLException {
        userFrame.updateProblemTable();
        paperFrame.updateProblemTable();
    }

    public static void updatePaperTable() throws SQLException {
        userFrame.updatePaperTable();
        paperFrame.updatePaperTable();
    }

    public static void updateTaskTable() throws SQLException {
        userFrame.updateTaskTable();
    }
}

