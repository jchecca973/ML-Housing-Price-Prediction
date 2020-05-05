package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Serialization;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;



public class AdminController {
    Stage primaryStage;

    @FXML TextField  addingUsername;
    @FXML TextField  deleteUsername;
    @FXML
    ListView<String> listView;

    private ObservableList<String> obsList;

    public void startController(Stage mainStage) throws IOException{
        obsList = FXCollections.observableArrayList();
        File myObj = new File("data/userlist.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if(!data.isEmpty()) {
                obsList.add(data);
            }
            }

        listView.setItems(obsList);
        // select the first item
        listView.getSelectionModel().select(0);


        primaryStage = mainStage;
    }

    public void addUser(ActionEvent event) throws IOException{
        File myObj = new File("data/userlist.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if(!data.isEmpty()){
                if (data.equals(addingUsername.getText())){
                    Alert confirm = new Alert(Alert.AlertType.WARNING);
                    confirm.initOwner(primaryStage);
                    confirm.setTitle("Warning");
                    confirm.setHeaderText("User already exist");
                    Optional<ButtonType> result = confirm.showAndWait();
                    if(!result.isPresent()){
                        return;
                    }
                    return;
                }
            }
        }

        FileWriter myWriter = new FileWriter(myObj,true);
        BufferedWriter br = new BufferedWriter(myWriter);
        br.write(addingUsername.getText());
        br.newLine();
        br.flush();
        br.close();
        myReader.close();
        myWriter.close();
        Alert confirm = new Alert(Alert.AlertType.INFORMATION);
        confirm.initOwner(primaryStage);
        confirm.setTitle("Success");
        confirm.setHeaderText("Successfully added user.");
        confirm.showAndWait();
        showList();

    }

    public void showList() throws IOException{
        obsList = FXCollections.observableArrayList();
        File myObj = new File("data/userlist.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if(!data.isEmpty()) {
                obsList.add(data);
            }
        }

        listView.setItems(obsList);
        // select the first item
        listView.getSelectionModel().select(0);
    }

    public void deleteUser(ActionEvent event) throws IOException{
        File myObj = new File("data/userlist.txt");
        Scanner myReader = new Scanner(myObj);
        ArrayList<String> temp = new ArrayList<String>();

        boolean found = false;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (!data.isEmpty()) {
                if(!data.equals(deleteUsername.getText())){
                    temp.add(data);
                }else{
                    found = true;
                }
            }
        }


        if(!found){
            Alert confirm = new Alert(Alert.AlertType.INFORMATION);
            confirm.initOwner(primaryStage);
            confirm.setTitle("Deletion Failed");
            confirm.setHeaderText("User not found.");
            confirm.showAndWait();
            return;
        }

        FileWriter myWriter = new FileWriter(myObj);
        BufferedWriter br = new BufferedWriter(myWriter);
        int checkEOF = 0;
        for(String s : temp){
            br.write(s);
            if( checkEOF+1 < temp.size()){
                br.newLine();
            }
            checkEOF++;
        }
        br.flush();
        br.close();
        myWriter.close();
        myReader.close();

        Serialization.deleteUserData(deleteUsername.getText());
        Alert confirm = new Alert(Alert.AlertType.ERROR);
        confirm.initOwner(primaryStage);
        confirm.setTitle("Deletion Success");
        confirm.setHeaderText("Deletion Success");
        confirm.showAndWait();
        showList();
    }

    public  void logout (ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/login.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage secondaryStage = new Stage();
            primaryStage.close();
            LoginController controller = loader.getController();
            controller.startController(secondaryStage);
            Scene scene = new Scene(root);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Photo Library");
            secondaryStage.setResizable(false);
            secondaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void quit (ActionEvent e){
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.initOwner(primaryStage);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Do you want to quit?");
        Optional<ButtonType> result = confirm.showAndWait();
        if(!result.isPresent()){
            primaryStage.close();
        }
        if(result.get() == ButtonType.OK){
            primaryStage.close();
        } }

}
