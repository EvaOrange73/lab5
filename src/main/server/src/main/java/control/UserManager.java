package control;

import data.MusicBand;
import data.User;

import java.util.ArrayList;

public class UserManager {
    DatabaseManager databaseManager;
    CollectionManager collectionManager;

    public UserManager(DatabaseManager databaseManager, CollectionManager collectionManager) {
        this.databaseManager = databaseManager;
        this.collectionManager = collectionManager;
    }

    public Integer authorize(User user) {
        User userFromDatabase = this.databaseManager.getUserByName(user.getUsername());
        if (userFromDatabase == null) {
            return this.databaseManager.insertAndGetId(user);
        } else {
            if (user.getPassword().equals(userFromDatabase.getPassword())) {
                return userFromDatabase.getId();
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
