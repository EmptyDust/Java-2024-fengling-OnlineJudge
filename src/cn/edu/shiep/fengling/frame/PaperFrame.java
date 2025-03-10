package cn.edu.shiep.fengling.frame;

import cn.edu.shiep.fengling.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import java.util.ArrayList;
import java.sql.Date;

public class PaperFrame extends JFrame {

    JTable problemsTable;
    JTable paperTable;
    JPanel panel;

    JTextField IDTextField;
    JTextField titleField;
    JTextField timeField;
    JTextField lastTimeField;
    JTextField durationField;
    JComboBox<String> openComboBox;
    JButton newPaperButton;
    JButton okCompletePaperButton;
    JTextField openTextField;

    JButton okButton;
    JButton studentPaperButton;
    JButton deleteAllSelectProblemButton;
    JButton insertAllSelectProblemButton;
    JButton deleteAllSelectPaperButton;

    JPanel baseInformationPanel;
    JPanel problemListPanel;
    JPanel paperListPanel;
    JPanel problemPanel;
    JPanel problemRightListPanel;
    JPanel optionPanel;
    JPanel paperPanel;

    JButton lastPageButton;
    JButton nextPageButton;

    JTextArea answerField;
    JLabel answerLabel;

    JTextField problemTitleField;
    JTextField problemIDField;
    JTextArea contentArea;
    JTextField scoreField;
    JButton problemOKButton;
    JButton problemEditButton;

    JTable paperProblemsTable;
    JTable paperTasksTable;

    Paper paper = null;
    Problem problem = null;
    Task task = null;
    Answer answer = null;

    int currentPage = 0;

