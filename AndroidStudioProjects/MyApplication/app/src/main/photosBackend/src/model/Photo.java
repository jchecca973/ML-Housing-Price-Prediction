package model;
import java.io.File;
import java.io.Serializable;
import java.util.*;

public class Photo
implements Serializable {
	public File file;
	public Date dateTaken;
	public String caption;
	public HashMap<String, ArrayList<String>> tags;


	public Photo(String filePath)
	{
		this.file = new File(filePath);
		this.tags = new HashMap<String, ArrayList<String>>();
		dateTaken = new Date(this.file.lastModified());
	}

	public File getFile(){
		return this.file;
	}

	public void setCaption(String caption){
		this.caption = caption;
	}
	
	public boolean addTag(String tagName, ArrayList<String> tagValues)
	{
		this.tags.put(tagName, tagValues);
		return true;
	}

	public boolean isTagDuplicated(String tagName, ArrayList<String> tagValues){
		if (this.tags.get(tagName) == null) {
			return false;
		}
		for (  String i : tags.get(tagName)){
			if( i .equals(tagValues)){
				return true;
			}
		}
		return false;

	}
	
	public boolean deleteTag(String key)
	{
		if (this.tags.get(key) != null)
		{
			this.tags.remove(key);
			return true;
		}
		else
		{
			System.out.println("Tag not found");
			return false;
		}
	}
}
