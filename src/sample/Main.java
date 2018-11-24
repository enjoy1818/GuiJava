package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.io.File;
import java.util.ArrayList;

public class Main extends Application {
    private Stage primaryStage;
    private final FileChooser fileChooser = new FileChooser();
    private final FileChooser fileSaver = new FileChooser();
    private ArrayList<File> fileListArray = new ArrayList<>();
    @Override
    public void start(Stage Stages) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        primaryStage = Stages;
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
    @FXML
    private Button importButton, validateButton, exportButton, removeFileButton, validateAllButton;
    @FXML
    private ListView<String> fileList;
    @FXML
    private void eventHandler(ActionEvent evnt){
        if(evnt.getSource().equals(importButton)){
//            Import file for path only
            fileChooser.setTitle("Import exam images");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All images", "*.*"),
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            fileListArray.addAll(fileChooser.showOpenMultipleDialog(primaryStage));
            if(fileListArray != null) {
                for(File file : fileListArray){
                    fileList.getItems().add(file.getName()+" : "+file.getAbsolutePath());
                }
                removeFileButton.setDisable(false);
            }
            System.out.println("Imported");
            System.out.println(fileListArray);
        }else if(evnt.getSource().equals(removeFileButton)){
            int selectedItem = fileList.getSelectionModel().getSelectedIndex();
            if(!fileList.getItems().isEmpty()){
                fileListArray.remove(selectedItem);
                fileList.getItems().remove(selectedItem);
                System.out.println(fileListArray);
                fileList.refresh();
                System.out.println("File removed");
                if(fileList.getItems().isEmpty()){
                    removeFileButton.setDisable(true);
                    fileListArray.clear();
                }
            }
        }
        else if(evnt.getSource().equals(validateButton)){
            /* TODO validating an image file*/


        }else if(evnt.getSource().equals(exportButton)){
            String sampleStudent = "Jack Damian, 60";
            fileSaver.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("csv", "*.csv"),
                    new FileChooser.ExtensionFilter("txt", "*.txt")
                    );
            File saveFilePath = fileSaver.showSaveDialog(primaryStage); // get location of file
            if(saveFilePath != null){
                try {
                    PrintWriter writer;
                    writer = new PrintWriter(saveFilePath); //Write file with location of file
                    writer.println("STUDENT NAME, SCORE"); //Write csv head row, seperated with comma
                    writer.println(sampleStudent); //Write csv data row
                    writer.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    @FXML
    private void initialize(){}

    public static void main(String[] args) {
        launch(args);
    }
}
