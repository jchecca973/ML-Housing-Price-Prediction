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

import model.*;
import java.util.List;

import java.io.IOException;
import java.io.*;
import java.util.*;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;

public class SearchController {
    Stage primaryStage;
    User currentUser;

    public void startController(Stage mainStage, User username) throws IOException{
        this.currentUser = username;
        primaryStage = mainStage;
    }

    public void toHomepage(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UserHome.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage secondaryStage = new Stage();

            primaryStage.close();

            UserHomeController controller = loader.getController();
            controller.startController(secondaryStage,currentUser);
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

    public void logout (ActionEvent event){

        Serialization.serialize(currentUser);
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
        Serialization.serialize(currentUser);
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
