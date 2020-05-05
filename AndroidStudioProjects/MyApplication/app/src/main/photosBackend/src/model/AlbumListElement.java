package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class AlbumListElement{
    private SimpleStringProperty albumName;
    private SimpleStringProperty numberOfPhotos;
    private SimpleStringProperty dateRange;


    public AlbumListElement(String albumName, String numberOfPhotos, String dateRange){
        this.albumName = new SimpleStringProperty(albumName);
        this.numberOfPhotos = new SimpleStringProperty(numberOfPhotos);
        this.dateRange = new SimpleStringProperty(dateRange);
    }


    public String getAlbumName() {
        return albumName.get();
    }

    public String getDateRange() {
        return dateRange.get();
    }

    public String getNumberOfPhotos() {
        return numberOfPhotos.get();
    }
}