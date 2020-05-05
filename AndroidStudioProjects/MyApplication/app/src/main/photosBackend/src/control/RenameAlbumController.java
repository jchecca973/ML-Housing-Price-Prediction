package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.*;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import java.awt.*;

public class RenameAlbumController {
    Stage primaryStage;
    User currentUser;
    @FXML Text oldAlbumName;
    @FXML
    TextField newAlbumName;
    public void startController(Stage mainStage, User currentUser, String oldAlbumName) {
        this.oldAlbumName.setText(oldAlbumName);
        this.currentUser = currentUser;
        primaryStage = mainStage;
    }

    public void rename(ActionEvent event){
        for(Album s : currentUser.getAlbumList()){
            if(s.albumName.equals(oldAlbumName.getText())){
                s.albumName = newAlbumName.getText();
            }
        }
        Serialization.serialize(currentUser);
        primaryStage.close();
    }

    public void cancel(ActionEvent event){
        primaryStage.close();
    }
}
