package Control;


import DataDescription.DataFactory;
import DataDescription.DataTypes;
import DataDescription.MusicBand;
import InputExceptions.FieldException;
import InputExceptions.RecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Менеджер исполняемого файла -- класс, отвечающий за чтение команд и их аргументов из файла.
 */
public class ScriptManager extends IOManager {
    private String fileName;
    private Scanner scanner;
    private int recursionDepth;

    /**
     * @param fileName       имя файла
     * @param recursionDepth глубина рекурсии
     * @throws FileNotFoundException файл не найден
     * @throws RecursionException    превышена максимальная глубина рекурсии при вызове команды execute_script внутри другого скрипта
     */
    public ScriptManager(String fileName, StartFileManager startFileManager, CollectionManager collectionManager, CommandManager commandManager, int recursionDepth) throws FileNotFoundException, RecursionException {
        super(startFileManager, collectionManager, commandManager);
        if (recursionDepth > 10) throw new RecursionException();
        this.recursionDepth = recursionDepth;
        this.fileName = (new File("")).getAbsolutePath() + "/src/main/resources/" + fileName + ".txt";
        File file = new File(this.fileName);
        this.scanner = new Scanner(file);
    }

    /**
     * Считывает команды из файла и вызывает их выполнение
     */
    public void executeFile() {
        String currentLine = "";
        while (scanner.hasNext()) {
            currentLine = this.nextLine();
            if (!(currentLine.isEmpty())) {
                super.print("\nкоманда " + currentLine);
                super.commandManager.execute(currentLine);
            }
        }
        scanner.close();
    }

    /**
     * Считать музыкальную группу из файла. Предполагается, что поля объекта записаны на отдельных строках.
     *
     * @return MusicBand
     * @throws FieldException если поля объекта некорректны (пустая строка, неверный тип и т.п.)
     */
    @Override
    public MusicBand askMusicBand() throws FieldException {
        String[] args = new String[11];
        for (int i = 0; i < 7; i++) {
            args[i] = this.nextLine();
        }
        args[7] = this.nextLine();
        if (args[7].isEmpty())
            try {
                return (MusicBand) DataFactory.formObject(DataTypes.MUSIC_BAND, args);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        for (int i = 8; i < 11; i++) {
            args[i] = this.nextLine();
        }
        try {
            return (MusicBand) DataFactory.formObject(DataTypes.MUSIC_BAND, args);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRecursionDepth() {
        return this.recursionDepth;
    }

    private String nextLine() {
        return this.scanner.nextLine().split("#")[0].trim();
    }
}
