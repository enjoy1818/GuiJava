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
        db.closeConnection();
    }
    private Scene testScene;
    private Stage testStage;
    private String dbUname = "root";
    private String dbPassword = "1234";
    private String dbSchema = "test";
    private ArrayList<Exam> examArrayList;
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
        Database db = new Database();
        db.Connect("root", "1234", "test");
        examArrayList = db.getAllExam();
        db.closeConnection();
        for(Exam exam:examArrayList){
            examList.getItems().add(exam.getExamNumber()+" "+exam.getExamName()+" "+exam.getExamSolution());
        }
        examList.refresh();
    }
    @FXML
    private JFXButton importExam, confirmImportExam, removeExam;
    @FXML
    private JFXTextField examName, examNumber, examAnswer;
    @FXML
    private JFXListView<String> examList;
    @FXML
    private void eventHandler(ActionEvent event){
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
            examAnswer.setText("");
            examNumber.setText("");
            examName.setText("");
        }else if(event.getSource().equals(removeExam)){
            int removeIndex = examList.getSelectionModel().getSelectedIndex();
            Database db = new Database();
            db.Connect(dbUname, dbPassword, dbSchema);
            db.removeExam(examArrayList.get(removeIndex).getExamNumber());
            examList.getItems().remove(removeIndex);
            examArrayList.remove(removeIndex);
            examList.refresh();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