    public PaperFrame() {
        setTitle("Paper Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);

        paperPanel = new JPanel();
        paperPanel.setLayout(null);
        paperPanel.setBorder(BorderFactory.createTitledBorder("试卷信息总览"));
        paperPanel.setBounds(10, 0, 700, 540);
        panel.add(paperPanel);


        JLabel timeLabel = new JLabel("考试时间：");
        timeLabel.setBounds(30, 40, 120, 30);
        paperPanel.add(timeLabel);

        timeField = new JTextField();
        timeField.setBounds(140, 40, 170, 30);
        paperPanel.add(timeField);

        JLabel durationLabel = new JLabel("持续时间（分钟）：");
        durationLabel.setBounds(30, 80, 120, 30);
        paperPanel.add(durationLabel);

        durationField = new JTextField();
        durationField.setBounds(140, 80, 170, 30);
        paperPanel.add(durationField);

        JLabel openLabel = new JLabel("是否开放:");
        openLabel.setBounds(30, 120, 120, 30);
        paperPanel.add(openLabel);

        openComboBox = new JComboBox<>();
        openComboBox.setBounds(140, 120, 100, 30);
        openComboBox.setEditable(false);
        paperPanel.add(openComboBox);
        openComboBox.addItem(Paper.open);
        openComboBox.addItem(Paper.close);

        openTextField = new JTextField();
        openTextField.setBounds(140, 120, 100, 30);
        openTextField.setEditable(false);
        paperPanel.add(openTextField);

        JPanel paperProblemListPanel = new JPanel();
        paperProblemListPanel.setLayout(null);
        paperProblemListPanel.setBorder(BorderFactory.createTitledBorder("试卷题目列表"));
        paperProblemListPanel.setBounds(330, 30, 360, 245);
        paperPanel.add(paperProblemListPanel);

        paperProblemsTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        paperProblemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paperProblemsTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane paperProblemScrollPane = new JScrollPane(
                paperProblemsTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        paperProblemScrollPane.setBounds(10, 20, 330, 215);
        paperProblemListPanel.add(paperProblemScrollPane);

        paperProblemsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = paperProblemsTable.getSelectedRow();
                    int column = paperProblemsTable.getSelectedColumn();
                    if (row == -1 || column == -1) return;
                    if (column == 6) {
                        try {
                            paper.deleteProblem(row);
                            updatePaperProblemTable();
                            showProblem(currentPage);
                            paperProblemsTable.repaint();
                            problemRightListPanel.repaint();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        JPanel paperTaskListPanel = new JPanel();
        paperTaskListPanel.setLayout(null);
        paperTaskListPanel.setBorder(BorderFactory.createTitledBorder("答题卡列表"));
        paperTaskListPanel.setBounds(330, 280, 360, 245);
        paperPanel.add(paperTaskListPanel);


        paperTasksTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        paperTasksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paperTasksTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane paperTaskScrollPane = new JScrollPane(
                paperTasksTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        paperTaskScrollPane.setBounds(10, 20, 330, 215);
        paperTaskListPanel.add(paperTaskScrollPane);


        problemRightListPanel = new JPanel();
        problemRightListPanel.setLayout(null);
        problemRightListPanel.setBorder(BorderFactory.createTitledBorder("题目列表-右"));
        problemRightListPanel.setBounds(720, 195, 200, 190);
        panel.add(problemRightListPanel);


        problemPanel = new JPanel();
        problemPanel.setLayout(null);
        problemPanel.setBorder(BorderFactory.createTitledBorder("题目信息"));
        problemPanel.setBounds(10, 0, 700, 540);
        panel.add(problemPanel);

        optionPanel = new JPanel();
        optionPanel.setLayout(null);
        optionPanel.setBorder(BorderFactory.createTitledBorder("题目选项"));
        optionPanel.setBounds(30, 180, 630, 280);
        problemPanel.add(optionPanel);

        JLabel problemTitleLabel = new JLabel("标题：");
        problemTitleLabel.setBounds(30, 30, 100, 30);
        problemPanel.add(problemTitleLabel);

        problemTitleField = new JTextField();
        problemTitleField.setEditable(false);
        problemTitleField.setColumns(10);
        problemTitleField.setBounds(100, 30, 100, 30);
        problemPanel.add(problemTitleField);

        JLabel problemIDLabel = new JLabel("ID");
        problemIDLabel.setBounds(230, 30, 100, 30);
        problemPanel.add(problemIDLabel);

        problemIDField = new JTextField();
        problemIDField.setColumns(10);
        problemIDField.setBounds(300, 30, 100, 30);
        problemIDField.setEditable(false);
        problemPanel.add(problemIDField);

        JLabel scoreLabel = new JLabel("分数：");
        scoreLabel.setBounds(430, 30, 100, 30);
        problemPanel.add(scoreLabel);

        scoreField = new JTextField();
        scoreField.setEditable(false);
        scoreField.setColumns(10);
        scoreField.setBounds(500, 30, 100, 30);
        problemPanel.add(scoreField);

        problemOKButton = new JButton("保存分数");
        problemOKButton.setBounds(560, 480, 95, 30);
        problemPanel.add(problemOKButton);
        problemOKButton.addActionListener(e -> {
            if(Main.user instanceof Student){
                if(answer.getTrueOrFalse()){
                    JOptionPane.showMessageDialog(null,"正确");
                }
                else JOptionPane.showMessageDialog(null,"错误");
            }
            else {
                if (paper == null) {
                    JOptionPane.showMessageDialog(null, "当前没有试卷，无法设置！");
                    return;
                }
                if (Main.user instanceof Teacher || Main.user instanceof Manager) {
                    try {
                        paper.setScore(problem, Integer.parseInt(scoreField.getText()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    setProblem(problem);
                }
                JOptionPane.showMessageDialog(null, "ok");
            }
        });

        problemEditButton = new JButton("编辑");
        problemEditButton.setBounds(460, 480, 95, 30);
        problemPanel.add(problemEditButton);
        problemEditButton.addActionListener(e -> {
            Main.problemManageFrame.setProblem(problem);
            Main.problemManageFrame.setVisible(true);
            Main.problemManageFrame.setFromFrame(this);
            setVisible(false);
        });

        JLabel contentLabel = new JLabel("试题内容：");
        contentLabel.setBounds(30, 70, 100, 20);
        problemPanel.add(contentLabel);

        contentArea = new JTextArea();
        contentArea.setBounds(100, 70, 560, 100);
        contentArea.setEditable(false);
        problemPanel.add(contentArea);

        answerLabel = new JLabel("Answer:");
        answerLabel.setBounds(30, 400, 100, 20);
        problemPanel.add(answerLabel);

        answerField = new JTextArea();
        answerField.setColumns(10);
        answerField.setBounds(100, 400, 570, 80);
        problemPanel.add(answerField);

        baseInformationPanel = new JPanel();
        baseInformationPanel.setBorder(BorderFactory.createTitledBorder("试卷信息"));
        baseInformationPanel.setBounds(720, 0, 200, 190);
        baseInformationPanel.setLayout(null);
        panel.add(baseInformationPanel);

        JLabel IDLabel = new JLabel("ID");
        IDLabel.setBounds(5, 30, 50, 20);
        baseInformationPanel.add(IDLabel);
        JLabel titleLabel = new JLabel("标题:");
        titleLabel.setBounds(5, 70, 50, 20);
        baseInformationPanel.add(titleLabel);
        JLabel lastTimeLabel = new JLabel("剩余时间：");
        lastTimeLabel.setBounds(5, 110, 90, 20);
        baseInformationPanel.add(lastTimeLabel);

        IDTextField = new JTextField();
        IDTextField.setBounds(90, 30, 100, 20);
        IDTextField.setEditable(false);
        baseInformationPanel.add(IDTextField);
        titleField = new JTextField();
        titleField.setBounds(90, 70, 100, 20);
        baseInformationPanel.add(titleField);
        lastTimeField = new JTextField();
        lastTimeField.setBounds(90, 110, 100, 20);
        lastTimeField.setEditable(false);
        baseInformationPanel.add(lastTimeField);

        okCompletePaperButton = new JButton("提交试卷");
        okCompletePaperButton.setBounds(90, 150, 100, 20);
        okCompletePaperButton.addActionListener(e -> {
            if (paper == null || !paper.checkStatus()) {
                JOptionPane.showMessageDialog(null, "当前无试卷");
                return;
            }
            boolean okb = true;
            for (Answer answer : task.getProblemAnswer()) {
                if (answer.getContent().isEmpty()) {
                    okb = false;
                    break;
                }
            }
            if (!okb) {
                JOptionPane.showMessageDialog(null, "有题目未完成");
                return;
            }
            int ok = JOptionPane.showConfirmDialog(null, "确认提交？提交后将不能再返回答题！");
            if (ok == JOptionPane.OK_OPTION) {
                try {
                    task.insert();
                    task.update();
                    Main.updateTaskTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                clearPaper();
                clearProblem();
                exitFrame();
            }
        });
        baseInformationPanel.add(okCompletePaperButton);

        newPaperButton = new JButton("新的试卷");
        newPaperButton.setBounds(90, 150, 100, 20);
        newPaperButton.addActionListener(e -> {
            clearPaper();
            paperListPanel.setVisible(true);
        });
        baseInformationPanel.add(newPaperButton);

        new Timer(1000, e -> {
            okCompletePaperButton.setVisible(paper != null && paper.checkStatus() && Main.user instanceof Student);
            if (paper == null) {
                lastTimeField.setText("");
                return;
            }
            if (paper.getDuration() == -1) {
                lastTimeField.setText("尚未设置持续时间");
                return;
            }
            Date date = paper.getDate();
            Time time = paper.getTime();
            if (time == null || date == null) return;

            LocalDate currentDate = LocalDate.now();

            LocalDate localDate = paper.getDate().toLocalDate();

            if (!localDate.equals(currentDate)) {
                lastTimeField.setText("考试未开放");
                return;
            }


            LocalTime currentTime = LocalTime.now();

            LocalTime startLocalTime = paper.getTime().toLocalTime();

            LocalTime endLocalTime = startLocalTime.plus(Duration.ofHours(0).plusMinutes(paper.getDuration()));

            Duration duration = Duration.between(startLocalTime, currentTime);

            Duration durationEnd = Duration.between(currentTime, endLocalTime);

            long startTime = duration.toMillis();
            long endTime = durationEnd.toMillis();
            if (endTime < 0) {
                lastTimeField.setText("考试已结束");
            } else if (startTime < 0) {
                long hours = Math.abs(duration.toHours());
                long minutes = Math.abs(duration.toMinutes()) % 60;
                long seconds = Math.abs(duration.getSeconds()) % 60;
                lastTimeField.setText("还有" + hours + ":" + minutes + ":" + seconds + "开始");
            } else {
                long hours = Math.abs(durationEnd.toHours());
                long minutes = Math.abs(durationEnd.toMinutes()) % 60;
                long seconds = Math.abs(durationEnd.getSeconds()) % 60;
                lastTimeField.setText(hours + ":" + minutes + ":" + seconds);
            }

        }).start();

        problemListPanel = new JPanel();
        problemListPanel.setLayout(null);
        problemListPanel.setBorder(BorderFactory.createTitledBorder("题目列表"));
        problemListPanel.setBounds(10, 0, 700, 540);
        panel.add(problemListPanel);

        insertAllSelectProblemButton = new JButton("插入所有选中题目");
        insertAllSelectProblemButton.setBounds(520, 490, 140, 30);
        insertAllSelectProblemButton.addActionListener(e -> {
            if (paper == null) {
                JOptionPane.showMessageDialog(null, "请先设置试卷");
                return;
            }
            int row = problemsTable.getModel().getRowCount();
            for (int i = row - 1; i >= 0; i--) {
                if (problemsTable.getModel().getValueAt(i, 0).equals("☑")) {
                    problemsTable.getModel().setValueAt("□", i, 0);
                    int id = Integer.parseInt(problemsTable.getModel().getValueAt(i, 1).toString());
                    try {
                        if (!paper.getProblemInPaper(id))
                            paper.addProblem(Problem.get(id));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            try {
                updatePaperProblemTable();
                updatePaperTaskTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            showProblem(-1);
        });
        problemListPanel.add(insertAllSelectProblemButton);

        deleteAllSelectProblemButton = new JButton("删除所有选中题目");
        deleteAllSelectProblemButton.setBounds(370, 490, 140, 30);
        deleteAllSelectProblemButton.addActionListener(e -> {
            int row = problemsTable.getModel().getRowCount();
            ArrayList<Integer> ids = new ArrayList<>();
            for (int i = row - 1; i >= 0; i--) {
                if (problemsTable.getModel().getValueAt(i, 0).equals("☑")) {
                    problemsTable.getModel().setValueAt("□", i, 0);
                    int id = Integer.parseInt(problemsTable.getModel().getValueAt(i, 1).toString());
                    ids.add(id);
                }
            }
            for (int id : ids) {
                try {
                    if (paper != null) {
                        paper.deleteProblem(Problem.get(id));
                        showProblem(currentPage);
                    }
                    Problem.get(id).delete();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        problemListPanel.add(deleteAllSelectProblemButton);

        problemsTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        problemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        problemsTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane problemScrollPane = new JScrollPane(
                problemsTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        problemsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = problemsTable.getSelectedRow();
                    int column = problemsTable.getSelectedColumn();
                    if (row == -1 || column == -1) return;
                    if (column == 0) {
                        if (problemsTable.getModel().getValueAt(row, 0).equals("□"))
                            problemsTable.getModel().setValueAt("☑", row, 0);
                        else
                            problemsTable.getModel().setValueAt("□", row, 0);
                    }
                }
                if (e.getClickCount() == 2) {
                    int row = problemsTable.getSelectedRow();
                    int column = problemsTable.getSelectedColumn();
                    if (row == -1 || column == -1) return;
                    int id = Integer.parseInt(problemsTable.getValueAt(row, 1).toString());
                    if (column == 5) {
                        try {
                            setProblem(Problem.get(id));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (column == 6) {
                        try {
                            addProblem(Problem.get(id));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (column == 7) {
                        try {
                            if (paper != null) {
                                paper.deleteProblem(Problem.get(id));
                                showProblem(currentPage);
                            }
                            Problem.get(id).delete();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        problemScrollPane.setBounds(30, 20, 640, 450);
        problemListPanel.add(problemScrollPane);

        paperListPanel = new JPanel();
        paperListPanel.setLayout(null);
        paperListPanel.setBorder(BorderFactory.createTitledBorder("试卷列表"));
        paperListPanel.setBounds(10, 0, 700, 540);
        panel.add(paperListPanel);

        deleteAllSelectPaperButton = new JButton("删除所有选中试卷");
        deleteAllSelectPaperButton.setBounds(370, 490, 140, 30);
        deleteAllSelectPaperButton.addActionListener(e -> {
            int row = paperTable.getModel().getRowCount();
            ArrayList<Integer> ids = new ArrayList<>();
            for (int i = row - 1; i >= 0; i--) {
                if (paperTable.getModel().getValueAt(i, 0).equals("☑")) {
                    paperTable.getModel().setValueAt("□", i, 0);
                    int id = Integer.parseInt(paperTable.getModel().getValueAt(i, 1).toString());
                    ids.add(id);
                }
            }
            for (int id : ids) {
                try {
                    if (paper != null && paper.getId() == id) clearPaper();
                    Paper.getPaper(id).delete();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        paperListPanel.add(deleteAllSelectPaperButton);

        paperTable = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        paperTable.getTableHeader().setReorderingAllowed(false);
        paperTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = paperTable.getSelectedRow();
                    int column = paperTable.getSelectedColumn();
                    if (row == -1 || column == -1) return;
                    if (column == 0) {
                        if (paperTable.getModel().getValueAt(row, 0).equals("□"))
                            paperTable.getModel().setValueAt("☑", row, 0);
                        else
                            paperTable.getModel().setValueAt("□", row, 0);
                    }
                }
                if (e.getClickCount() == 2) {
                    int row = paperTable.getSelectedRow();
                    int column = paperTable.getSelectedColumn();
                    if (row == -1 || column == -1) return;
                    int id = Integer.parseInt(paperTable.getModel().getValueAt(row, 1).toString());
                    if (column == 8) {
                        try {
                            setPaper(Paper.getPaper(id));
                            if (Main.user instanceof Student) {
                                task = new Task(paper, (Student) Main.user);
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (column == 9) {
                        try {
                            Paper.getPaper(id).delete();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        paperTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane paperScrollPane = new JScrollPane(
                paperTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        paperScrollPane.setBounds(30, 20, 640, 450);
        paperListPanel.add(paperScrollPane);

        nextPageButton = new JButton("下一页");
        nextPageButton.addActionListener(e -> {
            ArrayList<Problem> allProblems = paper.getProblemsList();
            int sz = allProblems == null ? 0 : allProblems.size();
            if (Main.user instanceof Teacher || Main.user instanceof Manager) sz++;
            sz--;
            int problemsAmount = buttonAmountInOneColumn * buttonAmountInOneRow;
            int lastPage = sz / problemsAmount;
            if (lastPage == currentPage) {
                JOptionPane.showMessageDialog(null, "已经是最后一页！");
                return;
            }
            currentPage++;
            showProblem(currentPage);
        });
        nextPageButton.setBounds(825, 390, 95, 30);
        panel.add(nextPageButton);

        lastPageButton = new JButton("上一页");
        lastPageButton.addActionListener(e -> {
            if (currentPage == 0) {
                JOptionPane.showMessageDialog(null, "已经是第一页！");
                return;
            }
            currentPage--;
            showProblem(currentPage);
        });
        lastPageButton.setBounds(720, 390, 95, 30);
        panel.add(lastPageButton);

        okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String title = titleField.getText();
            String status = openComboBox.getSelectedItem().toString();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入标题");
                return;
            }
            if (paper == null) {
                paper = new Paper(title, Main.user, status);
                paper.setDate(new Date(0));
                paper.setTime(new Time(0));
                try {
                    paper.insert();
                    setPaper(paper);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "OK!");
                return;
            }
            Date date;
            Time time;
            try {
                String[] times = timeField.getText().split(" ");
                date = Date.valueOf(times[0]);
                time = Time.valueOf(times[1]);
                paper.setDate(date);
                paper.setTime(time);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "日期格式有误，请输入YYYY-MM-DD HH:MM:SS");
                return;
            }
            try {
                paper.setDuration(Integer.parseInt(durationField.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入合法的持续时间");
                return;
            }
            try {
                paper.setAuthor(Main.user);
                paper.setStatus(status);
                paper.setTitle(title);
                paper.update();
                setPaper(paper);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null, "OK!");
        });
        okButton.setBounds(720, 470, 200, 30);
        panel.add(okButton);


        studentPaperButton = new JButton("查看试卷信息");
        studentPaperButton.setBounds(720, 470, 200, 30);
        studentPaperButton.addActionListener(e -> {
            if (paper == null) {
                JOptionPane.showMessageDialog(null, "当前无试卷");
                return;
            }
            try {
                setPaper(paper);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        panel.add(studentPaperButton);

        JButton problemListButton = new JButton("题目列表");
        problemListButton.addActionListener(e -> {
            if (paper != null && paper.checkStatus() && Main.user instanceof Student) {
                int ok = JOptionPane.showConfirmDialog(null, "确认离开吗？将不会保留已做题目信息！");
                if (ok == JOptionPane.OK_OPTION) {
                    task = null;
                    clearPaper();
                    clearProblem();
                } else return;
            }
            paperPanel.setVisible(false);
            problemListPanel.setVisible(true);
            paperListPanel.setVisible(false);
            problemPanel.setVisible(false);
        });
        problemListButton.setBounds(720, 430, 95, 30);
        panel.add(problemListButton);

        JButton paperListButton = new JButton("试卷列表");
        paperListButton.addActionListener(e -> {
            if (paper != null && paper.checkStatus() && Main.user instanceof Student) {
                int ok = JOptionPane.showConfirmDialog(null, "确认离开吗？将不会保留已做题目信息！");
                if (ok == JOptionPane.OK_OPTION) {
                    clearPaper();
                    clearProblem();
                    task = null;
                } else return;
            }
            paperPanel.setVisible(false);
            problemListPanel.setVisible(false);
            paperListPanel.setVisible(true);
            problemPanel.setVisible(false);
        });
        paperListButton.setBounds(825, 430, 95, 30);
        panel.add(paperListButton);

        JButton exitButton = new JButton("返回");
        exitButton.setBounds(720, 510, 200, 30);
        exitButton.addActionListener(e -> {
            if (paper != null && paper.checkStatus() && Main.user instanceof Student) {
                int ok = JOptionPane.showConfirmDialog(null, "确认离开吗？退出将不会保留已做题目信息！");
                if (ok != JOptionPane.OK_OPTION) {
                    return;
                }
            }
            clearPaper();
            clearProblem();
            task = null;
            exitFrame();
        });
        panel.add(exitButton);

        clearPaper();
        clearProblem();
        paperListPanel.setVisible(false);
        problemListPanel.setVisible(false);
        paperListPanel.setVisible(true);
        problemPanel.setVisible(false);
        add(panel);

    }

    public void setPaper(Paper paper) throws SQLException {
        clearPaper();
        clearProblem();
        paperListPanel.setVisible(false);
        problemListPanel.setVisible(false);
        problemRightListPanel.setVisible(true);
        problemPanel.setVisible(false);
        lastPageButton.setVisible(true);
        nextPageButton.setVisible(true);
        this.paper = paper;
        IDTextField.setText(String.valueOf(paper.getId()));
        titleField.setText(paper.getTitle());
        if (paper.getDate() != null)
            timeField.setText(paper.getDate().toString() + " " + paper.getTime().toString());
        if (paper.getDuration() != -1)
            durationField.setText(String.valueOf(paper.getDuration()));
        openTextField.setText(paper.getStatus());
        openComboBox.setSelectedItem(paper.getStatus());

        paperPanel.setVisible(true);
        updatePaperProblemTable();
        updatePaperTaskTable();
        currentPage = 0;
        showProblem(currentPage);
    }

    public void clearPaper() {
        problemRightListPanel.setVisible(false);
        lastPageButton.setVisible(false);
        nextPageButton.setVisible(false);
        paperPanel.setVisible(false);
        paper = null;
        IDTextField.setText("");
        titleField.setText("");
        timeField.setText("");
        durationField.setText("");
        lastTimeField.setText("");
        openComboBox.setSelectedIndex(0);
    }

    public void addProblem(Problem problem) throws SQLException {
        if (paper == null) {
            JOptionPane.showMessageDialog(null, "当前无试卷，无法添加试题");
            return;
        }
        if (paper.getProblemInPaper(problem.getId())) {
            JOptionPane.showMessageDialog(null, "当前题目已经在试卷中了！");
            return;
        }
        paper.addProblem(problem);
        problemListPanel.setVisible(false);
        paperListPanel.setVisible(false);
        problemPanel.setVisible(true);
        setProblem(problem);
        updatePaperProblemTable();
        updatePaperTaskTable();
        showProblem(-1);
    }


    static String currentOption = "";

    public void setProblem(Problem problem) {
        clearProblem();
        this.problem = problem;
        answerField.setVisible(false);
        answerLabel.setVisible(false);

        problemPanel.setVisible(true);

        problemTitleField.setText(problem.getTitle());
        contentArea.setText(problem.getContent());
        problemIDField.setText(String.valueOf(problem.getId()));
        if (paper != null && paper.getScore(problem) != -1) {
            scoreField.setText(String.valueOf(paper.getScore(problem)));
        }

        optionPanel.removeAll();
        optionPanel.repaint();
        ArrayList<String> options = problem.getOptions();
        currentOption = "";
        if (Main.user instanceof Student && paper != null){
            currentOption = task.getProblemAnswer(problem).getContent();
        }
        if (Main.user instanceof Student && paper == null){
            answer = new Answer(problem);
        }
        ButtonGroup buttonGroup = new ButtonGroup();
        if (options == null) return;
        for (int i = 0; i < options.size(); i++) {
            int row = i / 2;
            int column = i % 2;
            String option = "" + (char) ((int) 'A' + i);
            if (problem.getType().equals(Problem.TFProblem) || problem.getType().equals(Problem.OneOptionProblem)) {
                JRadioButton optionButton = new JRadioButton(option + " " + options.get(i));
                optionPanel.add(optionButton);
                optionButton.addActionListener(e -> {
                    if (!(Main.user instanceof Student)) {
                        JOptionPane.showMessageDialog(null, "只有学生可以答题");
                        buttonGroup.clearSelection();
                        return;
                    }
                    if (optionButton.isSelected())
                        currentOption = option;
                    else currentOption = "";
                    if (paper == null) {
                        this.answer.setContent(currentOption);
                        return;
                    }
                    Answer answer = new Answer(paper, problem, (Student) Main.user, currentOption);
                    task.setProblemAnswer(problem, answer);
                    showProblem(currentPage);
                });
                for (char c : currentOption.toCharArray()) {
                    if (c == option.charAt(0)) optionButton.setSelected(true);
                }
                optionButton.setBounds(42 + column * 230, 30 + row * 60, 200, 30);
                buttonGroup.add(optionButton);
            } else if (problem.getType().equals(Problem.multiOptionProblem)) {
                JCheckBox optionButton = new JCheckBox(option + " " + options.get(i));
                optionPanel.add(optionButton);
                optionButton.addActionListener(e -> {
                    if (!(Main.user instanceof Student)) {
                        JOptionPane.showMessageDialog(null, "只有学生可以答题");
                        buttonGroup.clearSelection();
                        return;
                    }
                    if (optionButton.isSelected())
                        currentOption += option;
                    else
                        currentOption = currentOption.replace(option, "");
                    if (paper == null) {
                        this.answer.setContent(currentOption);
                        return;
                    }
                    Answer answer = new Answer(paper, problem, (Student) Main.user, currentOption);
                    task.setProblemAnswer(problem, answer);
                    showProblem(currentPage);
                });
                for (char c : currentOption.toCharArray()) {
                    if (c == option.charAt(0)) optionButton.setSelected(true);
                }
                optionButton.setBounds(42 + column * 230, 30 + row * 60, 200, 30);
            }
        }
        if (Main.user instanceof Student && paper == null) {
            problemOKButton.setVisible(true);
        }
    }

    public void clearProblem() {
        scoreField.setText("");
        problemTitleField.setText("");
        contentArea.setText("");
        problemIDField.setText("");
        paperListPanel.setVisible(false);
        problemListPanel.setVisible(false);
        problemPanel.setVisible(false);
        paperPanel.setVisible(false);
        if (Main.user instanceof Student&&paper==null){
            problemOKButton.setVisible(false);
            answer = null;
        }
        problem = null;
    }

    public void updatePaperTaskTable() throws SQLException {
        if (paper == null) return;
        paperTasksTable.setModel(getPaperTaskTable());
        paperTasksTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        paperTasksTable.getColumnModel().getColumn(1).setPreferredWidth(30);
        paperTasksTable.getColumnModel().getColumn(2).setPreferredWidth(70);
    }

    private DefaultTableModel getPaperTaskTable() throws SQLException {
        ArrayList<Task> tasks = paper.getTasksList();

        Object[][] data = new Object[tasks.size()][4];
        String[] columnNames;
        int i = 0;
        if (Main.user instanceof Student) {
            for (Task task : tasks) {
                data[i][0] = i + 1;
                data[i][1] = task.getStudent().getId();
                data[i][2] = task.getStudent().getName();
                i++;
            }
            columnNames = new String[]{"编号", "学生ID", "学生姓名"};
        } else {
            for (Task task : tasks) {
                data[i][0] = i + 1;
                data[i][1] = task.getStudent().getId();
                data[i][2] = task.getStudent().getName();
                data[i][3] = task.getGrade();
                i++;
            }
            columnNames = new String[]{"编号", "学生ID", "学生姓名", "总分"};
        }


        return new DefaultTableModel(data, columnNames);
    }

    public void updatePaperProblemTable() throws SQLException {
        if (paper == null) return;
        paperProblemsTable.setModel(getPaperProblemTable());
        paperProblemsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        paperProblemsTable.getColumnModel().getColumn(1).setPreferredWidth(30);
        paperProblemsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        paperProblemsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
    }

    private DefaultTableModel getPaperProblemTable() {
        ArrayList<Problem> problems = paper.getProblemsList();

        Object[][] data = new Object[problems.size()][7];

        String[] columnNames;
        int i = 0;
        if (Main.user instanceof Student) {
            for (Problem problem : problems) {
                data[i][0] = i + 1;
                data[i][1] = problem.getId();
                data[i][2] = problem.getTitle();
                data[i][3] = problem.getType();
                data[i][4] = problem.getStatus();
                data[i][5] = paper.getScoreList().get(i);
                i++;
            }
            columnNames = new String[]{"题号", "ID", "标题", "类型", "状态", "分数"};
        } else {
            for (Problem problem : problems) {
                data[i][0] = i + 1;
                data[i][1] = problem.getId();
                data[i][2] = problem.getTitle();
                data[i][3] = problem.getType();
                data[i][4] = problem.getStatus();
                data[i][5] = paper.getScoreList().get(i);
                data[i][6] = "删除";
                i++;
            }
            columnNames = new String[]{"题号", "ID", "标题", "类型", "状态", "分数", ""};
        }


        return new DefaultTableModel(data, columnNames);
    }

    public void updatePaperTable() throws SQLException {
        paperTable.setModel(getPaperModel());
        paperTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        paperTable.getColumnModel().getColumn(1).setPreferredWidth(30);
        paperTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        paperTable.getColumnModel().getColumn(3).setPreferredWidth(30);
        paperTable.getColumnModel().getColumn(4).setPreferredWidth(50);
    }

    private DefaultTableModel getPaperModel() throws SQLException {
        ArrayList<Paper> papers = Paper.getAllPapers();
        Object[][] data = new Object[papers.size()][10];
        int i = 0;
        String[] columnNames;
        if (Main.user instanceof Student) {
            for (Paper paper : papers) {
                data[i][0] = "□";
                data[i][1] = paper.getId();
                data[i][2] = paper.getTitle();
                data[i][3] = paper.getAuthor().getId();
                data[i][4] = paper.getAuthor().getName();
                if (paper.getDate() != null)
                    data[i][5] = paper.getDate().toString() + " " + paper.getTime().toString();
                data[i][6] = paper.getStatus();
                data[i][7] = paper.getDuration();
                if (paper.getDuration() == -1) data[i][7] = "尚未设置";
                data[i][8] = "查看";
                i++;
            }
            columnNames = new String[]{"", "ID", "标题", "作者ID", "作者名", "考试时间", "状态", "考试时长", "",};
        } else {
            for (Paper paper : papers) {
                data[i][0] = "□";
                data[i][1] = paper.getId();
                data[i][2] = paper.getTitle();
                data[i][3] = paper.getAuthor().getId();
                data[i][4] = paper.getAuthor().getName();
                if (paper.getDate() != null)
                    data[i][5] = paper.getDate().toString() + " " + paper.getTime().toString();
                data[i][6] = paper.getStatus();
                data[i][7] = paper.getDuration();
                data[i][8] = "查看";
                data[i][9] = "删除";
                i++;
            }
            columnNames = new String[]{"", "ID", "标题", "作者ID", "作者名", "考试时间", "状态", "考试时长", "", ""};
        }
        return new DefaultTableModel(data, columnNames);
    }

    public void updateProblemTable() throws SQLException {
        problemsTable.setModel(getProblemTableModel());
        problemsTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        problemsTable.getColumnModel().getColumn(1).setPreferredWidth(30);
        problemsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        problemsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
    }

    private DefaultTableModel getProblemTableModel() throws SQLException {
        ArrayList<Problem> problems = Problem.getAllProblems();

        Object[][] data = new Object[problems.size()][8];
        String[] columnNames;
        int i = 0;
        if (Main.user instanceof Student) {
            for (Problem problem : problems) {
                data[i][0] = "□";
                data[i][1] = problem.getId();
                data[i][2] = problem.getTitle();
                data[i][3] = problem.getType();
                data[i][4] = problem.getStatus();
                data[i][5] = "查看";
                i++;
            }
            columnNames = new String[]{"", "ID", "标题", "类型", "状态", ""};
        } else {
            for (Problem problem : problems) {
                data[i][0] = "□";
                data[i][1] = problem.getId();
                data[i][2] = problem.getTitle();
                data[i][3] = problem.getType();
                data[i][4] = problem.getStatus();
                data[i][5] = "查看";
                data[i][6] = "插入";
                data[i][7] = "删除";
                i++;
            }
            columnNames = new String[]{"", "ID", "标题", "类型", "状态", "", "", ""};
        }


        return new DefaultTableModel(data, columnNames);
    }

    final int buttonAmountInOneRow = 4;
    final int buttonAmountInOneColumn = 4;

    private void showProblem(int page) {
        problemRightListPanel.removeAll();
        problemRightListPanel.repaint();
        ArrayList<Problem> allProblems = paper.getProblemsList();

        int sz = allProblems == null ? 0 : allProblems.size();
        if (Main.user instanceof Teacher || Main.user instanceof Manager) sz++;
        sz--;
        int problemsAmount = buttonAmountInOneColumn * buttonAmountInOneRow;
        int lastPage = sz / problemsAmount;
        if (currentPage > lastPage) currentPage = lastPage + 1;
        if (page == -1) page = lastPage;
        else page = Math.min(lastPage, page);

        ArrayList<Problem> problems = new ArrayList<>();

        int i = 0;
        if (allProblems != null) {
            for (Problem problem : allProblems) {
                if (i >= problemsAmount * (page + 1)) break;
                if (i >= problemsAmount * page) {
                    problems.add(problem);
                }
                i++;
            }
        }

        JButton[][] problemsButtonList = new JButton[buttonAmountInOneColumn][buttonAmountInOneRow];
        i = 0;
        for (Problem problem : problems) {
            problemsButtonList[i / buttonAmountInOneRow][i % buttonAmountInOneRow] = new JButton(String.valueOf(page * problemsAmount + i + 1));
            problemsButtonList[i / buttonAmountInOneRow][i % buttonAmountInOneRow].addActionListener(e -> {
                if (Main.user instanceof Student && !paper.checkStatus()) {
                    JOptionPane.showMessageDialog(null, "考试未开放查看！");
                    return;
                }

                problemListPanel.setVisible(false);
                paperListPanel.setVisible(false);
                problemPanel.setVisible(true);
                setProblem(problem);
            });
            if(Main.user instanceof Student && paper!=null&&task!=null){
                String content = task.getProblemAnswer().get(page * problemsAmount + i).getContent();
                if (content != null && !content.isEmpty()) {
                    problemsButtonList[i / buttonAmountInOneRow][i % buttonAmountInOneRow].setBackground(new Color(152,251,152));
                }
            }
            i++;
        }
        if (i < problemsAmount) if (Main.user instanceof Teacher || Main.user instanceof Manager) {
            JFrame This = this;
            problemsButtonList[i / buttonAmountInOneRow][i % buttonAmountInOneRow] = new JButton("+");
            problemsButtonList[i / buttonAmountInOneRow][i % buttonAmountInOneRow].addActionListener(e -> {
                if (paper.getDate() == null || paper.getTime() == null || paper.getDuration() == -1) {
                    JOptionPane.showMessageDialog(null, "请先填写开始时间和持续时间");
                    return;
                }
                Main.problemManageFrame.setVisible(true);
                Main.problemManageFrame.setFromFrame(This);
                Main.problemManageFrame.setProblem(null);
                problemListPanel.setVisible(false);
                paperListPanel.setVisible(false);
                setVisible(false);
            });
        }
        for (int row = 0; row < buttonAmountInOneColumn; ++row) {
            for (int column = 0; column < buttonAmountInOneRow; ++column) {
                if (problemsButtonList[row][column] != null) {
                    problemsButtonList[row][column].setBounds(10 + column * 45, 20 + row * 42, 40, 38);
                    problemsButtonList[row][column].setMargin(new Insets(0, 0, 0, 0));
                    problemsButtonList[row][column].setFont(new Font("", Font.PLAIN, 10));
                    problemRightListPanel.add(problemsButtonList[row][column]);
                }
            }
        }
    }

    public void setUser(User user) {
        if (user instanceof Student) {
            titleField.setEditable(false);
            timeField.setEditable(false);
            openComboBox.setEditable(false);
            durationField.setEditable(false);
            openComboBox.setVisible(false);
            openTextField.setVisible(true);

            scoreField.setEditable(false);
            problemEditButton.setVisible(false);

            okButton.setVisible(false);
            deleteAllSelectProblemButton.setVisible(false);
            insertAllSelectProblemButton.setVisible(false);
            deleteAllSelectPaperButton.setVisible(false);

            newPaperButton.setVisible(false);
            okCompletePaperButton.setVisible(true);
            studentPaperButton.setVisible(true);

            problemOKButton.setVisible(false);
            problemOKButton.setText("提交");
        } else {
            titleField.setEditable(true);
            timeField.setEditable(true);
            openComboBox.setEditable(true);
            durationField.setEditable(true);
            openComboBox.setVisible(true);
            openTextField.setVisible(false);

            scoreField.setEditable(true);
            problemEditButton.setVisible(true);

            okButton.setVisible(true);
            deleteAllSelectProblemButton.setVisible(true);
            insertAllSelectProblemButton.setVisible(true);
            deleteAllSelectPaperButton.setVisible(true);

            newPaperButton.setVisible(true);
            okCompletePaperButton.setVisible(false);
            studentPaperButton.setVisible(false);

            problemOKButton.setVisible(true);
            problemOKButton.setText("保存分数");
        }
    }

    public void openFrameAndProblemList() {
        this.setVisible(true);
        paperListPanel.setVisible(false);
        problemListPanel.setVisible(true);
    }

    public void openFrameAndPaperList() {
        this.setVisible(true);
        paperListPanel.setVisible(true);
        problemListPanel.setVisible(false);
    }

    private void exitFrame() {
        setVisible(false);
        Main.userFrame.setVisible(true);
        clearPaper();
        clearProblem();
        paperListPanel.setVisible(true);
    }
}
