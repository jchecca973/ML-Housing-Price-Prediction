package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;
import model.*;
import java.io.*;
import javafx.scene.control.ListCell;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


import javafx.scene.image.ImageView;
import java.io.IOException;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowAlbumController {
    Stage primaryStage;
    User currentUser;
    Album currentAlbum;
    @FXML
    ImageView display;
    @FXML
    ListView listView;

    public void startController(Stage mainStage, User username, String openThisAlbum) {
        this.currentUser = username;
        this.currentAlbum = currentUser.getAlbum(openThisAlbum);
        primaryStage = mainStage;
    }


    public void importPhoto (ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        Photo temp = new Photo(selectedFile.getPath());
        currentAlbum.addPhoto(temp);
        Image img = new Image (temp.getFile().toURI().toString());
        display.setImage(img);
        showList();
    }
    public void showList (){
        List list = new ArrayList();
        for(Photo p: currentAlbum.contents){
            Image img = new Image (p.getFile().toURI().toString());
            list.add(img);
        }

        ObservableList completeList = FXCollections.observableList(list);



    }
    public  void logout (ActionEvent event){
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
        }
    }
    public void toHomepage(ActionEvent event){
        Serialization.serialize(currentUser);
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

}
