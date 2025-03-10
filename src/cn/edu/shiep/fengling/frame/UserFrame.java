package cn.edu.shiep.fengling.frame;

import cn.edu.shiep.fengling.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserFrame extends JFrame {
    JLabel nameLabel;
    JLabel IDLabel;
    JLabel majorLabel;
    JLabel enrollmentYearLabel;
    JButton addProblemButton;
    JButton manageButton;
    JButton myAccountButton;
    JButton addPaperButton;
    JButton openProblemListButton;
    JTable problemsTable;
    JTable paperTable;
    JTable taskTable;

    public UserFrame() {
        setTitle("User Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(null);
        taskPanel.setBorder(BorderFactory.createTitledBorder("我的答题卡"));
        taskPanel.setBounds(110, 280, 600, 270);
        panel.add(taskPanel);

        taskTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane taskScrollPane = new JScrollPane(
                taskTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        taskScrollPane.setBounds(10, 20, 580, 230);
        taskPanel.add(taskScrollPane);

        JPanel paperPanel = new JPanel();
        paperPanel.setLayout(null);
        paperPanel.setBorder(BorderFactory.createTitledBorder("我的试卷"));
        paperPanel.setBounds(110, 0, 600, 270);
        panel.add(paperPanel);

        paperTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        paperTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paperTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane paperScrollPane = new JScrollPane(
                paperTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        paperScrollPane.setBounds(10, 20, 580, 230);
        paperPanel.add(paperScrollPane);


        JPanel problemSetPanel = new JPanel();
        problemSetPanel.setBorder(BorderFactory.createTitledBorder("最新题目"));
        problemSetPanel.setBounds(720, 0, 190, 390);

        problemsTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        problemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        problemsTable.getTableHeader().setReorderingAllowed(false);
        problemsTable.setBounds(0, 10, 140, 380);
        problemSetPanel.add(problemsTable);
        panel.add(problemSetPanel);

        addPaperButton = new JButton("查看试卷");
        addPaperButton.setBounds(720, 400, 190, 30);
        addPaperButton.addActionListener(e -> {
            Main.paperFrame.openFrameAndPaperList();
            setVisible(false);
        });
        panel.add(addPaperButton);

        addProblemButton = new JButton("新增题目");
        addProblemButton.setBounds(720, 440, 190, 30);
        addProblemButton.addActionListener(e -> {
            Main.problemManageFrame.setVisible(true);
            Main.problemManageFrame.showFrame(this);
            setVisible(false);
        });
        panel.add(addProblemButton);

        openProblemListButton = new JButton("查看题库");
        openProblemListButton.setBounds(720, 440, 190, 30);
        openProblemListButton.addActionListener(e -> {
            Main.paperFrame.openFrameAndProblemList();
            setVisible(false);
        });
        panel.add(openProblemListButton);

        manageButton = new JButton("用户管理");
        manageButton.setBounds(720, 480, 190, 30);
        manageButton.addActionListener(e -> {
            Main.managerFrame.setVisible(true);
            if(Main.user instanceof Teacher){
                Main.managerFrame.setManageUser(Main.user);
            }
            setVisible(false);
        });
        panel.add(manageButton);

        myAccountButton = new JButton("我的账户");
        myAccountButton.setBounds(720, 480, 190, 30);
        myAccountButton.addActionListener(e -> {
            Main.managerFrame.setVisible(true);
            Main.managerFrame.setManageUser(Main.user);
            setVisible(false);
        });
        panel.add(myAccountButton);

        JButton logoutButton = new JButton("登出");
        logoutButton.setBounds(720, 520, 190, 30);
        logoutButton.addActionListener(e -> {
            Main.loginFrame.setVisible(true);
            setVisible(false);
            Main.user = null;
        });
        panel.add(logoutButton);

        JPanel userPanel = new JPanel();
        userPanel.setBorder(BorderFactory.createTitledBorder("用户信息"));
        userPanel.setBounds(5, 0, 100, 550);
        panel.add(userPanel);

        IDLabel = new JLabel();
        IDLabel.setBounds(10, 10, 100, 30);
        userPanel.add(IDLabel);

        nameLabel = new JLabel();
        nameLabel.setBounds(10, 60, 100, 30);
        userPanel.add(nameLabel);

        majorLabel = new JLabel();
        majorLabel.setBounds(10, 110, 100, 30);
        userPanel.add(majorLabel);

        enrollmentYearLabel = new JLabel();
        enrollmentYearLabel.setBounds(10, 160, 100, 30);
        userPanel.add(enrollmentYearLabel);

        add(panel);
    }

    public void setUser(User user) throws SQLException {
        nameLabel.setText("姓名：" + user.getName());
        IDLabel.setText("ID:" + user.getId());
        switch (user) {
            case Student student -> majorLabel.setText("专业：" + student.getMajor());
            case Teacher teacher -> majorLabel.setText("专业：" + teacher.getMajor());
            default -> majorLabel.setText("");
        }
        if (user instanceof Student) {
            manageButton.setVisible(false);
            addProblemButton.setVisible(false);
            myAccountButton.setVisible(true);
            openProblemListButton.setVisible(true);
            enrollmentYearLabel.setText("入学年份："+((Student)user).getEnrollmentYear());
            enrollmentYearLabel.setText("班级："+((Student)user).getStudentClass());
        } else {
            manageButton.setVisible(true);
            addProblemButton.setVisible(true);
            myAccountButton.setVisible(false);
            openProblemListButton.setVisible(false);
            enrollmentYearLabel.setText("");
        }
    }

    public void updatePaperTable() throws SQLException {
        paperTable.setModel(getPaperModel());
        paperTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        paperTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        paperTable.getColumnModel().getColumn(2).setPreferredWidth(30);
        paperTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        paperTable.getColumnModel().getColumn(4).setPreferredWidth(130);
    }

    private DefaultTableModel getPaperModel() throws SQLException {
        ArrayList<Paper> papers = Paper.getAllPapers();
        Object[][] data = new Object[papers.size()][7];
        int i = 0;
        for (Paper paper : papers) {
            data[i][0] = paper.getId();
            data[i][1] = paper.getTitle();
            data[i][2] = paper.getAuthor().getId();
            data[i][3] = paper.getAuthor().getName();
            if (paper.getDate() != null)
                data[i][4] = paper.getDate().toString() + " " + paper.getTime().toString();
            data[i][5] = paper.getStatus();
            data[i][6] = paper.getDuration();
            if(paper.getDuration()==-1)data[i][6] = "尚未设置";
            i++;
        }

        String[] columnNames = {"ID", "标题", "作者ID", "作者名", "考试时间", "状态", "考试时长"};
        return new DefaultTableModel(data, columnNames);
    }

    public void updateProblemTable() throws SQLException {
        problemsTable.setModel(getTableModel());
        problemsTable.getColumnModel().getColumn(0).setPreferredWidth(180);
    }

    final int maxColumnSize = 20;
    private DefaultTableModel getTableModel() throws SQLException {
        ArrayList<Problem> problems = Problem.getAllProblems();
        Problem[] problemsList = new Problem[Math.min(problems.size(), maxColumnSize)];
        for (Problem p : problems) {
            Problem cur = p;
            for (int i = 0; i < problemsList.length; i++) {
                if (problemsList[i] == null || problemsList[i].getId() < cur.getId()) {
                    Problem tmp = cur;
                    cur = problemsList[i];
                    problemsList[i] = tmp;
                }
            }
        }

        Object[][] data = new Object[problemsList.length][1];
        for (int i = 0; i < problemsList.length; i++) {
            data[i][0] = problemsList[i].getTitle();
        }

        String[] columnNames = {"Title"};

        return new DefaultTableModel(data, columnNames);
    }


    public void updateTaskTable() throws SQLException {
        taskTable.setModel(getTaskTableModel());
        if(!(Main.user instanceof Student))return;
        taskTable.getColumnModel().getColumn(0).setPreferredWidth(180);
    }

    private DefaultTableModel getTaskTableModel() throws SQLException {
        if(!(Main.user instanceof Student))return new DefaultTableModel();
        ArrayList<Task> tasks = Task.getTaskByStudentID(Main.user.getId());
        Task[] taskList = new Task[tasks.size()];
        for (int i = 0; i < taskList.length; i++) {
            taskList[i] = tasks.get(i);
        }

        Object[][] data = new Object[tasks.size()][3];
        for (int i = 0; i < taskList.length; i++) {
            data[i][0] = taskList[i].getPaper().getTitle();
            data[i][1] = taskList[i].getPaper().getAuthor().getName();
            data[i][2] = taskList[i].getGrade();
        }

        String[] columnNames = {"试卷","试卷作者","成绩"};

        return new DefaultTableModel(data, columnNames);
    }
}
