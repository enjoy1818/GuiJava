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
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Controller(){}
    private Scene testScene;
    private Stage testStage;
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
            db.addExam("000A", "test01", "AAAA");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            db.closeConnection();
        }else if(event.getSource().equals())
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
