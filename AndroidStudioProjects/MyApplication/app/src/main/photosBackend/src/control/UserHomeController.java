package control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserHomeController {
    Stage primaryStage;
    @FXML
    TextField userName;
    private ObservableList displayList;

    @FXML
    TableColumn albumName;
    @FXML
    TableColumn numberOfPhotos;
    @FXML
    TableColumn dateRange;

    @FXML
    TableView tableView;

    User currentUser;

    public void startController(Stage mainStage, User user)
    {
        currentUser = Serialization.deserialize(user.getUserName());
        primaryStage = mainStage;

         albumName.setCellValueFactory(new PropertyValueFactory("albumName"));
        numberOfPhotos.setCellValueFactory(new PropertyValueFactory("numberOfPhotos"));
        dateRange.setCellValueFactory(new PropertyValueFactory("dateRange"));
        showList(0);
    }

    public void createAlbum(ActionEvent event) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/CreateAlbum.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            CreateAlbumController controller = loader.getController();
            Stage secondaryStage = new Stage();

            controller.startController(secondaryStage,currentUser);
            Scene scene = new Scene(root);

            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Creating Album");
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(primaryStage);
            secondaryStage.showAndWait();
            currentUser = Serialization.deserialize(currentUser.getUserName());
            showList(0);
        }catch (Exception e){

                e.printStackTrace();

        }

    }


    public void renameAlbum(ActionEvent event){

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/RenameAlbum.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            RenameAlbumController controller = loader.getController();
            Stage secondaryStage = new Stage();
            AlbumListElement temp = (AlbumListElement) tableView.getSelectionModel().getSelectedItem();
            String oldAlbumName =temp.getAlbumName();
            controller.startController(secondaryStage,currentUser,oldAlbumName);
            Scene scene = new Scene(root);

            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Renaming Album");
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(primaryStage);
            secondaryStage.showAndWait();
            currentUser = Serialization.deserialize(currentUser.getUserName());
            showList(0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAlbum(ActionEvent event){
        AlbumListElement targetName = (AlbumListElement) tableView.getSelectionModel().getSelectedItem();
        //System.out.println(("Delete ALbum "+  targetName.getAlbumName()));
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.initOwner(primaryStage);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Do you want to delete selected album?");
        Optional<ButtonType> result = confirm.showAndWait();
        if(!result.isPresent()){
            return;
        }
        if(result.get() == ButtonType.OK){
            currentUser.deleteAlbum(targetName.getAlbumName());
            showList(0);
        }
    }


    public void showAlbum(ActionEvent event){
        AlbumListElement temp = (AlbumListElement) tableView.getSelectionModel().getSelectedItem();
        if(temp != null){
           String openThisAlbum = temp.getAlbumName();
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/ShowAlbum.fxml"));
                AnchorPane root = (AnchorPane) loader.load();
                Stage secondaryStage = new Stage();
                primaryStage.close();
                ShowAlbumController controller = loader.getController();

                controller.startController(secondaryStage,currentUser,openThisAlbum);
                Scene scene = new Scene(root);
                secondaryStage.setScene(scene);
                secondaryStage.setTitle("Showing Album");
                secondaryStage.setResizable(false);
                secondaryStage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }


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



    public void showList(int selectIndex){
        //displayList = parsingTxt();
        //tableView.setItems(displayList);
        List list = new ArrayList();
        for(Album s: currentUser.getAlbumList()){
            String albumName = s.albumName;
            int numberOfPhoto = s.numberOfPhoto();

            String dateRange = s.getDateRange();
            AlbumListElement temp = new AlbumListElement(albumName,String.valueOf(numberOfPhoto),dateRange);
            list.add(temp);

        }
        ObservableList completeList = FXCollections.observableList(list);
        tableView.setItems(completeList);

        tableView.getSelectionModel().select(selectIndex);
    }


    public  void quit (ActionEvent e){
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.initOwner(primaryStage);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Do you want to quit?");
        Optional<ButtonType> result = confirm.showAndWait();
        if(!result.isPresent()){
            Serialization.serialize(currentUser);
            primaryStage.close();
        }
        if(result.get() == ButtonType.OK){
            Serialization.serialize(currentUser);
            primaryStage.close();
        }
    }

    public void search(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/SearchResult.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage secondaryStage = new Stage();
            primaryStage.close();
            SearchController controller = loader.getController();
            controller.startController(secondaryStage,currentUser);
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


}
