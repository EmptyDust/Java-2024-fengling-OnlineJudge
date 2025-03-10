package cn.edu.shiep.fengling;

import java.sql.SQLException;
import java.util.ArrayList;

public class Problem {
    int id;
    String title;
    String content;
    String type;
    String status;
    ArrayList<String> options;
    String answer;
    public static final String open = "Open";
    public static final String close = "Close";
    public static final String TFProblem = "判断题";
    public static final String OneOptionProblem = "单选题";
    public static final String multiOptionProblem = "多选题";
    public static final String clozeProblem = "填空题";

    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getType() {return type;}
    public String getStatus() {return status;}
    public String getAnswer() {return answer;}
    public ArrayList<String> getOptions() {return options;}

    public void setTitle(String title) {this.title = title;}
    public void setContent(String content) {this.content = content;}
    public void setStatus(String status) {this.status = status;}
    public void setAnswer(String answer) {this.answer = answer;}
    public void setOptions(ArrayList<String> options) {this.options = options;}

    public Problem(String title, String content, String type, String status, ArrayList<String> options, String answer) {
        this.content = content;
        this.title = title;
        this.options = options;
        this.type = type;
        this.status = status;
        this.answer = answer;
    }

    Problem(int id, String title, String content, String type, String status, ArrayList<String> options, String answer) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.options = options;
        this.type = type;
        this.answer = answer;
        this.status = status;
    }

    public void insert() throws SQLException {
        this.id = Main.instance.addProblem(this);
        Main.updateProblemTable();
    }

    public void update() throws SQLException {
        Main.instance.updateProblem(this);
        Main.updateProblemTable();
    }

    public void delete() throws SQLException {
        Main.instance.deleteProblem(this);
        Main.updateProblemTable();
    }

    public static ArrayList<Problem> getAllProblems() throws SQLException {
        ArrayList<Problem> problems = Main.instance.queryProblemList();
        if(Main.user instanceof Teacher || Main.user instanceof Manager){
            return problems;
        }
        ArrayList<Problem> result = new ArrayList<>();
        for (Problem problem : problems) {
            if (problem.status.equals(open)) {
                result.add(problem);
            }
        }
        return result;
    }

    public static Problem get(int id) throws SQLException {
        return Main.instance.getProblem(id);
    }
}
