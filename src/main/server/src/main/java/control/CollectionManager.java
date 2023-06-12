package control;

import data.MusicBand;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 * Менеджер коллекции -- класс, осуществляющий работу с коллекцией:
 * сортировку, добавление элементов,
 * доступ к множеству элементов, типу коллекции и т.д.
 */
public class CollectionManager {
    private LinkedHashSet<MusicBand> collection = new LinkedHashSet<>();
    private final Date creationDate = new Date();
    private final DatabaseManager databaseManager;

    public CollectionManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void setCollection(LinkedHashSet<MusicBand> musicBands) {
        collection = musicBands;
        this.sort();
    }

    public MusicBand getById(Integer id) {
        for (MusicBand musicBand : this.collection) {
            if (musicBand.getId().equals(id)) {
                return musicBand;
            }
        }
        return null;
    }

    public LinkedHashSet<MusicBand> getCollection() {
        return this.collection;
    }

    public String getType() {
        return this.collection.getClass().toString();
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public int getSize() {
        return this.collection.size();
    }

    public void remove(int musicBandId) {
        databaseManager.removeMusicBand(musicBandId);
        this.collection.removeIf(musicBand -> musicBand.getId() == (musicBandId));
    }

    public void add(MusicBand musicBand) {
        databaseManager.insertAndGetId(musicBand);
        collection.add(musicBand);
        this.sort();
    }

    public void update(MusicBand oldMusicBand, MusicBand newMusicBand) {
        databaseManager.update(newMusicBand);
        collection.remove(oldMusicBand);
        collection.add(newMusicBand);
        this.sort();
    }

    public MusicBand getMaxElement() {
        return this.collection.iterator().next();
    }

    public MusicBand getMinElement() {
        MusicBand maxMusicBand = null;
        for (MusicBand musicBand : this.collection)
            maxMusicBand = musicBand;
        return maxMusicBand;
    }

    private void sort() {
        collection = new LinkedHashSet<>(new TreeSet<>(collection));
    }
}
