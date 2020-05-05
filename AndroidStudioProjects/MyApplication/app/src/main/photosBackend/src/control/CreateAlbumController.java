package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.*;

import javafx.scene.control.TextField;
import java.util.Optional;

public class CreateAlbumController {
    Stage primaryStage;
    User currentUser;
@FXML TextField albumName;
    public void startController(Stage mainStage,User currentUser) {
        this.currentUser = currentUser; primaryStage = mainStage;
    }

    public void create(ActionEvent event){
        String newAlbumName = albumName.getText();
        if(currentUser.isInAlbumList(newAlbumName)){
            Alert confirm = new Alert(Alert.AlertType.WARNING);
            confirm.initOwner(primaryStage);
            confirm.setTitle("Warning");
            confirm.setHeaderText("Duplicated Album Name");
            confirm.setContentText("Please input a new album name.");
            Optional<ButtonType> result = confirm.showAndWait();
            if(!result.isPresent()){
                return;
            }

        }else{
            Album newAlbum = new Album(newAlbumName);
            currentUser.addAlbum(newAlbum);
            Serialization.serialize(currentUser);
            Alert confirm = new Alert(Alert.AlertType.INFORMATION);
            confirm.initOwner(primaryStage);
            confirm.setTitle("Success");
            confirm.setHeaderText("Successfully added album.");
            confirm.showAndWait();
            primaryStage.close();
        }
    }

    public void cancel(ActionEvent event){
        primaryStage.close();
    }
}
