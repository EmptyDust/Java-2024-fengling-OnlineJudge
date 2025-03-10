package cn.edu.shiep.fengling.frame;

import cn.edu.shiep.fengling.Problem;
import cn.edu.shiep.fengling.listener.JTextFieldHintListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProblemManageFrame extends JFrame {

    String type = null;
    JLabel optionALabel = new JLabel("选项A：");
    JTextField optionATextField = new JTextField(10);
    JLabel optionBLabel = new JLabel("选项B：");
    JTextField optionBTextField = new JTextField(10);
    JLabel optionCLabel = new JLabel("选项C：");
    JTextField optionCTextField = new JTextField(10);
    JLabel optionDLabel = new JLabel("选项D：");
    JTextField optionDTextField = new JTextField(10);
    JLabel optionELabel = new JLabel("选项E：");
    JTextField optionETextField = new JTextField(10);
    JLabel optionFLabel = new JLabel("选项F：");
    JTextField optionFTextField = new JTextField(10);
    JLabel optionGLabel = new JLabel("选项G：");
    JTextField optionGTextField = new JTextField(10);
    JLabel optionHLabel = new JLabel("选项H：");
    JTextField optionHTextField = new JTextField(10);
    JFrame fromFrame;
    JTextField titleField;
    JTextArea contentArea;
    JTextArea answerField;
    JTextField IDField;
    JComboBox<String> openBox;

    Problem problem = null;

    JPanel TFOptionPanel;
    JPanel oneOptionPanel;
    JPanel multiOptionPanel;
    JPanel clozeOptionPanel;

    JButton okButton;
    JLabel answerLabel;
    JLabel contentLabel;
    JLabel titleLabel;
    JLabel IDLabel;

    public void setFromFrame(JFrame fromFrame) {
        this.fromFrame = fromFrame;
    }

    public ProblemManageFrame() {
        setTitle("Problem Manage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        TFOptionPanel = new JPanel();
        TFOptionPanel.setLayout(null);
        TFOptionPanel.setBounds(10, 10, 700, 500);
        TFOptionPanel.setBorder(BorderFactory.createTitledBorder(Problem.TFProblem));

        oneOptionPanel = new JPanel();
        oneOptionPanel.setLayout(null);
        oneOptionPanel.setBounds(10, 10, 700, 500);
        oneOptionPanel.setBorder(BorderFactory.createTitledBorder(Problem.OneOptionProblem));

        multiOptionPanel = new JPanel();
        multiOptionPanel.setLayout(null);
        multiOptionPanel.setBounds(10, 10, 700, 500);
        multiOptionPanel.setBorder(BorderFactory.createTitledBorder(Problem.multiOptionProblem));

        clozeOptionPanel = new JPanel();
        clozeOptionPanel.setLayout(null);
        clozeOptionPanel.setBounds(10, 10, 700, 500);
        clozeOptionPanel.setBorder(BorderFactory.createTitledBorder(Problem.clozeProblem));


        titleLabel = new JLabel("标题：");
        titleLabel.setBounds(30, 30, 100, 30);

        titleField = new JTextField();
        titleField.setColumns(10);
        titleField.setBounds(100, 30, 250, 30);

        IDLabel = new JLabel("ID:");
        IDLabel.setBounds(360, 30, 40, 30);

        IDField = new JTextField();
        IDField.setColumns(10);
        IDField.setBounds(400, 30, 100, 30);
        IDField.setEditable(false);

        openBox = new JComboBox<>();
        openBox.setBounds(510, 30, 100, 30);
        openBox.addItem(Problem.open);
        openBox.addItem(Problem.close);

        contentLabel = new JLabel("试题内容：");
        contentLabel.setBounds(30, 70, 100, 20);

        contentArea = new JTextArea();
        contentArea.setBounds(100, 70, 570, 100);

        answerLabel = new JLabel("答案：");
        answerLabel.setBounds(30, 400, 100, 20);

        answerField = new JTextArea();
        answerField.setColumns(10);
        answerField.setBounds(100, 400, 570, 80);

        optionALabel.setBounds(30, 200, 100, 30);
        optionATextField.setBounds(100, 200, 200, 30);
        optionBLabel.setBounds(330, 200, 100, 30);
        optionBTextField.setBounds(400, 200, 200, 30);
        optionCLabel.setBounds(30, 250, 100, 30);
        optionCTextField.setBounds(100, 250, 200, 30);
        optionDLabel.setBounds(330, 250, 100, 30);
        optionDTextField.setBounds(400, 250, 200, 30);
        optionELabel.setBounds(30, 300, 100, 30);
        optionETextField.setBounds(100, 300, 200, 30);
        optionFLabel.setBounds(330, 300, 100, 30);
        optionFTextField.setBounds(400, 300, 200, 30);
        optionGLabel.setBounds(30, 350, 100, 30);
        optionGTextField.setBounds(100, 350, 200, 30);
        optionHLabel.setBounds(330, 350, 100, 30);
        optionHTextField.setBounds(400, 350, 200, 30);


        okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            if (type == null) {
                clearProblem();
                return;
            }
            String title = titleField.getText();
            String content = contentArea.getText();
            String answer = answerField.getText();
            String status = openBox.getSelectedItem().toString();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入标题");
                return;
            }
            if (content.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入题目内容");
                return;
            }
            if (answer.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入题目答案");
                return;
            }

            if (problem == null) {
                ArrayList<String> options;
                if (type.equals(Problem.TFProblem)) {
                    options = new ArrayList<>();
                    options.add("Yes");
                    options.add("No");
                } else if (type.equals(Problem.OneOptionProblem)) {
                    options = getOptions();
                } else if (type.equals(Problem.multiOptionProblem)) {
                    options = getOptions();
                } else {
                    options = new ArrayList<>();
                }
                problem = new Problem(title, content, type, status, options, answer);
                try {
                    problem.insert();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (fromFrame instanceof PaperFrame) {
                    try {
                        ((PaperFrame) fromFrame).addProblem(problem);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    setVisible(false);
                    fromFrame.setVisible(true);
                }
                else
                    JOptionPane.showMessageDialog(null, "成功新建题目");
            } else {
                problem.setTitle(title);
                problem.setContent(content);
                problem.setAnswer(answer);
                problem.setStatus(status);
                if (type.equals(Problem.OneOptionProblem)) {
                    problem.setOptions(getOptions());
                }
                if (type.equals(Problem.multiOptionProblem)) {
                    problem.setOptions(getOptions());
                }
                try {
                    problem.update();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (fromFrame instanceof PaperFrame) {
                    setVisible(false);
                    fromFrame.setVisible(true);
                    ((PaperFrame) fromFrame).setProblem(problem);
                }
            }
            setProblem(problem);
        });
        okButton.setBounds(720, 470, 100, 30);
        panel.add(okButton);

        JButton TFOptionProblemButton = new JButton("新建" + Problem.TFProblem);
        TFOptionProblemButton.addActionListener(e -> {
            problem = null;
            setTFProblemPanel();
        });
        TFOptionProblemButton.setBounds(720, 20, 200, 30);
        panel.add(TFOptionProblemButton);


        JButton oneOptionProblemButton = new JButton("新建" + Problem.OneOptionProblem);
        oneOptionProblemButton.addActionListener(e -> {
            problem = null;
            setOneOptionPanel();
        });
        oneOptionProblemButton.setBounds(720, 60, 200, 30);
        panel.add(oneOptionProblemButton);

        JButton multiOptionProblemButton = new JButton("新建" + Problem.multiOptionProblem);
        multiOptionProblemButton.addActionListener(e -> {
            problem = null;
            setMultiOptionPanel();
        });
        multiOptionProblemButton.setBounds(720, 100, 200, 30);
        panel.add(multiOptionProblemButton);


        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(720, 510, 200, 30);
        exitButton.addActionListener(e -> {
            setVisible(false);
            TFOptionPanel.setVisible(false);
            oneOptionPanel.setVisible(false);
            multiOptionPanel.setVisible(false);
            type = null;
            problem = null;
            fromFrame.setVisible(true);
            IDField.setText("");
        });
        panel.add(exitButton);

        panel.add(TFOptionPanel);
        panel.add(oneOptionPanel);
        panel.add(multiOptionPanel);

        clearProblem();

        add(panel);
    }

    private @NotNull ArrayList<String> getOptions() {
        String A = optionATextField.getText();
        String B = optionBTextField.getText();
        String C = optionCTextField.getText();
        String D = optionDTextField.getText();
        String E = optionETextField.getText();
        String F = optionFTextField.getText();
        String G = optionGTextField.getText();
        String H = optionHTextField.getText();
        ArrayList<String> options = new ArrayList<>();
        if (!A.isEmpty())
            options.add(A);
        if (!B.isEmpty())
            options.add(B);
        if (!C.isEmpty())
            options.add(C);
        if (!D.isEmpty())
            options.add(D);
        if (!E.isEmpty())
            options.add(E);
        if (!F.isEmpty())
            options.add(F);
        if (!G.isEmpty())
            options.add(G);
        if (!H.isEmpty())
            options.add(H);
        return options;
    }

    private void setMultiOptionPanel() {
        clearProblem();
        multiOptionPanel.add(titleField);
        multiOptionPanel.add(titleLabel);
        multiOptionPanel.add(contentLabel);
        multiOptionPanel.add(contentArea);
        multiOptionPanel.add(optionALabel);
        multiOptionPanel.add(optionATextField);
        multiOptionPanel.add(optionBLabel);
        multiOptionPanel.add(optionBTextField);
        multiOptionPanel.add(optionCLabel);
        multiOptionPanel.add(optionCTextField);
        multiOptionPanel.add(optionDLabel);
        multiOptionPanel.add(optionDTextField);
        multiOptionPanel.add(optionELabel);
        multiOptionPanel.add(optionETextField);
        multiOptionPanel.add(optionFLabel);
        multiOptionPanel.add(optionFTextField);
        multiOptionPanel.add(optionGLabel);
        multiOptionPanel.add(optionGTextField);
        multiOptionPanel.add(optionHLabel);
        multiOptionPanel.add(optionHTextField);
        multiOptionPanel.add(answerLabel);
        multiOptionPanel.add(answerField);
        multiOptionPanel.add(IDField);
        multiOptionPanel.add(IDLabel);
        multiOptionPanel.add(openBox);
        for(FocusListener focusListener:answerField.getFocusListeners()){
            answerField.removeFocusListener(focusListener);
        }
        answerField.addFocusListener(new JTextFieldHintListener(answerField,"请输入正确选项。顺序无关。"));

        TFOptionPanel.setVisible(false);
        oneOptionPanel.setVisible(false);
        multiOptionPanel.setVisible(true);
        okButton.setVisible(true);

        type = Problem.multiOptionProblem;
    }

    private void setOneOptionPanel() {
        clearProblem();
        oneOptionPanel.add(titleLabel);
        oneOptionPanel.add(titleField);
        oneOptionPanel.add(contentArea);
        oneOptionPanel.add(contentLabel);
        oneOptionPanel.add(optionALabel);
        oneOptionPanel.add(optionATextField);
        oneOptionPanel.add(optionBLabel);
        oneOptionPanel.add(optionBTextField);
        oneOptionPanel.add(optionCLabel);
        oneOptionPanel.add(optionCTextField);
        oneOptionPanel.add(optionDLabel);
        oneOptionPanel.add(optionDTextField);
        oneOptionPanel.add(optionELabel);
        oneOptionPanel.add(optionETextField);
        oneOptionPanel.add(optionFLabel);
        oneOptionPanel.add(optionFTextField);
        oneOptionPanel.add(optionGLabel);
        oneOptionPanel.add(optionGTextField);
        oneOptionPanel.add(optionHLabel);
        oneOptionPanel.add(optionHTextField);
        oneOptionPanel.add(answerLabel);
        oneOptionPanel.add(answerField);
        oneOptionPanel.add(IDField);
        oneOptionPanel.add(IDLabel);
        oneOptionPanel.add(openBox);
        for(FocusListener focusListener:answerField.getFocusListeners()){
            answerField.removeFocusListener(focusListener);
        }
        answerField.addFocusListener(new JTextFieldHintListener(answerField,"请输入正确选项。"));

        TFOptionPanel.setVisible(false);
        oneOptionPanel.setVisible(true);
        multiOptionPanel.setVisible(false);
        okButton.setVisible(true);

        type = Problem.OneOptionProblem;
    }

    private void setTFProblemPanel() {
        clearProblem();
        TFOptionPanel.add(titleField);
        TFOptionPanel.add(contentLabel);
        TFOptionPanel.add(contentArea);
        TFOptionPanel.add(titleLabel);
        TFOptionPanel.add(answerLabel);
        TFOptionPanel.add(answerField);
        TFOptionPanel.add(IDField);
        TFOptionPanel.add(IDLabel);
        TFOptionPanel.add(openBox);
        for(FocusListener focusListener:answerField.getFocusListeners()){
            answerField.removeFocusListener(focusListener);
        }
        answerField.addFocusListener(new JTextFieldHintListener(answerField,"正确请输入A,错误请输入B。"));

        TFOptionPanel.setVisible(true);
        oneOptionPanel.setVisible(false);
        multiOptionPanel.setVisible(false);
        okButton.setVisible(true);

        type = Problem.TFProblem;
    }

    void setProblem(Problem problem) {
        clearProblem();
        if (problem == null) {
            return;
        }
        type = problem.getType();
        switch (type) {
            case Problem.TFProblem -> setTFProblemPanel();
            case Problem.OneOptionProblem -> setOneOptionPanel();
            case Problem.multiOptionProblem -> setMultiOptionPanel();
        }
        IDField.setText(String.valueOf(problem.getId()));
        titleField.setText(problem.getTitle());
        contentArea.setText(problem.getContent());
        answerField.setText(problem.getAnswer());
        openBox.setSelectedItem(problem.getStatus());
        ArrayList<String> options = problem.getOptions();
        switch (options.size()) {
            case 8:
                optionHTextField.setText(options.get(7));
            case 7:
                optionGTextField.setText(options.get(6));
            case 6:
                optionFTextField.setText(options.get(5));
            case 5:
                optionETextField.setText(options.get(4));
            case 4:
                optionDTextField.setText(options.get(3));
            case 3:
                optionCTextField.setText(options.get(2));
            case 2:
                optionBTextField.setText(options.get(1));
            case 1:
                optionATextField.setText(options.get(0));
        }
        this.problem = problem;
    }

    private void clearProblem() {
        problem = null;

        optionATextField.setText("");
        optionBTextField.setText("");
        optionCTextField.setText("");
        optionDTextField.setText("");
        optionETextField.setText("");
        optionFTextField.setText("");
        optionGTextField.setText("");
        optionHTextField.setText("");
        titleField.setText("");
        contentArea.setText("");
        answerField.setText("");
        IDField.setText("");
        TFOptionPanel.setVisible(false);
        oneOptionPanel.setVisible(false);
        multiOptionPanel.setVisible(false);
        okButton.setVisible(false);
    }

    public void showFrame(JFrame fromFrame){
        this.fromFrame = fromFrame;
        setTFProblemPanel();
    }
}


