package control;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.ArrayList;

import java.util.List;

import java.io.IOException;
import java.io.*;
import java.util.*;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import model.Serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;

public class LoginController {
    Stage primaryStage;
    @FXML TextField userName;

    public void startController(Stage mainStage) {
        primaryStage = mainStage;
    }

    public void login(ActionEvent event) throws IOException{
       String input = userName.getText();
       if(input.equals("admin")){
            loadAdmin();
        }else{
           if(matchUser(input)){
                loadUser(input);
           }else{
               // User not found
               Alert confirm = new Alert(AlertType.WARNING);
               confirm.initOwner(primaryStage);
               confirm.setTitle("Warning");
               confirm.setHeaderText("User not found");
               confirm.setContentText("Please input the registered username.");
               Optional<ButtonType> result = confirm.showAndWait();
               if(!result.isPresent()){
                   return;
               }
           }
       }
    }


    public  void quit (ActionEvent e){
        primaryStage.close();
    }

    public void  loadUser(String username){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UserHome.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage secondaryStage = new Stage();
            primaryStage.close();
            UserHomeController controller = loader.getController();
            controller.startController(secondaryStage, Serialization.deserialize(username));
            Scene scene = new Scene(root);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("User Homepage");
            secondaryStage.setResizable(false);
            secondaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAdmin(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/admin.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage secondaryStage = new Stage();

            primaryStage.close();

            AdminController controller = loader.getController();
            controller.startController(secondaryStage);
            Scene scene = new Scene(root);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Admin Page");
            secondaryStage.setResizable(false);
            secondaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean matchUser( String userName) throws IOException{
        File myObj = new File("data/userlist.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if(!data.isEmpty()){
                if (data.equals(userName)){
                    myReader.close();
                    return true;
                }
            }
        }
        myReader.close();
        return false;
    }
}
