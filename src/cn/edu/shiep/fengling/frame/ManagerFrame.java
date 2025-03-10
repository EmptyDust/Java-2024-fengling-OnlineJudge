package cn.edu.shiep.fengling.frame;

import cn.edu.shiep.fengling.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManagerFrame extends JFrame {
    User user;
    JPanel studentPanel;
    JPanel teacherPanel;
    JPanel teacherListPanel;
    JPanel studentListPanel;

    JTextField studentIDField;
    JTextField studentPasswordField;
    JTextField studentNameField;
    JTextField studentMajorField;
    JTextField studentEnrollmentYearField;
    JTextField studentClassField;

    JTextField teacherIDField;
    JTextField teacherPasswordField;
    JTextField teacherNameField;
    JTextField teacherMajorField;

    JTable teacherListTable;
    JTable studentListTable;

    JButton studentListButton;
    JButton teacherListButton;
    JButton manageStudent;
    JButton manageTeacher;

    public ManagerFrame() {
        setTitle("Manager Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        studentPanel = new JPanel();
        studentPanel.setLayout(null);
        studentPanel.setBorder(BorderFactory.createTitledBorder("学生信息"));
        studentPanel.setBounds(5, 0, 700, 550);
        panel.add(studentPanel);

        JLabel studentIDLabel = new JLabel("学号：");
        studentIDLabel.setBounds(30, 30, 100, 30);
        studentPanel.add(studentIDLabel);

        studentIDField = new JTextField(10);
        studentIDField.setBounds(100, 30, 100, 30);
        studentPanel.add(studentIDField);

        JLabel studentPassword = new JLabel("密码：");
        studentPassword.setBounds(30, 70, 100, 30);
        studentPanel.add(studentPassword);

        studentPasswordField = new JTextField(10);
        studentPasswordField.setBounds(100, 70, 100, 30);
        studentPanel.add(studentPasswordField);

        JButton resetStudentPasswordButton = new JButton("重置");
        resetStudentPasswordButton.setBounds(210, 70, 100, 30);
        studentPanel.add(resetStudentPasswordButton);
        resetStudentPasswordButton.addActionListener(e -> {
            if (this.user == null) {
                JOptionPane.showMessageDialog(null, "当前无用户");
                return;
            }
            int res = JOptionPane.showConfirmDialog(null, "确定重置密码？");
            if (res == JOptionPane.OK_OPTION) {
                user.setPassword("123456");
                try {
                    user.update();
                    setManageUser(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "密码已重置为123456");
            }
        });
        studentPanel.add(resetStudentPasswordButton);

        JLabel studentNameLabel = new JLabel("姓名：");
        studentNameLabel.setBounds(30, 110, 100, 30);
        studentPanel.add(studentNameLabel);

        studentNameField = new JTextField();
        studentNameField.setBounds(100, 110, 100, 30);
        studentPanel.add(studentNameField);

        JLabel studentMajorLabel = new JLabel("专业：");
        studentMajorLabel.setBounds(30, 150, 100, 30);
        studentPanel.add(studentMajorLabel);

        studentMajorField = new JTextField();
        studentMajorField.setBounds(100, 150, 100, 30);
        studentPanel.add(studentMajorField);

        JLabel studentEnrollmentYearLabel = new JLabel("入学年份：");
        studentEnrollmentYearLabel.setBounds(30, 190, 100, 30);
        studentPanel.add(studentEnrollmentYearLabel);

        studentEnrollmentYearField = new JTextField();
        studentEnrollmentYearField.setBounds(100, 190, 100, 30);
        studentPanel.add(studentEnrollmentYearField);

        JLabel studentClassLabel = new JLabel("班级：");
        studentClassLabel.setBounds(30, 230, 100, 30);
        studentPanel.add(studentClassLabel);

        studentClassField = new JTextField();
        studentClassField.setBounds(100, 230, 100, 30);
        studentPanel.add(studentClassField);

        JButton addStudentButton = new JButton("完成");
        addStudentButton.setBounds(100, 270, 100, 30);
        addStudentButton.addActionListener(e -> {
            int id;
            try {
                id = Integer.parseInt(studentIDField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入合法学号");
                return;
            }

            String password;
            if (user != null && Main.user.getId() == user.getId()) {
                password = studentPasswordField.getText();
            } else password = "123456";
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(null, "密码长度必须大于六位");
                return;
            }
            String name = studentNameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入姓名");
                return;
            }
            String major = studentMajorField.getText();
            if (major.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入专业");
                return;
            }
            String enrollmentYear = studentEnrollmentYearField.getText();
            if (enrollmentYear.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入入学年份");
                return;
            }
            String className = studentClassField.getText();
            if (className.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入班级");
                return;
            }
            Student student;
            try {
                if (Student.getStudent(id) != null) {
                    student = new Student(id, name, password, major, enrollmentYear, className);
                    student.update();
                } else {
                    student = new Student(id, name, password, major, enrollmentYear, className);
                    student.insert();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setManageUser(student);
            JOptionPane.showMessageDialog(null, "OK");
        });
        studentPanel.add(addStudentButton);

        teacherPanel = new JPanel();
        teacherPanel.setLayout(null);
        teacherPanel.setBorder(BorderFactory.createTitledBorder("教师信息"));
        teacherPanel.setBounds(5, 0, 700, 550);
        panel.add(teacherPanel);

        JLabel teacherIDLabel = new JLabel("工号：");
        teacherIDLabel.setBounds(30, 30, 100, 30);
        teacherPanel.add(teacherIDLabel);

        teacherIDField = new JTextField();
        teacherIDField.setBounds(100, 30, 100, 30);
        teacherPanel.add(teacherIDField);

        JLabel teacherPasswordLabel = new JLabel("密码：");
        teacherPasswordLabel.setBounds(30, 70, 100, 30);
        teacherPanel.add(teacherPasswordLabel);

        teacherPasswordField = new JTextField();
        teacherPasswordField.setBounds(100, 70, 100, 30);
        teacherPanel.add(teacherPasswordField);

        JButton resetTeacherPasswordButton = new JButton("重置");
        resetTeacherPasswordButton.setBounds(210, 70, 100, 30);
        teacherPanel.add(resetTeacherPasswordButton);
        resetTeacherPasswordButton.addActionListener(e -> {
            if (this.user == null) {
                JOptionPane.showMessageDialog(null, "当前无用户");
                return;
            }
            int res = JOptionPane.showConfirmDialog(null, "确定重置密码？");
            if (res == JOptionPane.OK_OPTION) {
                this.user.setPassword("123456");
                try {
                    user.update();
                    setManageUser(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "密码已重置为123456");
            }
        });
        teacherPanel.add(resetTeacherPasswordButton);

        JLabel teacherNameLabel = new JLabel("姓名：");
        teacherNameLabel.setBounds(30, 110, 100, 30);
        teacherPanel.add(teacherNameLabel);

        teacherNameField = new JTextField();
        teacherNameField.setBounds(100, 110, 100, 30);
        teacherPanel.add(teacherNameField);

        JLabel teacherMajorLabel = new JLabel("专业：");
        teacherMajorLabel.setBounds(30, 150, 100, 30);
        teacherPanel.add(teacherMajorLabel);

        teacherMajorField = new JTextField();
        teacherMajorField.setBounds(100, 150, 100, 30);
        teacherPanel.add(teacherMajorField);

        JButton addTeacherButton = new JButton("完成");
        addTeacherButton.setBounds(100, 190, 100, 30);

        addTeacherButton.addActionListener(e -> {
            if (Main.user instanceof Teacher || Main.user instanceof Student) {
                JOptionPane.showMessageDialog(null, "只有管理员可以添加教师。");
                return;
            }
            int id;
            try {
                id = Integer.parseInt(teacherIDField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入合法工号");
                return;
            }

            String password;
            if (user != null && Main.user.getId() == user.getId()) {
                password = teacherPasswordField.getText();
            } else password = "123456";

            if (password.length() < 6) {
                JOptionPane.showMessageDialog(null, "密码长度必须大于六位");
                return;
            }
            String name = teacherNameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入合法姓名。");
                return;
            }
            String major = teacherMajorField.getText();
            if (major.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入教师专业。");
                return;
            }
            Teacher teacher;
            try {
                if (Teacher.getTeacher(id) != null) {
                    teacher = new Teacher(id, name, password, major);
                    teacher.update();
                } else {
                    teacher = new Teacher(id, name, password, major);
                    teacher.insert();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            setManageUser(teacher);
            JOptionPane.showMessageDialog(null, "OK");
        });
        teacherPanel.add(addTeacherButton);

        studentListPanel = new JPanel();
        studentListPanel.setLayout(null);
        studentListPanel.setBorder(BorderFactory.createTitledBorder("学生列表"));
        studentListPanel.setBounds(5, 0, 700, 550);
        panel.add(studentListPanel);

        studentListTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentListTable.getTableHeader().setReorderingAllowed(false);
        studentListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = studentListTable.getSelectedRow();
                    int column = studentListTable.getSelectedColumn();
                    if (row == -1 || column == -1) return;
                    if (column == 0) {
                        if (studentListTable.getModel().getValueAt(row, 0).equals("□"))
                            studentListTable.getModel().setValueAt("☑", row, 0);
                        else
                            studentListTable.getModel().setValueAt("□", row, 0);
                    }
                }
                if (e.getClickCount() == 2) {
                    int row = studentListTable.getSelectedRow();
                    int column = studentListTable.getSelectedColumn();
                    if (row == -1 || column == -1) return;
                    int id = Integer.parseInt(studentListTable.getModel().getValueAt(row, 1).toString());
                    if (column == 6) {
                        try {
                            setManageUser(Student.getStudent(id));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (column == 7) {
                        try {
                            Student.getStudent(id).delete();
                            updateStudentTable();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        studentListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane studentListScrollPane = new JScrollPane(
                studentListTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        studentListScrollPane.setBounds(30, 20, 640, 450);
        studentListPanel.add(studentListScrollPane);

        JButton deleteAllSelectStudentButton = new JButton("删除所有选中学生");
        deleteAllSelectStudentButton.setBounds(370, 490, 140, 30);
        deleteAllSelectStudentButton.addActionListener(e -> {
            int row = studentListTable.getModel().getRowCount();
            ArrayList<Integer> ids = new ArrayList<>();
            for (int i = row - 1; i >= 0; i--) {
                if (studentListTable.getModel().getValueAt(i, 0).equals("☑")) {
                    studentListTable.getModel().setValueAt("□", i, 0);
                    int id = Integer.parseInt(studentListTable.getModel().getValueAt(i, 1).toString());
                    ids.add(id);
                }
            }
            for (int id : ids) {
                try {
                    Student.getStudent(id).delete();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                updateStudentTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        studentListPanel.add(deleteAllSelectStudentButton);


        teacherListPanel = new JPanel();
        teacherListPanel.setLayout(null);
        teacherListPanel.setBorder(BorderFactory.createTitledBorder("教师列表"));
        teacherListPanel.setBounds(5, 0, 700, 550);
        panel.add(teacherListPanel);

        teacherListTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        teacherListTable.getTableHeader().setReorderingAllowed(false);
        teacherListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = teacherListTable.getSelectedRow();
                    int column = teacherListTable.getSelectedColumn();
                    if (row == -1 || column == -1) return;
                    if (column == 0) {
                        if (teacherListTable.getModel().getValueAt(row, 0).equals("□"))
                            teacherListTable.getModel().setValueAt("☑", row, 0);
                        else
                            teacherListTable.getModel().setValueAt("□", row, 0);
                    }
                }
                if (e.getClickCount() == 2) {
                    int row = teacherListTable.getSelectedRow();
                    int column = teacherListTable.getSelectedColumn();
                    if (row == -1 || column == -1) return;
                    int id = Integer.parseInt(teacherListTable.getModel().getValueAt(row, 1).toString());
                    if (column == 4) {
                        try {
                            setManageUser(Teacher.getTeacher(id));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (column == 5) {
                        try {
                            if (Main.user instanceof Teacher) {
                                JOptionPane.showMessageDialog(null, "只有管理员可以删除教师");
                                return;
                            }
                            Teacher.getTeacher(id).delete();
                            updateTeacherTable();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        teacherListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane teacherListScrollPane = new JScrollPane(
                teacherListTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        teacherListScrollPane.setBounds(30, 20, 640, 450);
        teacherListPanel.add(teacherListScrollPane);

        JButton deleteAllSelectTeacherButton = new JButton("删除所有选中教师");
        deleteAllSelectTeacherButton.setBounds(370, 490, 140, 30);
        deleteAllSelectTeacherButton.addActionListener(e -> {
            int row = teacherListTable.getModel().getRowCount();
            ArrayList<Integer> ids = new ArrayList<>();
            for (int i = row - 1; i >= 0; i--) {
                if (teacherListTable.getModel().getValueAt(i, 0).equals("☑")) {
                    teacherListTable.getModel().setValueAt("□", i, 0);
                    int id = Integer.parseInt(teacherListTable.getModel().getValueAt(i, 1).toString());
                    ids.add(id);
                }
            }
            for (int id : ids) {
                try {
                    Teacher.getTeacher(id).delete();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                updateTeacherTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        teacherListPanel.add(deleteAllSelectTeacherButton);


        studentListButton = new JButton("学生列表");
        studentListButton.addActionListener(e -> {
            studentPanel.setVisible(false);
            teacherPanel.setVisible(false);
            studentListPanel.setVisible(true);
            teacherListPanel.setVisible(false);
            try {
                updateStudentTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        studentListButton.setBounds(720, 430, 200, 30);
        panel.add(studentListButton);

        teacherListButton = new JButton("教师列表");
        teacherListButton.addActionListener(e -> {
            studentPanel.setVisible(false);
            teacherPanel.setVisible(false);
            studentListPanel.setVisible(false);
            teacherListPanel.setVisible(true);
            try {
                updateTeacherTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        teacherListButton.setBounds(720, 470, 200, 30);
        panel.add(teacherListButton);

        manageStudent = new JButton("新建学生");
        manageStudent.setBounds(720, 60, 200, 30);
        manageStudent.addActionListener(e -> {
            clearUser();
            studentPanel.setVisible(true);
            studentListPanel.setVisible(false);
            teacherListPanel.setVisible(false);
            teacherPanel.setVisible(false);
        });
        panel.add(manageStudent);

        manageTeacher = new JButton("新建教师");
        manageTeacher.setBounds(720, 20, 200, 30);
        manageTeacher.addActionListener(e -> {
            clearUser();
            studentPanel.setVisible(false);
            studentListPanel.setVisible(false);
            teacherListPanel.setVisible(false);
            teacherPanel.setVisible(true);
        });
        panel.add(manageTeacher);

        JButton exitButton = new JButton("返回");
        exitButton.setBounds(720, 510, 200, 30);
        exitButton.addActionListener(e -> {
            setVisible(false);
            Main.userFrame.setVisible(true);
            clearUser();
        });
        panel.add(exitButton);

        studentPanel.setVisible(false);

        teacherPanel.setVisible(false);

        add(panel);
    }

    void setManageUser(User user) {
        clearUser();
        this.user = user;
        if (user instanceof Student student) {
            studentPanel.setVisible(true);
            teacherPanel.setVisible(false);
            studentListPanel.setVisible(false);
            teacherListPanel.setVisible(false);

            studentIDField.setText(String.valueOf(student.getId()));
            studentNameField.setText(student.getName());
            if (Main.user.getId() == student.getId()) {
                studentPasswordField.setText(student.getPassword());
                studentPasswordField.setEditable(true);
            } else {
                studentPasswordField.setText("****");
                studentPasswordField.setEditable(false);
            }
            studentMajorField.setText(student.getMajor());
            studentEnrollmentYearField.setText(student.getEnrollmentYear());
            studentClassField.setText(student.getStudentClass());
        }
        if (user instanceof Teacher teacher) {
            teacherPanel.setVisible(true);
            teacherListPanel.setVisible(false);
            teacherListPanel.setVisible(false);
            studentPanel.setVisible(false);

            teacherIDField.setText(String.valueOf(teacher.getId()));
            if (Main.user.getId() == teacher.getId()) {
                teacherPasswordField.setText(teacher.getPassword());
                teacherPasswordField.setEditable(true);
            } else {
                teacherPasswordField.setText("****");
                teacherPasswordField.setEditable(false);
            }
            teacherNameField.setText(teacher.getName());
            teacherMajorField.setText(teacher.getMajor());
        }
        studentIDField.setEditable(false);
        teacherIDField.setEditable(false);
    }

    public void setUser(User user) {
        if (user instanceof Student) {
            studentListButton.setVisible(false);
            teacherListButton.setVisible(false);
            manageStudent.setVisible(false);
            manageTeacher.setVisible(false);
        } else {
            studentListButton.setVisible(true);
            teacherListButton.setVisible(true);
            manageStudent.setVisible(true);
            manageTeacher.setVisible(true);
        }
    }

    void clearUser() {
        user = null;
        studentPanel.setVisible(false);
        teacherPanel.setVisible(false);

        studentIDField.setText("");
        studentNameField.setText("");
        studentMajorField.setText("");
        studentEnrollmentYearField.setText("");
        studentClassField.setText("");

        teacherIDField.setText("");
        teacherNameField.setText("");
        teacherMajorField.setText("");

        teacherPasswordField.setText("****");
        teacherPasswordField.setEditable(false);
        studentPasswordField.setText("****");
        studentPasswordField.setEditable(false);

        studentIDField.setEditable(true);
        teacherIDField.setEditable(true);
    }

    public void updateStudentTable() throws SQLException {
        studentListTable.setModel(getStudentTableModel());
        studentListTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        studentListTable.getColumnModel().getColumn(1).setPreferredWidth(30);
        studentListTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        studentListTable.getColumnModel().getColumn(3).setPreferredWidth(100);
    }

    private DefaultTableModel getStudentTableModel() throws SQLException {
        ArrayList<Student> students = Student.getAllStudents();

        Object[][] data = new Object[students.size()][8];
        String[] columnNames;
        int i = 0;
        for (Student student : students) {
            data[i][0] = "□";
            data[i][1] = student.getId();
            data[i][2] = student.getName();
            data[i][3] = student.getMajor();
            data[i][4] = student.getStudentClass();
            data[i][5] = student.getEnrollmentYear();
            data[i][6] = "查看";
            data[i][7] = "删除";
            i++;
        }
        columnNames = new String[]{"", "学号", "姓名", "专业", "班级", "入学年份", "", ""};

        return new DefaultTableModel(data, columnNames);
    }

    public void updateTeacherTable() throws SQLException {
        teacherListTable.setModel(getTeacherTableModel());
        teacherListTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        teacherListTable.getColumnModel().getColumn(1).setPreferredWidth(30);
        teacherListTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        teacherListTable.getColumnModel().getColumn(3).setPreferredWidth(100);
    }

    private DefaultTableModel getTeacherTableModel() throws SQLException {
        ArrayList<Teacher> teachers = Teacher.getAllTeacher();

        Object[][] data = new Object[teachers.size()][6];
        String[] columnNames;
        int i = 0;
        for (Teacher teacher : teachers) {
            data[i][0] = "□";
            data[i][1] = teacher.getId();
            data[i][2] = teacher.getName();
            data[i][3] = teacher.getMajor();
            data[i][4] = "查看";
            data[i][5] = "删除";
            i++;
        }
        columnNames = new String[]{"", "工号", "姓名", "专业", "", ""};

        return new DefaultTableModel(data, columnNames);
    }
}