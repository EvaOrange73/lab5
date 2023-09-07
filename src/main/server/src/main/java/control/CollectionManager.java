package control;

import data.MusicBand;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Менеджер коллекции -- класс, осуществляющий работу с коллекцией:
 * сортировку, добавление элементов,
 * доступ к множеству элементов, типу коллекции и т.д.
 */
public class CollectionManager {
    private LinkedHashSet<MusicBand> collection = new LinkedHashSet<>();
    private final Date creationDate = new Date();
    private final DatabaseManager databaseManager;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public CollectionManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    // Методы записи

    public void setCollection(LinkedHashSet<MusicBand> musicBands) {
        writeLock.lock();
        try {
            collection = musicBands;
            this.sort();
        } finally {
            writeLock.unlock();
        }
    }

    public void add(MusicBand musicBand) {
        writeLock.lock();
        try {
            Integer id = databaseManager.insertAndGetId(musicBand);
            musicBand.setId(id);
            collection.add(musicBand);
            this.sort();
        } finally {
            writeLock.unlock();
        }
    }

    public void update(MusicBand oldMusicBand, MusicBand newMusicBand) {
        writeLock.lock();
        try {
            databaseManager.update(oldMusicBand.getId(), newMusicBand);
            collection.remove(oldMusicBand);
            collection.add(newMusicBand);
            this.sort();
        } finally {
            writeLock.unlock();
        }
    }

    public void remove(int musicBandId) {
        writeLock.lock();
        try {
            databaseManager.removeMusicBand(musicBandId);
            this.collection.removeIf(musicBand -> musicBand.getId() == (musicBandId));
        } finally {
            writeLock.unlock();
        }
    }

    // Методы чтения

    public LinkedHashSet<MusicBand> getCollection() {
        readLock.lock();
        try {
            return this.collection;
        } finally {
            readLock.unlock();
        }
    }

    public MusicBand getById(Integer id) {
        readLock.lock();
        try {
            for (MusicBand musicBand : this.collection) {
                if (musicBand.getId().equals(id)) {
                    return musicBand;
                }
            }
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public MusicBand getMaxElement() {
        readLock.lock();
        try {
            return this.collection.iterator().next();
        } finally {
            readLock.unlock();
        }
    }

    public MusicBand getMinElement() {
        readLock.lock();
        try {
            MusicBand maxMusicBand = null;
            for (MusicBand musicBand : this.collection)
                maxMusicBand = musicBand;
            return maxMusicBand;
        } finally {
            readLock.unlock();
        }
    }

    // Остальные

    public int getSize() {
        readLock.lock();
        try {
            return this.collection.size();
        } finally {
            readLock.unlock();
        }
    }

    public String getType() {
        return this.collection.getClass().toString();
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    private void sort() {
        collection = new LinkedHashSet<>(new TreeSet<>(collection));
    }
}
