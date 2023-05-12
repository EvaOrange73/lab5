package control;

import data.generation.IdGenerator;
import data.MusicBand;

import java.util.*;

/**
 * Менеджер коллекции -- класс, осуществляющий работу с коллекцией:
 * сортировку, добавление элементов,
 * доступ к множеству элементов, типу коллекции и т.д.
 */
public class CollectionManager {
    private LinkedHashSet<MusicBand> collection = new LinkedHashSet<>();
    private final Date creationDate = new Date();

    /**
     * Метод, заполняющий коллекцию элементами, считанными из стартового файла
     *
     * @param musicBands список элементов
     */
    public void setStartCollection(ArrayList<MusicBand> musicBands) {
        collection.addAll(musicBands);
        this.sort();
        int maxId = 0;
        for (MusicBand musicBand : collection)
            if (musicBand.getId() > maxId) maxId = musicBand.getId();
        IdGenerator.setNextId(maxId + 1);
    }

    public LinkedHashSet<MusicBand> getCollection() {
        return this.collection;
    }

    public void clear() {
        this.collection.clear();
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

    public void remove(MusicBand musicBand) {
        collection.remove(musicBand);
    }

    public void add(MusicBand musicBand) {
        collection.add(musicBand);
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
