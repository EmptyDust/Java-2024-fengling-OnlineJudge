package cn.edu.shiep.fengling;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private final Statement cmd;

    DatabaseManager() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
        String url = "jdbc:mysql://localhost:3306/mydb", user = "root", pwd = "root";
        Connection conn = DriverManager.getConnection(url, user, pwd);
        cmd = conn.createStatement();
        staticAnswerID = getAnswerID() + 1;
        staticTaskID = getTaskID() + 1;
        staticPaperID = getPaperID() + 1;
        staticProblemID = getProblemID() + 1;
    }

    public void addTeacher(@NotNull Teacher teacher) throws SQLException {
        int teacherID = teacher.id;
        String name = teacher.name;
        String password = teacher.password;
        String major = teacher.major;
        String sql = "insert into teachers (id,name,password,major) values ('"
                + teacherID + "','" + name + "','" + password + "','" + major + "');";
        cmd.execute(sql);
    }

    boolean loginTeacher(@NotNull Teacher teacher) throws SQLException {
        String sql = "select * from teachers where id = " + teacher.id +
                " and password = '" + teacher.password + "';";
        ResultSet rs = cmd.executeQuery(sql);
        return rs.next();
    }

    ArrayList<Teacher> queryTeachersList() throws SQLException {
        String sql = "select * from teachers;";
        ResultSet rs = cmd.executeQuery(sql);
        ArrayList<Teacher> teachers = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String major = rs.getString("major");
            teachers.add(new Teacher(id, name, password, major));
        }
        return teachers;
    }

    void clearTeachersList() throws SQLException {
        String sql = "delete from teachers;";
        cmd.execute(sql);
    }

    Teacher getTeacherById(int id) throws SQLException {
        String sql = "select * from teachers where id = " + id;
        ResultSet rs = cmd.executeQuery(sql);
        if (rs.next()) {
            int teacherID = rs.getInt("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String major = rs.getString("major");
            return new Teacher(teacherID, name, password, major);
        }
        return null;
    }

    void deleteTeacher(@NotNull Teacher teacher) throws SQLException {
        int id = teacher.id;
        String sql = "delete from teachers where id = " + id;
        cmd.execute(sql);
    }

    void updateTeacher(@NotNull Teacher teacher) throws SQLException {
        int id = teacher.id;
        String name = teacher.name;
        String password = teacher.password;
        String major = teacher.major;
        String sql = "update teachers set name = '"
                + name + "', password = '" + password + "', major = '" + major + "' where id = " + id;
        cmd.execute(sql);
    }

    void addStudent(@NotNull Student student) throws SQLException {
        int studentID = student.id;
        String name = student.name;
        String password = student.password;
        String major = student.major;
        String enrollmentYear = student.enrollmentYear;
        String Class = student.Class;

        String sql = "insert into students (id,name,password,major,enrollmentYear,class) values ('"
                + studentID + "','" + name + "','" + password + "','" + major + "','"
                + enrollmentYear + "','" + Class + "');";
        cmd.execute(sql);
    }

    boolean loginStudent(@NotNull Student student) throws SQLException {
        String sql = "select * from students where id = " + student.id + " and password = '" + student.password + "';";
        ResultSet rs = cmd.executeQuery(sql);
        return rs.next();
    }

    ArrayList<Student> queryStudentList() throws SQLException {
        String sql = "select * from students;";
        ResultSet rs = cmd.executeQuery(sql);
        ArrayList<Student> students = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String major = rs.getString("major");
            String enrollmentYear = rs.getString("enrollmentYear");
            String Class = rs.getString("class");
            students.add(new Student(id, name, password, major, enrollmentYear, Class));
        }
        return students;
    }

    void clearStudentList() throws SQLException {
        String sql = "delete from students;";
        cmd.execute(sql);
    }

    void deleteStudent(@NotNull Student student) throws SQLException {
        int id = student.id;
        String sql = "delete from students where id = " + id;
        cmd.execute(sql);
    }

    void updateStudent(@NotNull Student student) throws SQLException {
        int id = student.id;
        String name = student.name;
        String password = student.password;
        String major = student.major;
        String enrollmentYear = student.enrollmentYear;
        String Class = student.Class;
        String sql = "update students set name = '"
                + name + "', password = '" + password + "', major = '"
                + major + "', enrollmentYear = '"
                + enrollmentYear + "', class = '"
                + Class + "' where id = " + id;
        cmd.execute(sql);
    }

    Student getStudentById(int id) throws SQLException {
        String sql = "select * from students where id = " + id;
        ResultSet rs = cmd.executeQuery(sql);
        if (rs.next()) {
            int studentID = rs.getInt("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            String major = rs.getString("major");
            String enrollmentYear = rs.getString("enrollmentYear");
            String Class = rs.getString("class");
            return new Student(studentID, name, password, major, enrollmentYear, Class);
        }
        return null;
    }

    String defaultOption = "*";
    int staticProblemID = 0;

    private int getProblemID() throws SQLException {
        String sql = "select max(id) maxID from problems";
        ResultSet rs = cmd.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt("maxID");
        }
        return 0;
    }

    int addProblem(@NotNull Problem problem) throws SQLException {
        int problemID = staticProblemID;
        staticProblemID++;
        String content = problem.content;
        String title = problem.title;
        String type = problem.type;
        String status = problem.status;
        String optionA = defaultOption, optionB = defaultOption, optionC = defaultOption,
                optionD = defaultOption, optionE = defaultOption, optionF = defaultOption,
                optionG = defaultOption, optionH = defaultOption;
        ArrayList<String> options = problem.options;
        switch (options.size()) {
            case 8:
                optionH = options.get(7);
            case 7:
                optionG = options.get(6);
            case 6:
                optionF = options.get(5);
            case 5:
                optionE = options.get(4);
            case 4:
                optionD = options.get(3);
            case 3:
                optionC = options.get(2);
            case 2:
                optionB = options.get(1);
            case 1:
                optionA = options.get(0);
        }
        String answer = problem.answer;
        String sql = "insert into problems (id, content, type, optionA, "
                + "optionB, optionC, optionD, optionE, optionF, "
                + "optionG, optionH, answer, title, status) values ('"
                + problemID + "','" + content + "','" + type + "','" + optionA + "',"
                + "'" + optionB + "','" + optionC + "','" + optionD + "',"
                + "'" + optionE + "','" + optionF + "','" + optionG + "',"
                + "'" + optionH + "','" + answer + "','" + title + "','" + status
                + "') ";
        cmd.execute(sql);
        return problemID;
    }

    ArrayList<Problem> queryProblemList() throws SQLException {
        String sql = "select * from problems";
        ResultSet rs = cmd.executeQuery(sql);
        ArrayList<Problem> problems = new ArrayList<>();
        while (rs.next()) {
            int problemID = rs.getInt("id");
            String title = rs.getString("title");
            String content = rs.getString("content");
            String type = rs.getString("type");
            String status = rs.getString("status");
            ArrayList<String> options = new ArrayList<>();
            if (!rs.getString("optionA").equals(defaultOption)) options.add(rs.getString("optionA"));
            if (!rs.getString("optionB").equals(defaultOption)) options.add(rs.getString("optionB"));
            if (!rs.getString("optionC").equals(defaultOption)) options.add(rs.getString("optionC"));
            if (!rs.getString("optionD").equals(defaultOption)) options.add(rs.getString("optionD"));
            if (!rs.getString("optionE").equals(defaultOption)) options.add(rs.getString("optionE"));
            if (!rs.getString("optionF").equals(defaultOption)) options.add(rs.getString("optionF"));
            if (!rs.getString("optionG").equals(defaultOption)) options.add(rs.getString("optionG"));
            if (!rs.getString("optionH").equals(defaultOption)) options.add(rs.getString("optionH"));
            String answer = rs.getString("answer");
            problems.add(new Problem(problemID, title, content, type, status, options, answer));
        }
        return problems;
    }

    void clearProblemList() throws SQLException {
        String sql = "delete from problems";
        cmd.execute(sql);
    }

    void deleteProblem(@NotNull Problem problem) throws SQLException {
        int problemID = problem.id;
        String sql = "delete from problems where id = " + problemID;
        cmd.execute(sql);
    }

    void updateProblem(@NotNull Problem problem) throws SQLException {
        int problemID = problem.id;
        String title = problem.title;
        String content = problem.content;
        String type = problem.type;
        String status = problem.status;
        String optionA = defaultOption, optionB = defaultOption, optionC = defaultOption,
                optionD = defaultOption, optionE = defaultOption, optionF = defaultOption,
                optionG = defaultOption, optionH = defaultOption;
        ArrayList<String> options = problem.options;
        switch (options.size()) {
            case 8:
                optionH = options.get(7);
            case 7:
                optionG = options.get(6);
            case 6:
                optionF = options.get(5);
            case 5:
                optionE = options.get(4);
            case 4:
                optionD = options.get(3);
            case 3:
                optionC = options.get(2);
            case 2:
                optionB = options.get(1);
            case 1:
                optionA = options.get(0);
        }
        String answer = problem.answer;
        String sql = "update problems set title='" + title + "', content='" + content + "',"
                + "type='" + type + "', status='" + status + "', optionA='" + optionA + "',"
                + "optionB='" + optionB + "', optionC='" + optionC + "', optionD='" + optionD + "',"
                + "optionE='" + optionE + "', optionF='" + optionF + "', optionG='"
                + optionG + "', optionH='" + optionH + "', answer='" + answer + "'"
                + " where id = " + problemID;
        cmd.execute(sql);
    }

    Problem getProblem(int problemID) throws SQLException {
        String sql = "select * from problems where id = " + problemID;
        ResultSet rs = cmd.executeQuery(sql);
        if (rs.next()) {
            String title = rs.getString("title");
            String content = rs.getString("content");
            String status = rs.getString("status");
            String type = rs.getString("type");
            ArrayList<String> options = new ArrayList<>();
            if (!rs.getString("optionA").equals(defaultOption)) options.add(rs.getString("optionA"));
            if (!rs.getString("optionB").equals(defaultOption)) options.add(rs.getString("optionB"));
            if (!rs.getString("optionC").equals(defaultOption)) options.add(rs.getString("optionC"));
            if (!rs.getString("optionD").equals(defaultOption)) options.add(rs.getString("optionD"));
            if (!rs.getString("optionE").equals(defaultOption)) options.add(rs.getString("optionE"));
            if (!rs.getString("optionF").equals(defaultOption)) options.add(rs.getString("optionF"));
            if (!rs.getString("optionG").equals(defaultOption)) options.add(rs.getString("optionG"));
            if (!rs.getString("optionH").equals(defaultOption)) options.add(rs.getString("optionH"));
            String answer = rs.getString("answer");
            return new Problem(problemID, title, content, type, status, options, answer);
        }
        return null;
    }

    int staticPaperID = 0;

    private int getPaperID() throws SQLException {
        String sql = "select max(id) maxID from papers";
        ResultSet rs = cmd.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt("maxID");
        }
        return 0;
    }

    int addPaper(@NotNull Paper paper) throws SQLException {
        int id = staticPaperID;
        staticPaperID++;
        String title = paper.title;
        String author = String.valueOf(paper.author.id);
        String status = paper.status;
        int duration = paper.duration;
        String content = paper.content;
        String sql = "insert into papers (id, title, status, author, duration, content) value ("
                + id + ",'" + title + "','" + status + "','" + author + "','" + duration + "','" +
                content + "')";
        cmd.execute(sql);
        return id;
    }

    ArrayList<Paper> queryPapersList() throws SQLException {
        ArrayList<Paper> papers = new ArrayList<>();
        String sql = "select * from papers";
        ResultSet rs = cmd.executeQuery(sql);
        ArrayList<Integer> authorIDs = new ArrayList<>();
        ArrayList<String> problems = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            int authorID = Integer.parseInt(rs.getString("author"));
            authorIDs.add(authorID);
            Date date = rs.getDate("date");
            Time time = rs.getTime("time");
            String status = rs.getString("status");
            problems.add(rs.getString("problemList"));
            int duration = rs.getInt("duration");
            String content = rs.getString("content");
            ArrayList<Integer> scoresList = Paper.scoresFromString(rs.getString("scoreList"));
            papers.add(new Paper(id, title, new Manager(), date, time, status, content, new ArrayList<>(), scoresList, duration));
        }
        rs.close();
        for (int i = 0; i < authorIDs.size(); ++i) {
            User author;
            if (authorIDs.get(i) == 0) author = new Manager();
            else author = Teacher.getTeacher(authorIDs.get(i));
            papers.get(i).author = author;
            papers.get(i).problemsList = Paper.stringToProblemsList(problems.get(i));
        }
        return papers;
    }

    void clearPaperList() throws SQLException {
        String sql = "delete from papers";
        cmd.execute(sql);
    }

    void updatePaper(@NotNull Paper paper) throws SQLException {
        int id = paper.id;
        String title = paper.title;
        String author = String.valueOf(paper.author.id);
        Date date = paper.date;
        Time time = paper.time;
        String status = paper.status;
        String problemsString = Paper.problemtoString(paper.problemsList);
        int duration = paper.duration;
        String scoresListString = Paper.scoresToString(paper.scoreList);
        String content = paper.content;
        String sql = "update papers set title = '" + title + "',author = '" + author +
                "',date = '" + date + "',time = '" + time + "',status = '" + status +
                "',problemList = '" + problemsString + "',duration = '" + duration +
                "',scoreList = '" + scoresListString + "',content = '" + content +
                "' where id = " + id;
        cmd.execute(sql);
    }

    void deletePaper(@NotNull Paper paper) throws SQLException {
        int id = paper.id;
        String sql = "delete from papers where id = " + id;
        cmd.execute(sql);
    }

    Paper getPaper(int id) throws SQLException {
        String sql = "select * from papers where id = " + id;
        ResultSet rs = cmd.executeQuery(sql);
        if (rs.next()) {
            String title = rs.getString("title");
            Date date = rs.getDate("date");
            Time time = rs.getTime("time");
            String status = rs.getString("status");
            String content = rs.getString("content");
            int duration = rs.getInt("duration");

            int authorID = Integer.parseInt(rs.getString("author"));
            String problemListString = rs.getString("problemList");
            String scoresString = rs.getString("scoreList");

            User author;
            if(authorID==0)author = new Manager();
            else author = Teacher.getTeacher(authorID);
            ArrayList<Problem> problems = Paper.stringToProblemsList(problemListString);
            ArrayList<Integer> scoresList = Paper.scoresFromString(scoresString);
            return new Paper(id, title, author, date, time, status, content, problems, scoresList, duration);
        }
        return null;
    }

    int staticTaskID = 0;

    private int getTaskID() throws SQLException {
        String sql = "select max(id) maxID from task";
        ResultSet rs = cmd.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt("maxID");
        }
        return 0;
    }

    int addTask(@NotNull Task task) throws SQLException {
        int id = staticTaskID;
        staticTaskID++;
        int paperID = task.getPaper().getId();
        int studentID = task.getStudent().getId();
        String sql = "insert into task (id, paperID,studentID) value ("
                + id + ", " + paperID + ", " + studentID + ")";
        cmd.execute(sql);
        return id;
    }

    ArrayList<Task> queryTaskList() throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "select * from task";
        ResultSet rs = cmd.executeQuery(sql);
        ArrayList<Integer> paperIDs = new ArrayList<>();
        ArrayList<Integer> studentIDs = new ArrayList<>();
        ArrayList<String> answerListStrings = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            studentIDs.add(rs.getInt("studentID"));
            paperIDs.add(rs.getInt("paperID"));
            answerListStrings.add(rs.getString("problemAnswer"));
            int grade = rs.getInt("grade");
            ArrayList<Answer> answers = new ArrayList<>();
            tasks.add(new Task(id, new Paper(), new Student(), grade, answers));
        }
        for (int i = 0; i < tasks.size(); ++i) {
            Task task = tasks.get(i);
            task.setPaper(Paper.getPaper(paperIDs.get(i)));
            task.setStudent(Student.getStudent(studentIDs.get(i)));
            task.setProblemAnswer(Task.stringToAnswerList(answerListStrings.get(i)));
        }
        return tasks;
    }

    ArrayList<Task> queryTaskByPaperID(int paperID) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "select * from task where paperID = " + paperID;
        ResultSet rs = cmd.executeQuery(sql);
        ArrayList<Integer> paperIDs = new ArrayList<>();
        ArrayList<Integer> studentIDs = new ArrayList<>();
        ArrayList<String> answerListStrings = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            studentIDs.add(rs.getInt("studentID"));
            paperIDs.add(rs.getInt("paperID"));
            answerListStrings.add(rs.getString("problemAnswer"));
            int grade = rs.getInt("grade");
            ArrayList<Answer> answers = new ArrayList<>();
            tasks.add(new Task(id, new Paper(), new Student(), grade, answers));
        }
        for (int i = 0; i < tasks.size(); ++i) {
            Task task = tasks.get(i);
            task.setPaper(Paper.getPaper(paperIDs.get(i)));
            task.setStudent(Student.getStudent(studentIDs.get(i)));
            task.setProblemAnswer(Task.stringToAnswerList(answerListStrings.get(i)));
        }
        return tasks;
    }

    void clearTaskList() throws SQLException {
        String sql = "delete from task";
        cmd.execute(sql);
    }

    void updateTask(@NotNull Task task) throws SQLException {
        int id = task.id;
        int paperID = task.getPaper().getId();
        int studentID = task.getStudent().getId();
        int grade = task.grade;
        String problemAnswerString = Task.answerListToString(task.getProblemAnswer());
        String sql = "update task set paperID = " + paperID + ", studentID = " + studentID +
                ", grade = " + grade + ", problemAnswer = '" + problemAnswerString + "'" +
                " where id = " + id;
        cmd.execute(sql);
    }

    void deleteTask(@NotNull Task task) throws SQLException {
        int id = task.id;
        String sql = "delete from task where id = " + id;
        cmd.execute(sql);
    }

    Task getTask(int id) throws SQLException {
        String sql = "select id from task where id = " + id;
        ResultSet rs = cmd.executeQuery(sql);
        int paperID, studentID;
        String answerListString;
        Task task;
        if (rs.next()) {
            studentID = rs.getInt("studentID");
            paperID = rs.getInt("paperID");
            answerListString = rs.getString("problemAnswer");
            int grade = rs.getInt("grade");
            ArrayList<Answer> answers = new ArrayList<>();
            task = new Task(id, new Paper(), new Student(), grade, answers);
        }
        else return null;
        task.setPaper(Paper.getPaper(paperID));
        task.setStudent(Student.getStudent(studentID));
        task.setProblemAnswer(Task.stringToAnswerList(answerListString));
        return task;
    }

    ArrayList<Task> getTaskByStudentID(int StudentID) throws SQLException {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "select * from task where studentID = " + StudentID;
        ResultSet rs = cmd.executeQuery(sql);
        ArrayList<Integer> paperIDs = new ArrayList<>();
        ArrayList<Integer> studentIDs = new ArrayList<>();
        ArrayList<String> answerListStrings = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            studentIDs.add(rs.getInt("studentID"));
            paperIDs.add(rs.getInt("paperID"));
            answerListStrings.add(rs.getString("problemAnswer"));
            int grade = rs.getInt("grade");
            ArrayList<Answer> answers = new ArrayList<>();
            tasks.add(new Task(id, new Paper(), new Student(), grade, answers));
        }
        for (int i = 0; i < tasks.size(); ++i) {
            Task task = tasks.get(i);
            task.setPaper(Paper.getPaper(paperIDs.get(i)));
            task.setStudent(Student.getStudent(studentIDs.get(i)));
            task.setProblemAnswer(Task.stringToAnswerList(answerListStrings.get(i)));
        }
        return tasks;
    }

    int staticAnswerID;

    private int getAnswerID() throws SQLException {
        String sql = "select max(id) maxID from answer";
        ResultSet rs = cmd.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt("maxID");
        }
        return 0;
    }

    int addAnswer(Answer answer) throws SQLException {
        int id = staticAnswerID;
        staticAnswerID++;
        int paperID = answer.paperID;
        int problemID = answer.problemID;
        int studentID = answer.studentID;
        String content = answer.content;
        String stdAnswer = answer.stdAnswer;
        String sql = "insert into answer (id, paperID, problemID, studentID, content, stdAnswer) values ('" +
                id + "','" + paperID + "','" + problemID + "','" +
                studentID + "','" + content + "','" + stdAnswer + "')";
        cmd.execute(sql);
        return id;
    }

    ArrayList<Answer> queryAnswerList() throws SQLException {
        ArrayList<Answer> answers = new ArrayList<>();
        String sql = "select * from answer";
        ResultSet rs = cmd.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            int paperID = rs.getInt("paperID");
            int problemID = rs.getInt("problemID");
            int studentID = rs.getInt("studentID");
            String content = rs.getString("content");
            String stdAnswer = rs.getString("stdAnswer");
            answers.add(new Answer(id, paperID, problemID, studentID, content, stdAnswer));
        }
        return answers;
    }

    void clearAnswerList() throws SQLException {
        String sql = "delete from answer";
        cmd.execute(sql);
    }

    void updateAnswer(Answer answer) throws SQLException {
        int id = answer.id;
        int paperID = answer.paperID;
        int problemID = answer.problemID;
        int studentID = answer.studentID;
        String content = answer.content;
        String stdAnswer = answer.stdAnswer;
        String sql = "update answer set paperID = '" + paperID + "', problemID = '" + problemID +
                "', studentID = '" + studentID + "', content = '" + content +
                "', stdAnswer = '" + stdAnswer + "' where id = '" + id + "')";
        cmd.execute(sql);
    }

    void deleteAnswer(Answer answer) throws SQLException {
        int id = answer.id;
        String sql = "delete from answer where id = " + id;
        cmd.execute(sql);
    }

    Answer getAnswer(int id) throws SQLException {
        String sql = "select * from answer where id = " + id;
        ResultSet rs = cmd.executeQuery(sql);
        if (rs.next()) {
            int paperID = rs.getInt("paperID");
            int problemID = rs.getInt("problemID");
            int studentID = rs.getInt("studentID");
            String content = rs.getString("content");
            String stdAnswer = rs.getString("stdAnswer");
            return new Answer(id, paperID, problemID, studentID, content, stdAnswer);
        }
        return null;
    }
}

