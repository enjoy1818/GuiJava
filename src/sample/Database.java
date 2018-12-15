package sample;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private Connection connect = null;
    public Database(){

    }
    public void connect(String userName, String password, String table){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+table, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.connect = connection;
    }

    public Connection getConnect() {
        return connect;
    }
    public ArrayList<Exam> getAllExam(){
        ArrayList<Exam> examList = new ArrayList<>();
        try {
            Statement statement = connect.createStatement();
            String comm = "select * from examtable";
            ResultSet result = statement.executeQuery(comm);
            while(result.next()){
                Exam temp = new Exam(result.getString(1), result.getString(2), result.getString(3), result.getString(4));
                examList.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examList;
    }
    public boolean addExam(String examNumber, String examName, String examAnswer){
        try {
            Statement check = connect.createStatement();
            ResultSet resultSet = check.executeQuery("select ExamName from examtable where ExamNumber = '" + examNumber+"'");
            if(!resultSet.isBeforeFirst()){
                PreparedStatement preparedStatement = connect.prepareStatement("insert into examtable (ExamNumber, ExamName, ExamAnswer) values(?,?,?)");
                preparedStatement.setString(1, examNumber);
                preparedStatement.setString(2, examName);
                preparedStatement.setString(3, examAnswer);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void closeConnection(){
        try {
            this.connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeExam(String examNumber){
        try {
            Statement stmt = connect.createStatement();
            String comm = "delete from examtable where examNumber = '"+examNumber+"'";
            stmt.execute(comm);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void checkConnection(){
        if(connect == null){
            System.out.println("Not Connected");
        }
        else {
            System.out.println("Connected");
        }
    }

    public boolean addStudent(){
       return false;
    }
    public ArrayList<Student> getAllStudent(){
        ArrayList<Student> students = new ArrayList<>();
        try {
            Statement stmt = connect.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from studenttable");
            while(resultSet.next()){
                Student temp = new Student();
                temp.setName(resultSet.getString("StudentName"));
                temp.setStudentID(resultSet.getString("StudentID"));
                students.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    public void removeStudent(String StudentID){
        try {
            Statement stmt = connect.createStatement();
            stmt.execute("delete from studenttable where StudentID = '"+StudentID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean addStudent(String StudentID, String StudentName){
        try {
            Statement check = connect.createStatement();
            ResultSet resultSet = check.executeQuery("select StudentID from studenttable where StudentID = '" + StudentID+"'");
            if(!resultSet.isBeforeFirst()){
                PreparedStatement preparedStatement = connect.prepareStatement("insert into studenttable (StudentID, StudentName) values(?,?)");
                preparedStatement.setString(1, StudentID);
                preparedStatement.setString(2, StudentName);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void testColumn(){
        try {
            Statement stmt = connect.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from examtable");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            System.out.println(resultSetMetaData.getColumnName(1));
            System.out.println(resultSetMetaData.getColumnName(2));
            System.out.println(resultSetMetaData.getColumnName(3));
            System.out.println(resultSetMetaData.getColumnName(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getExamColumn(){
        ArrayList<String> examCol = new ArrayList<>();
        try {
            Statement stmt = connect.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from examtable");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            for(int i = 1;i <= resultSetMetaData.getColumnCount(); i++){
                examCol.add(resultSetMetaData.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examCol;
    }
    public ArrayList<String> getValidated(){
        ArrayList<String> temp = new ArrayList<>();
        try{
            Statement stmt = connect.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from validatedtable");
            while(resultSet.next()){
                temp.add(resultSet.getString("ExamName")+" "+resultSet.getString("ExamNumber")+" "
                +resultSet.getString("StudentID")+" "+resultSet.getString("StudentName")+
                        " "+resultSet.getInt("Score"));
            }
        }catch(Exception ex){

        }
        return temp;
    }
    public boolean addValidated(int score, String StudentID,String StudentName, String ExamName, String ExamNumber){
        try {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from validatedtable where StudentID='"+StudentID+"' and ExamName='"+ExamName+"' and ExamNumber='"+ExamNumber+"'");
            if(!resultSet.isBeforeFirst()){
                PreparedStatement preparedStatement = connect.prepareStatement("insert into validatedtable (ExamName, ExamNumber, StudentID, StudentName, Score) " +
                        "values(?, ?, ?, ?, ?)");
                preparedStatement.setString(1, ExamName);
                preparedStatement.setString(2, ExamNumber);
                preparedStatement.setString(3, StudentID);
                preparedStatement.setString(4, StudentName);
                preparedStatement.setInt(5, score);
                preparedStatement.executeUpdate();
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
