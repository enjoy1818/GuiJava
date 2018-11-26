package sample;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    private Connection connect = null;
    public Database(){

    }
    public void Connect(String userName, String password, String table){
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
                Exam temp = new Exam(result.getString(2), result.getString(3), result.getString(4));
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
}
