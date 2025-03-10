package cn.edu.shiep.fengling;

import java.sql.SQLException;
import java.util.ArrayList;

public class Task {
    int id;
    Paper paper;
    Student student;
    int grade;
    ArrayList<Answer> problemAnswer = new ArrayList<>();

    public Paper getPaper() {
        return paper;
    }

    public ArrayList<Answer> getProblemAnswer() {
        return problemAnswer;
    }


    public Answer getProblemAnswer(Problem problem) {
        for (Answer a : problemAnswer) {
            if (a.problemID == problem.getId()) return a;
        }
        return null;
    }

    public void clearProblemAnswer(Problem problem) {
        for (Answer answer : problemAnswer) {
            if (answer.problemID == problem.getId()) {
                answer.content = "";
            }
        }
    }

    public int getGrade() {
        return grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public void setProblemAnswer(ArrayList<Answer> problemAnswer) {
        this.problemAnswer = problemAnswer;
    }

    public void setProblemAnswer(Problem problem, Answer answer) {
        answer.setStdAnswer(problem.getAnswer());
        for (int i = 0; i < paper.getProblemsList().size(); i++) {
            if (paper.getProblemsList().get(i).getId() == problem.getId()) {
                problemAnswer.set(i, answer);
                break;
            }
        }
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public Task(Paper paper, Student student) {
        this.paper = paper;
        this.student = student;
        for (Problem problem : paper.getProblemsList()) {
            problemAnswer.add(new Answer(paper, problem, student, "").setProblemType(problem.getType()));
        }
    }

    Task(int id, Paper paper, Student student, int grade, ArrayList<Answer> problemAnswer) {
        this.id = id;
        this.paper = paper;
        this.grade = grade;
        this.problemAnswer = problemAnswer;
        this.student = student;
    }


    public void insert() throws SQLException {
        this.id = Main.instance.addTask(this);
    }

    public void update() throws SQLException {
        for (int i = 0; i < problemAnswer.size(); i++) {
            problemAnswer.get(i).insert();
            if (problemAnswer.get(i).getTrueOrFalse()) {
                grade += paper.getScoreList().get(i);
            }
        }
        Main.instance.updateTask(this);
    }

    public void delete() throws SQLException {
        Main.instance.deleteTask(this);
    }

    static Task get(int id) throws SQLException {
        return Main.instance.getTask(id);
    }

    public static ArrayList<Task> getTaskByStudentID(int id) throws SQLException {
        return Main.instance.getTaskByStudentID(id);
    }

    static String answerListToString(ArrayList<Answer> problemAnswer) {
        StringBuilder answer = new StringBuilder();
        for (Answer a : problemAnswer) {
            answer.append(a.id).append("&");
        }
        return answer.toString();
    }

    static ArrayList<Answer> stringToAnswerList(String answer) throws SQLException {
        ArrayList<Answer> answerList = new ArrayList<>();
        if (answer == null) return answerList;
        String[] split = answer.split("&");
        for (String s : split)
            if (s != null && !s.isEmpty()) {
                int id = Integer.parseInt(s);
                answerList.add(Answer.get(id));
            }
        return answerList;
    }
}

