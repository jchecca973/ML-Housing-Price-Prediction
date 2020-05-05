package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import sun.rmi.runtime.Log;
import control.LoginController;
import model.Album;
import model.Photo;
import model.User;

public class Photos extends Application {
    Stage mainStage;
    public void start(Stage primaryStage)
            throws IOException {
        try {
            mainStage = primaryStage;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/login.fxml"));
            AnchorPane root = (AnchorPane) loader.load();


            LoginController controller = loader.getController();
            controller.startController(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Photo Library");
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public HashMap<String, ArrayList<String>> convertQueryString(String query)
    {
    	HashMap<String, ArrayList<String>> tags = new HashMap<String, ArrayList<String>>();
    	ArrayList<String> values = new ArrayList<String>();
    	
    	String[] tokens = query.split("[ ]");
    	if (tokens.length == 1)
    	{
    		String[] keyValue = tokens[0].split("[=]");
    		values.add(keyValue[1]);
    		tags.put(keyValue[0], values);
    		return tags;
    	}
    	else if (tokens.length == 3)
    	{
    		String[] keyValue1 = tokens[0].split("[=]");
    		String[] keyValue2 = tokens[2].split("[=]");
    		ArrayList<String> opValue = new ArrayList<String>();
    		opValue.add(tokens[1]);
    		values.add(keyValue1[1]);
    		tags.put("$OPERATOR$", opValue);
    		if (keyValue1[0].equals(keyValue2[0]))
    		{
    			values.add(keyValue2[1]);
    			tags.put(keyValue1[0], values);
    			return tags;
    		}
    		else
    		{
    			ArrayList<String> values2 = new ArrayList<String>();
    			values2.add(keyValue2[1]);
    			tags.put(keyValue1[0], values);
    			tags.put(keyValue2[0], values2);
    			return tags;
    		}
    	}
    	else
    	{
    		return null;
    	}
    }
    
    public Album searchDates(Date a, Date b, User user) throws Exception
    {
    	if (a.compareTo(b) > 0)
    	{
    		System.out.println("Date range invalid");
    		return null;
    	}
    	
    	ArrayList<Photo> results = new ArrayList<Photo>();
    	ArrayList<Album> userAlbums = user.albumList;
    	Iterator<Album> albums = userAlbums.iterator();
    	Album currAlbum;
    	while(albums.hasNext())
    	{
    		currAlbum = albums.next();
    		Photo[] photoList = (Photo[])currAlbum.contents.toArray();
    		for (int i = 0; i < photoList.length; i++)
    		{
    			Date currDate = photoList[i].dateTaken;
	    		if ((a.compareTo(currDate) <= 0) && (b.compareTo(currDate) >= 0))
	    		{
	    			results.add(photoList[i]);
	    		}
	    		currAlbum = albums.next();
    		}
    	
    	}
    	Date date = new Date();
    	Album resultAlbum = new Album("DateRangeQuery" + date.toString());
    	resultAlbum.contents = results;
    	return resultAlbum;
    }
    
    public boolean hasAllTags(HashMap<String, ArrayList<String>> queryTags, HashMap<String, ArrayList<String>> photoTags)
    {
    	Set<String> qKeys = queryTags.keySet();
    	Set<String> pKeys = photoTags.keySet();
    	if (pKeys.containsAll(qKeys))
    	{
    		Iterator<String> iter = qKeys.iterator();
    		String key;
    		while(iter.hasNext())
    		{
    			key = iter.next();
    			if (photoTags.get(key) != queryTags.get(key))
    			{
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    public boolean hasSomeTags(HashMap<String, ArrayList<String>> queryTags, HashMap<String, ArrayList<String>> photoTags)
    {
    	Set<String> qKeys = queryTags.keySet();
    	
		Iterator<String> iter = qKeys.iterator();
		String key;
		while(iter.hasNext())
		{
			key = iter.next();
			if (photoTags.get(key) == queryTags.get(key))
			{
				return true;
			}
		}
    	
    	return false;
    }
    
    public Album searchTags(String query, User user) throws Exception
    {
    	HashMap<String, ArrayList<String>> tagList = convertQueryString(query);
    	if (tagList.size() < 1)
    	{
    		System.out.println("Empty tag list");
    		return null;
    	}
    	char type = ' ';
    	if (tagList.get("$OPERATOR$") == null)
    	{
    		type = 'n';
    	}
    	else
    	{
    		if (tagList.get("$OPERATOR$").contains("OR"))
    		{
    			type = 'o';
    			tagList.remove("$OPERATOR$");
    		}
    		else if (tagList.get("$OPERATOR$").contains("AND"))
    		{
    			type = 'a';
    			tagList.remove("$OPERATOR$");
    		}
    	}
    	ArrayList<Photo> results = new ArrayList<Photo>();
    	ArrayList<Album> userAlbums = user.albumList;
    	Iterator<Album> albums = userAlbums.iterator();
    	Album currAlbum;
    	while (albums.hasNext())
    	{
    		currAlbum = albums.next();
    		Photo[] photoList = (Photo[])currAlbum.contents.toArray();
    		for (int i = 0; i < photoList.length; i++)
    		{
	    		if (type == 'o')
	    		{
	    			if (hasSomeTags(tagList, photoList[i].tags))
	    			{
	    				results.add(photoList[i]);
	    			}
	    		}
	    		else if (type == 'a')
	    		{
	    			if (hasAllTags(tagList, photoList[i].tags))
	    			{
	    				results.add(photoList[i]);
	    			}
	    		}
	    		else
	    		{
	    			if (hasAllTags(tagList, photoList[i].tags))
	    			{
	    				results.add(photoList[i]);
	    			}
	    		}
    		}
    	}
    	Date date = new Date();
    	Album resultAlbum = new Album("TagQuery" + date.toString());
    	resultAlbum.contents = results;
    	return resultAlbum;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
