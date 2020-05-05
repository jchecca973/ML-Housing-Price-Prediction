package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
	public String userName;
	public ArrayList<Album> albumList;

	public User(String name)
	{
		this.userName = name;
		albumList = new ArrayList<Album>();
	}

	public String getUserName() {
		return userName;
	}

	public ArrayList<Album> getAlbumList(){
		return albumList;
	}

	public void addAlbum(Album newAlbum){
		this.albumList.add(newAlbum);
	}

	public Album getAlbum(String albumName){
		for(Album i : albumList){
			if (i.albumName.equals(albumName)){
				return i;
			}
		}
		return null;
	}

	public void deleteAlbum(String albumName){
		for(Album s : albumList){
			if(s.albumName.equals(albumName)){
				this.albumList.remove(s);
				return;
			}
		}
	}

	public boolean isInAlbumList(String albumName){
		for(Album i : albumList){
			if(i.albumName.equals(albumName)){
				return true;
			}
		}
		return false;
	}
	public boolean isAlbumDuplicated(String albumName){

	for (Album i : albumList){
		if (i.albumName.equals(albumName)){
			return true;
		}
	}
	return false;
	}
}
