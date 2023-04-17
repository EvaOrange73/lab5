package Control;

import Data.MusicBand;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * Менеджер стартового файла -- класс, отвечающий за чтение и запись в стартовый файл.
 * Стартовый файл передается программе с помощью переменной окружения.
 */
public class StartFileManager {
    private String fileName;

    public StartFileManager(String fileName) {
        this.fileName = (new File("")).getAbsolutePath() + "/src/main/resources/" + fileName;
    }

    /**
     * @param collectionManager менеджер коллекции, в которую сохранятся считанные данные
     * @throws IOException если файл не найден
     */
    public void readStartFile(CollectionManager collectionManager) throws IOException {
        StringBuilder str = new StringBuilder();
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
     */
    public void save(LinkedHashSet<MusicBand> collection) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(this.fileName, false);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        byte[] buffer = gson.toJson(collection).getBytes();
        fileOutputStream.write(buffer, 0, buffer.length);
        fileOutputStream.close();
    }
}
