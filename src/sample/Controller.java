package sample;

import javafx.fxml.*;
import javafx.event.*;
import javafx.scene.control.Button;

public class Controller{
    public Controller(){}
    @FXML
    private Button importButton, validateButton, exportButton;
    @FXML
    private void importHandler(ActionEvent evnt){
        if(evnt.getSource().equals(this.importButton)){
            System.out.println("Yay");
        }
    }
    @FXML
    private void eventHandler(ActionEvent evnt){

    }
    @FXML
    private void initialize(){}

}
