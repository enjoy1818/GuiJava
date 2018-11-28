package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Controller(){
        Database db = new Database();
        db.Connect("root", "1234", "test");
        examArrayList = db.getAllExam();
        studentArrayList = db.getAllStudent();
        db.closeConnection();
    }
    private Scene testScene;
    private Stage testStage;
    private String dbUname = "root";
    private String dbPassword = "1234";
    private String dbSchema = "test";
    private ArrayList<Exam> examArrayList;
    private ArrayList<Student> studentArrayList;
    @FXML
    private void changeScene(String url, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(url));
        Parent root = loader.load();
        testScene = new Scene(root);
        testStage = new Stage();
        testStage.setTitle(title);
        testStage.setScene(testScene);
        testStage.show();
    }
    private void refreshList(){
        examList.getItems().clear();
        studentList.getItems().clear();
        Database db = new Database();
        db.Connect(dbUname, dbPassword, dbSchema);
        examArrayList = db.getAllExam();
        studentArrayList = db.getAllStudent();
        db.closeConnection();
        for(Exam exam : examArrayList){
            examList.getItems().add(exam.getExamName());
        }
        for(Student student : studentArrayList){
            studentList.getItems().add(student.getName()+" "+student.getStudentID());
        }
    }
    @FXML
    private JFXButton importExam, confirmImportExam, removeExam, validateButton, refresh, removeStudent, confirmImportStudent, importStudent;
    @FXML
    private JFXTextField examName, examNumber, examAnswer, studentName, studentID;
    @FXML
    private JFXListView<String> examList, studentList;
    @FXML
    private void eventHandler(ActionEvent event){
        if(event.getSource().equals(refresh)){
            refreshList();
        }
        if(event.getSource().equals(validateButton)){
            System.out.println(studentList.getSelectionModel().getSelectedItems());
            System.out.println(examList.getSelectionModel().getSelectedItems());
        }
    }
    @FXML
    private void importExamHandler(ActionEvent event){
        if(event.getSource().equals(importExam)){
            try {
                changeScene("ImportExamDialog.fxml", "Import Exam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(event.getSource().equals(confirmImportExam)){
            Database db = new Database();
            db.Connect("root", "1234", "test");
            boolean test = db.addExam(examNumber.getText(), examName.getText(), examAnswer.getText());
            if(test == true){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Import Status");
                alert.setHeaderText("Import Success!!!!");
                alert.setContentText(null);
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Import Status");
                alert.setHeaderText("Import Failed!!!!");
                alert.setContentText(null);
                alert.showAndWait();
            }
            Stage stage = (Stage) confirmImportExam.getScene().getWindow();
            stage.close();
            db.closeConnection();

        }else if(event.getSource().equals(removeExam)){
            int removeIndex = examList.getSelectionModel().getSelectedIndex();
            Database db = new Database();
            db.Connect(dbUname, dbPassword, dbSchema);
            db.removeExam(examArrayList.get(removeIndex).getExamNumber());
            examList.getItems().remove(removeIndex);
            examArrayList.remove(removeIndex);
            examList.refresh();
            db.closeConnection();
        }
    }
    @FXML
    private void importStudentHandler(ActionEvent event){
        if(event.getSource().equals(removeStudent)){
            int removeIndex = studentList.getSelectionModel().getSelectedIndex();
            Database db = new Database();
            db.Connect(dbUname, dbPassword, dbSchema);
            db.removeStudent(studentArrayList.get(removeIndex).getStudentID());
            studentArrayList.remove(removeIndex);
            studentList.getItems().remove(removeIndex);
            studentList.refresh();
            db.closeConnection();
        }else if(event.getSource().equals(importStudent)){
            try {
                changeScene("ImportStudentDialog.fxml", "Import Student");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(event.getSource().equals(confirmImportStudent)){
            Database db = new Database();
            db.Connect(dbUname, dbPassword, dbSchema);
            boolean test = db.addStudent(studentID.getText(), studentName.getText());
            if(test){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Import Status");
                alert.setHeaderText("Import Success!!!!");
                alert.setContentText(null);
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Import Status");
                alert.setHeaderText("Import Failed!!!!");
                alert.setContentText(null);
                alert.showAndWait();
            }
            Stage stage = (Stage) confirmImportStudent.getScene().getWindow();
            stage.close();
            db.closeConnection();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
