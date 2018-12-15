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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static sample.Main.primaryStage;

public class Controller implements Initializable {
    public Controller(){
        Database db = new Database();
        db.connect(this.dbUname, this.dbPassword, this.dbSchema);
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
    private void refreshList(){ // Using to update list
        examList.getItems().clear();
        studentList.getItems().clear();
        Database db = new Database();
        db.connect(dbUname, dbPassword, dbSchema);
        examArrayList = db.getAllExam();
        studentArrayList = db.getAllStudent();
        db.closeConnection();
        for(Exam exam : examArrayList){
            examList.getItems().add(exam.getExamName()+" "+exam.getExamNumber());
        }
        for(Student student : studentArrayList){
            studentList.getItems().add(student.getName()+" "+student.getStudentID());
        }
    }
    @FXML
    private JFXButton importExam, confirmImportExam, removeExam, validateButton, refresh, removeStudent
            , confirmImportStudent, importStudent, exportExam, exportValidated;
    @FXML
    private JFXTextField examName, examNumber, examAnswer, studentName, studentID;
    @FXML
    private JFXListView<String> examList, studentList;
    @FXML
    private void eventHandler(ActionEvent event){
        if(event.getSource().equals(refresh)){
            refreshList();
        }
        else if(event.getSource().equals(validateButton)){
            int studentValidateIndex = studentList.getSelectionModel().getSelectedIndex();
            Student tempStudent = studentArrayList.get(studentValidateIndex);
            int examValidateIndex = examList.getSelectionModel().getSelectedIndex();
            Exam tempExam = examArrayList.get(examValidateIndex);
            System.out.println(tempStudent.getName()+" "+tempStudent.getStudentID());
            System.out.println(tempExam.getExamName()+" "+tempExam.getExamNumber());
            Database db = new Database();
            db.connect(this.dbUname, this.dbPassword, this.dbSchema);
            boolean test = db.addValidated(10, tempStudent.getStudentID(), tempStudent.getName(), tempExam.getExamName(), tempExam.getExamNumber());
            if(test){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Import Status");
                alert.setHeaderText("Validate Success!!!!");
                alert.setContentText(null);
                alert.showAndWait();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Import Status");
                alert.setHeaderText("Validate Failed!!!!\nDuplicate Validation.");
                alert.setContentText(null);
                alert.showAndWait();
            }
            db.closeConnection();
        }else if(event.getSource().equals(exportExam)){
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv"),
                    new FileChooser.ExtensionFilter("TEXT", "*.txt"));
            File fileSaver =fileChooser.showSaveDialog(primaryStage);
            if(fileSaver != null){
                try {
                    PrintWriter writer = new PrintWriter(fileSaver);
                    Database db = new Database();
                    db.connect(dbUname, dbPassword, dbSchema);
                    ArrayList<String> tempExamCol = db.getExamColumn();
                    ArrayList<Exam> tempExamList = db.getAllExam();
                    for(String examCol : tempExamCol){
                        writer.print(examCol+",");
                    }
                    writer.println();
                    for(Exam exam : tempExamList){
                        writer.print(exam.getExamId()+",");
                        writer.print(exam.getExamNumber()+",");
                        writer.print(exam.getExamName()+",");
                        writer.print(exam.getExamSolution());
                        writer.println();
                    }
                    writer.close();
                    db.closeConnection();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(event.getSource().equals(exportValidated)){
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv"),
                    new FileChooser.ExtensionFilter("TEXT", "*.txt"));
            File fileSaver = fileChooser.showSaveDialog(primaryStage);
            if(fileSaver != null){
                try {
                    PrintWriter writer = new PrintWriter(fileSaver);
                    Database db = new Database();
                    db.connect(dbUname, dbPassword, dbSchema);
                    ArrayList<String> tempValidatedColumn = db.getValidateColumn();
                    ArrayList<String> tempValidatedRow = db.getAllValidated();
                    for(String examCol : tempValidatedColumn){
                        writer.print(examCol+",");
                    }
                    writer.println();
                    for(String validatedItem : tempValidatedRow){
                        writer.print(validatedItem);
                        writer.println();
                    }
                    writer.close();
                    db.closeConnection();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
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
            db.connect(this.dbUname, this.dbPassword, this.dbSchema);
            boolean test = db.addExam(examNumber.getText(), examName.getText(), examAnswer.getText());
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
                alert.setHeaderText("Import Failed!!!!\nDuplicate Exam.");
                alert.setContentText(null);
                alert.showAndWait();
            }
            Stage stage = (Stage) confirmImportExam.getScene().getWindow();
            stage.close();
            db.closeConnection();

        }else if(event.getSource().equals(removeExam)){
            int removeIndex = examList.getSelectionModel().getSelectedIndex();
            Database db = new Database();
            db.connect(dbUname, dbPassword, dbSchema);
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
            db.connect(dbUname, dbPassword, dbSchema);
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
            db.connect(dbUname, dbPassword, dbSchema);
            boolean test = db.addStudent(studentID.getText(), studentName.getText()); //Check redundant information
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
                alert.setHeaderText("Import Failed!!!!\nDuplicate Student.");
                alert.setContentText(null);
                alert.showAndWait();
            }
            Stage stage = (Stage) confirmImportStudent.getScene().getWindow(); //Closing stage when complete
            stage.close();
            db.closeConnection();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
