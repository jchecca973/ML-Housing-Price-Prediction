package model;
import java.io.Serializable;
import java.util.*;

public class Album
 implements Serializable {
	public String albumName;
	public ArrayList<Photo> contents;
	public Date earliestDate;
	public Date latestDate;
	
	public Album(String albumName)
	{
		this.albumName = albumName;
		this.contents = new ArrayList<Photo>();
		earlistDate = new Date();
	}

	public void renameAlbum(String name){
		this.albumName = albumName;
	}

	public String getDateRange(){
		return " ";
	}

	public int numberOfPhoto(){
		int result = 0;

		for(Photo s : contents){
			result++;
		}
		return result;
	}


	public boolean addPhoto(Photo pic)
	{
		if (pic == null) return false;
		else if (pic.getFile() == null) return false;
		else
		{
			this.contents.add(pic);
			return true;
		}
	}
	
	public boolean removePhoto(Photo pic)
	{
		if (pic == null) return false;
		else if (pic.getFile() == null) return false;
		else
		{
			this.contents.remove(pic);
			return true;
		}
	}
	
	public void createDateRange()
	{
		if (contents.size() == 0) return;
		else if (contents.size() == 1)
		{
			this.earliestDate = contents.get(0).dateTaken;
			this.latestDate = this.earliestDate;
		}
		else
		{
			Photo[] photos = (Photo[])contents.toArray();
			this.earliestDate = photos[0].dateTaken;
			this.latestDate = this.earliestDate;
			for (int i = 1; i < photos.length; i++)
			{
				if (photos[i].dateTaken.compareTo(this.earliestDate) < 0)
				{
					this.earliestDate = photos[i].dateTaken;
				}
				else if (photos[i].dateTaken.compareTo(this.latestDate) > 0)
				{
					this.latestDate = photos[i].dateTaken;
				}
			}
		}
	}
}
