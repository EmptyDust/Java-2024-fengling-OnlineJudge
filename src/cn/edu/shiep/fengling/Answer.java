package cn.edu.shiep.fengling;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class Answer {
    int id;
    int paperID;
    int problemID;
    int studentID;
    String problemType;
    String content;
    String stdAnswer;

    public Answer(Problem problem) {
        stdAnswer = problem.getAnswer();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStdAnswer() {
        return stdAnswer;
    }

    public void setStdAnswer(String stdAnswer) {
        if (Objects.equals(problemType, Problem.multiOptionProblem)) {
            char[] chars = stdAnswer.toCharArray();
            Arrays.sort(chars);
            stdAnswer = new String(chars);
        }
        this.stdAnswer = stdAnswer;
    }

    public Answer setProblemType(String problemType) {
        this.problemType = problemType;
        return this;
    }

    public boolean getTrueOrFalse() {
        if (content == null || stdAnswer == null) return false;
        if (Objects.equals(problemType, Problem.multiOptionProblem)) {
            char[] chars = stdAnswer.toCharArray();
            Arrays.sort(chars);
            stdAnswer = new String(chars);
            char[] chars2 = content.toCharArray();
            Arrays.sort(chars2);
            content = new String(chars2);
        }
        return content.equals(stdAnswer);
    }

    public Answer(Paper paper, Problem problem, Student student, String content) {
        paperID = paper.getId();
        problemID = problem.getId();
        studentID = student.getId();
        this.content = content;
    }

    public Answer(int paperID, int problemID, int studentID, String content, String stdAnswer) {
        this.paperID = paperID;
        this.problemID = problemID;
        this.studentID = studentID;
        this.content = content;
        this.stdAnswer = stdAnswer;
    }

    Answer(int id, int paperID, int problemID, int studentID, String content, String stdAnswer) {
        this.id = id;
        this.paperID = paperID;
        this.problemID = problemID;
        this.studentID = studentID;
        this.content = content;
        this.stdAnswer = stdAnswer;
    }

    void insert() throws SQLException {
        this.id = Main.instance.addAnswer(this);
    }

    void update() throws SQLException {
        Main.instance.updateAnswer(this);
    }

    void delete() throws SQLException {
        Main.instance.deleteAnswer(this);
    }

    static Answer get(int id) throws SQLException {
        return Main.instance.getAnswer(id);
    }
}
