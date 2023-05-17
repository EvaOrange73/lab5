package control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import data.MusicBand;
import exceptions.EnvException;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * Менеджер стартового файла -- класс, отвечающий за чтение и запись в стартовый файл.
 * Стартовый файл передается программе с помощью переменной окружения.
 */
public class StartFileManager {
    private final String fileName;
    private final CollectionManager collectionManager;

    public StartFileManager(String fileName, CollectionManager collectionManager) {
        this.fileName = fileName;
        this.collectionManager = collectionManager;
    }

    /**
     * @throws IOException  если файл не найден
     * @throws EnvException если не задана переменная окружения
     */
    public void readStartFile() throws IOException, EnvException {
        StringBuilder str = new StringBuilder();
        if (fileName == null) throw new EnvException();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String currentLine = "";
        while ((currentLine = bufferedReader.readLine()) != null) {
            str.append(currentLine);
        }
        bufferedReader.close();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        ArrayList<MusicBand> musicBands = gson.fromJson(str.toString(), new TypeToken<List<MusicBand>>() {
        }.getType());
        this.collectionManager.setStartCollection(musicBands);
    }

    /**
     * @throws IOException  если файл не найден
     * @throws EnvException если не задана переменная окружения
     */
    public void save() throws IOException, EnvException {
        if (fileName == null) throw new EnvException();
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.fileName, false)) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            byte[] buffer = gson.toJson(this.collectionManager.getCollection()).getBytes();
            fileOutputStream.write(buffer, 0, buffer.length);
        }
    }

//TODO    /**
//     * @param data коллекция, которую нужно сохранить
//     */
//    public void saveInStartFile(LinkedHashSet<MusicBand> data) {
//        try {
//            this.save(data);
//        } catch (IOException e) {
//            this.print("стартовый файл не найден");
//        } catch (EnvException e) {
//            this.print(e.toString());
//        }
//    }
//        try {
//            startFileManager.readStartFile(this.collectionManager);
//        } catch (IOException e) {
//            print("стартовый файл не найден");
//        } catch (EnvException e) {
//            print(e.toString());
//        }
}
