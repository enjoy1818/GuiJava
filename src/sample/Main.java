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
    private ArrayList<Student> studentArrayList = new ArrayList<>();
    @Override
    public void start(Stage Stages) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        primaryStage = Stages;
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 780, 600));
        primaryStage.show();
    }
    @FXML
    private Button importExam, validateButton, exportButton, removeFileButton, validateAllButton, importStudent;
    @FXML
    private ListView<String> studentFileList, examFileList;
    @FXML
    private void eventHandler(ActionEvent evnt){
        if(evnt.getSource().equals(importExam)){
//            Import file for path only

            fileChooser.setTitle("Import exam images");
//            add
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All images", "*.*"),
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            fileListArray.addAll(fileChooser.showOpenMultipleDialog(primaryStage));
            if(fileListArray != null) {
                for(File file : fileListArray){
                    studentFileList.getItems().add(file.getName()+" : "+file.getAbsolutePath());
                }
                removeFileButton.setDisable(false);
            }
            System.out.println("Imported");
            System.out.println(fileListArray);
        }else if(evnt.getSource().equals(removeFileButton)){
            int selectedItem = studentFileList.getSelectionModel().getSelectedIndex();
            if(!studentFileList.getItems().isEmpty()){
                fileListArray.remove(selectedItem);
                studentFileList.getItems().remove(selectedItem);
                System.out.println(fileListArray);
                studentFileList.refresh();
                System.out.println("File removed");
                if(studentFileList.getItems().isEmpty()){
                    removeFileButton.setDisable(true);
                    fileListArray.clear();
                }
            }
        }
        else if(evnt.getSource().equals(validateButton)){
            /* TODO validating an image file*/


        }else if(evnt.getSource().equals(exportButton)){
            String sampleStudent = "EX01, Math, 60070000, Jack, 80";
            fileSaver.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("csv", "*.csv"),
                    new FileChooser.ExtensionFilter("txt", "*.txt")
                    );
            File saveFilePath = fileSaver.showSaveDialog(primaryStage); // get location of file
            if(saveFilePath != null){
                try {
                    PrintWriter writer;
                    writer = new PrintWriter(saveFilePath); //Write file with location of file
                    writer.println("Exam sheet number, Exam sheet name, Student ID, Student Name, Score"); //Write csv head row, seperated with comma
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
