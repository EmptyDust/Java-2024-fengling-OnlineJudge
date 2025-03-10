package cn.edu.shiep.fengling;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Paper {
    int id;
    String title;
    String content;
    User author;
    Date date;
    Time time;
    ArrayList<Problem> problemsList = new ArrayList<>();
    ArrayList<Integer> scoreList = new ArrayList<>();
    int duration = -1;
    String status;
    public static final String open = "Open";
    public static final String close = "Close";

    public Paper() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Integer> getScoreList() {
        return scoreList;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Problem> getProblemsList() {
        return problemsList;
    }

    public int getScore(Problem problem) {
        for (int i = 0; i < problemsList.size(); i++) {
            if (problem.getId() == problemsList.get(i).getId()) {
                return scoreList.get(i);
            }
        }
        return -1;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProblemsList(ArrayList<Problem> problemsList) {
        this.problemsList = problemsList;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setScoreList(ArrayList<Integer> scoreList) {
        this.scoreList = scoreList;
    }

    public void setScore(Problem problem, int score) throws SQLException {
        for (int i = 0; i < problemsList.size(); i++) {
            if (problemsList.get(i).getId() == problem.getId()) {
                scoreList.set(i, score);
                break;
            }
        }
        update();
    }

    public boolean getProblemInPaper(int id) {
        for (Problem problem : problemsList) {
            if (problem.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Paper(String title, User author, String status) {
        this.title = title;
        this.status = status;
        this.author = author;
    }

    Paper(int id, String title, User author, Date date, Time time, String status, String content, ArrayList<Problem> ProblemsList, ArrayList<Integer> scoreList, int duration) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.status = status;
        this.problemsList = ProblemsList;
        this.author = author;
        this.duration = duration;
        this.scoreList = scoreList;
        this.content = content;
    }

    public boolean checkStatus() {
        if (status.equals(open)) return true;
        if (duration == -1) return false;
        if (time == null || date == null) return false;

        LocalDate currentDate = LocalDate.now();

        LocalDate localDate = date.toLocalDate();

        if (!localDate.equals(currentDate)) return false;

        LocalTime currentTime = LocalTime.now();

        LocalTime startLocalTime = time.toLocalTime();

        LocalTime endLocalTime = startLocalTime.plus(Duration.ofHours(0).plusMinutes(duration));

        Duration durationEnd = Duration.between(currentTime, endLocalTime);
        Duration duration = Duration.between(startLocalTime, currentTime);

        long startTime = duration.toMillis();
        long endTime = durationEnd.toMillis();
        if (endTime < 0) {
            return false;
        } else return startTime >= 0;
    }

    public void addProblem(Problem problem, int pos) throws SQLException {
        problemsList.add(pos, problem);
        update();
    }

    public void addProblem(Problem problem) throws SQLException {
        if (author == null) {
            author = Main.user;
            date = new Date(System.currentTimeMillis());
            time = new Time(System.currentTimeMillis());
            status = close;
            title = " ";
        }
        problemsList.add(problem);
        scoreList.add(0);
        update();
    }

    public void deleteProblem(int pos) throws SQLException {
        problemsList.remove(pos);
        update();
    }

    public void deleteProblem(Problem problem) throws SQLException {
        for (int i = 0; i < problemsList.size(); i++) {
            if (problemsList.get(i).getId() == problem.getId()) {
                problemsList.remove(i);
                scoreList.remove(i);
                break;
            }
        }
        update();
    }

    public void updateProblem(Problem problem, int pos) throws SQLException {
        problemsList.set(pos, problem);
        update();
    }

    public void insert() throws SQLException {
        this.id = Main.instance.addPaper(this);
        Main.updatePaperTable();
    }

    public void update() throws SQLException {
        Main.instance.updatePaper(this);
        Main.updatePaperTable();
    }

    public void delete() throws SQLException {
        Main.instance.deletePaper(this);
        Main.updatePaperTable();
    }

    public static Paper getPaper(int id) throws SQLException {
        return Main.instance.getPaper(id);
    }

    public static ArrayList<Paper> getAllPapers() throws SQLException {
        return Main.instance.queryPapersList();
    }

    static String scoresToString(ArrayList<Integer> scoreList) {
        if (scoreList == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (Integer i : scoreList) {
            result.append(i).append("&");
        }
        return result.toString();
    }

    static ArrayList<Integer> scoresFromString(String scores) {
        if (scores == null || scores.isEmpty()) return new ArrayList<>();
        ArrayList<Integer> scoreList = new ArrayList<>();
        for (String s : scores.split("&")) {
            if (s == null || s.isEmpty()) continue;
            scoreList.add(Integer.parseInt(s));
        }
        return scoreList;
    }

    static String problemtoString(ArrayList<Problem> problemsList) {
        if (problemsList == null) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        for (Problem problem : problemsList) {
            res.append(problem.id).append("&");
        }
        return res.toString();
    }

    static ArrayList<Problem> stringToProblemsList(String problems) throws SQLException {
        if (problems == null || problems.isEmpty()) return new ArrayList<>();
        ArrayList<Problem> problemsList = new ArrayList<>();
        String[] problemsArray = problems.split("&");
        for (String problem : problemsArray) {
            if (problem == null || problem.isEmpty()) continue;
            int id = Integer.parseInt(problem);
            Problem p = Problem.get(id);
            if (p == null) continue;
            problemsList.add(p);
        }
        return problemsList;
    }

    public ArrayList<Task> getTasksList() throws SQLException {
        return Main.instance.queryTaskByPaperID(this.id);
    }
}
