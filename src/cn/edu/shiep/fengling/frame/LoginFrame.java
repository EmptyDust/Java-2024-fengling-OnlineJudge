package cn.edu.shiep.fengling.frame;

import cn.edu.shiep.fengling.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Objects;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setTitle("Login");
        setSize(245, 170);
        setResizable(false);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblUserID = new JLabel("ID");
        lblUserID.setBounds(10, 10, 100, 30);
        JTextField txtID = new JTextField(10);
        txtID.setBounds(120, 10, 100, 30);
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(10, 50, 100, 30);
        JPasswordField txtPassword = new JPasswordField(10);
        txtPassword.setBounds(120, 50, 100, 30);
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(120, 90, 100, 30);
        JComboBox<String> login = new JComboBox<>();
        login.setBounds(10, 90, 100, 30);
        login.addItem("Manager");
        login.addItem("Teacher");
        login.addItem("Student");

//        //
//        txtID.setText("0");
//        txtPassword.setText("pwd");
        //

        btnLogin.addActionListener(e -> {
            if (txtID.getText().isEmpty() || txtPassword.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "not null!");
                return;
            }
            int id;
            try {
                id = Integer.parseInt(txtID.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "id should be numeric!");
                return;
            }
            String password = txtPassword.getText();
            if (Objects.equals(login.getSelectedItem(), "Manager")) {
                Manager manager = new Manager(id, password);
                try {
                    if (manager.login()) {
                        JOptionPane.showMessageDialog(this, "Login Success");
                        setVisible(false);
                        Main.userFrame.setVisible(true);
                        Main.setUser(manager);
                    } else
                        JOptionPane.showMessageDialog(this, "invalid username or password");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (Objects.equals(login.getSelectedItem(), "Teacher")) {
                Teacher teacher = new Teacher(id, password);
                try {
                    if (teacher.login()) {
                        JOptionPane.showMessageDialog(this, "Login Success");
                        setVisible(false);
                        Main.userFrame.setVisible(true);
                        Main.setUser(Teacher.getTeacher(id));
                    } else
                        JOptionPane.showMessageDialog(this, "invalid username or password");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (Objects.equals(login.getSelectedItem(), "Student")) {
                Student student = new Student(id, password);
                try {
                    if (student.login()) {
                        JOptionPane.showMessageDialog(this, "Login Success");
                        setVisible(false);
                        Main.userFrame.setVisible(true);
                        Main.setUser(Student.getStudent(id));
                    } else
                        JOptionPane.showMessageDialog(this, "invalid username or password");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "请选择登录方式");
            }
        });
        panel.add(lblUserID);
        panel.add(txtID);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(login);
        add(panel);
    }
}
