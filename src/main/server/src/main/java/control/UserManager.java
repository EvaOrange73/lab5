package control;

import data.MusicBand;
import data.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class UserManager {
    DatabaseManager databaseManager;
    CollectionManager collectionManager;

    public UserManager(DatabaseManager databaseManager, CollectionManager collectionManager) {
        this.databaseManager = databaseManager;
        this.collectionManager = collectionManager;
    }

    public String getHash(String str){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD2");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1,messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public User authorize(User user) {
        User userFromDatabase = this.databaseManager.getUserByName(user.getUsername());
        if (userFromDatabase == null) {
            user.generateSalt();
            user.setPassword(getHash("pepper" + user.getSalt() + user.getPassword()));
            user.setId(this.databaseManager.insertAndGetId(user));
            return user;
        } else {
            if (getHash(userFromDatabase.getSalt() + user.getPassword()).equals(userFromDatabase.getPassword())) {
                return userFromDatabase;
            }
            else
                return null;
        }
    }

    public ArrayList<Integer> getListOfUserOwnedItemIds(Integer userID) {
        ArrayList<Integer> answer = new ArrayList<>();
        for (MusicBand musicBand: this.collectionManager.getCollection()){
            if (musicBand.getCreatorId().equals(userID))
                answer.add(musicBand.getId());
        }
        return answer;
    }

    public boolean checkRights(Integer userID, Integer musicBandId){
        for (MusicBand musicBand: this.collectionManager.getCollection()){
            if (musicBand.getId().equals(musicBandId) && musicBand.getCreatorId().equals(userID))
                return true;
        }
        return false;
    }
}
