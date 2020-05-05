package model;
import java.io.*;


public class Serialization {

    static  final String filePath = "data/userdata/";

    public static void serialize(User user) {
        try {
            String location = filePath.concat(user.getUserName());
            FileOutputStream fos = new FileOutputStream(location);
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(user);
            outputStream.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public static User deserialize(String username) {
        User savedUser = null;

        try {
            String location = filePath.concat(username);
            FileInputStream fis = new FileInputStream(location);
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            savedUser = (User) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException ex) {
            return new User(username);
        }
        return savedUser;
    }

    public static void deleteUserData(String username){
        try{
            String location = filePath.concat(username);
            File file = new File(location);
            file.delete();
        }catch ( Exception ex) {
            System.err.println(ex);
        }
    }
}
