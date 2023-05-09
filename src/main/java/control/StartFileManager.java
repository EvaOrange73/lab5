package control;

import data.MusicBand;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import inputExceptions.EnvException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * Менеджер стартового файла -- класс, отвечающий за чтение и запись в стартовый файл.
 * Стартовый файл передается программе с помощью переменной окружения.
 */
public class StartFileManager {
    private final String fileName;

    public StartFileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param collectionManager менеджер коллекции, в которую сохранятся считанные данные
     * @throws IOException  если файл не найден
     * @throws EnvException если не задана переменная окружения
     */
    public void readStartFile(CollectionManager collectionManager) throws IOException, EnvException {
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
        collectionManager.setStartCollection(musicBands);
    }

    /**
     * @param collection коллекция, которая будет сохранена в файл.
     * @throws IOException  если файл не найден
     * @throws EnvException если не задана переменная окружения
     */
    public void save(LinkedHashSet<MusicBand> collection) throws IOException, EnvException {
        if (fileName == null) throw new EnvException();
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.fileName, false)) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            byte[] buffer = gson.toJson(collection).getBytes();
            fileOutputStream.write(buffer, 0, buffer.length);
        }
    }
}
