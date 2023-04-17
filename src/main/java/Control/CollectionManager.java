package Control;

import DataDescription.Generation.IdGenerator;
import DataDescription.MusicBand;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Менеджер коллекции -- класс, осуществляющий работу с коллекцией:
 * сортировку, добавление элементов,
 * доступ к множеству элементов, типу коллекции и т.д.
 */
public class CollectionManager {
    private LinkedHashSet<MusicBand> collection = new LinkedHashSet<>();
    private Date creationDate = new Date();

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
        MusicBand maxMusicBand = null;
        for (MusicBand musicBand : this.collection)
            maxMusicBand = musicBand;
        return maxMusicBand;
    }

    public MusicBand getMinElement() {
        return this.collection.iterator().next();
    }

    private void sort() { //TODO ужс
        List<MusicBand> collectionList = collection.stream().sorted(MusicBand::compareTo).collect(Collectors.toList());
        Collections.reverse(collectionList);
        collection = new LinkedHashSet<>(collectionList);
    }
}
