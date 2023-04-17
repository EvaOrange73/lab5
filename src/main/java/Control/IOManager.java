package Control;

import Data.MusicBand;
import InputExceptions.InputException;

import java.io.IOException;
import java.util.LinkedHashSet;

/**
 * Менеджер ввода и вывода -- абстрактный класс для чтения команд и их аргументов, а так же записи результатов их выполнения.
 * Имеет двух наследников: менеджер консоли и менеджер исполняемого файла.
 * Сам класс отвечает за вывод, а наследники -- за ввод.
 */
public abstract class IOManager {
    StartFileManager startFileManager;
    CollectionManager collectionManager;
    CommandManager commandManager;

    /**
     * @param startFileManager  для записи результатов программы в файл
     * @param collectionManager общий параметр двух наследников
     * @param commandManager    для вызова команд
     */
    IOManager(StartFileManager startFileManager, CollectionManager collectionManager, CommandManager commandManager) {
        this.startFileManager = startFileManager;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    /**
     * Так как менеджеры файлов создаются рекурсивно в команде execute_script, необходимо следить за глубиной рекурсии
     *
     * @return текущая глубина рекурсии
     */
    public abstract int getRecursionDepth();

    /**
     * Считать музыкальную группу для команд, которые используют её в качестве аргументов
     *
     * @return объект типа MusicBand
     * @throws InputException если объект некорректно записан в файле или пользователь слишком много раз пытался ввести его через консоль
     */
    public abstract MusicBand askMusicBand() throws InputException;

    /**
     * @param data коллекция, которую нужно сохранить
     */
    public void saveInStartFile(LinkedHashSet<MusicBand> data) {
        try {
            startFileManager.save(data);
        } catch (IOException e){
            this.print("стартовый файл не найден");
        }
    }

    /**
     * Весь вывод сообщений в консоль реализуется этим методом
     *
     * @param message сообщение
     */
    public void print(String message) {
        System.out.print(message + "\n");
    }

    public StartFileManager getStartFileManager() {
        return startFileManager;
    }
}
