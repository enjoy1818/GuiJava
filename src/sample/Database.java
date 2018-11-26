package sample;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    Connection connect = null;
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
        public ArrayList getAllExam(){
        ArrayList examList = new ArrayList();
        try {
            Statement statement = this.connect.createStatement();
            String comm = "select * from examtable";
            ResultSet result = statement.executeQuery(comm);
            while(result.next()){
                examList.add(result.getInt(1)+" "+result.getString(2)+" "+result.getString(3)+" "+result.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examList;
    }
    public void addExam(String examNumber, String examName, String examAnswer){
        try {
            PreparedStatement preparedStatement = connect.prepareStatement("insert into examtable (ExamNumber, ExamName, ExamAnswer) values(?,?,?)");
            preparedStatement.setString(1, examNumber);
            preparedStatement.setString(2, examName);
            preparedStatement.setString(3, examAnswer);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void closeConnection(){
        try {
            this.connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
