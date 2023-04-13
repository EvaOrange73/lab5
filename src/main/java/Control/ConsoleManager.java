package Control;

import DataDescription.Album;
import DataDescription.Coordinates;
import DataDescription.MusicBand;
import DataDescription.MusicGenre;
import InputExceptions.FieldException;
import InputExceptions.InputException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;

/**
 * Менеджер консоли -- класс, отвечающий за чтение из консоли.
 */
public class ConsoleManager extends IOManager {

    private Scanner scanner;

    public ConsoleManager(String startFile, CollectionManager collectionManager) {
        super(new StartFileManager(startFile), collectionManager, new CommandManager(null, collectionManager));
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int getRecursionDepth() {
        return 0;
    }

    /**
     * Основной метод программы: считывает команды пользователя из консоли и вызывает их выполнение
     */
    public void start() {
        super.commandManager.setIoManagers(this);
        try {
            super.startFileManager.readStartFile(super.collectionManager);
        } catch (IOException e) {
            super.print("стартовый файл не найден");
        }
        super.print("""
                Здравствуйте! Предлагаю вам тестовые команды:

                Вызов скрипта, содержащего все команды с корректными аргументами:
                execute_script script_1
                                
                некорректные аргументы:
                execute_script script_2
                                
                рекурсивный вызов скрипта:
                execute_script script_3
                                
                Полная справка по командам:
                help
                """);
        while (this.scanner.hasNext()) {
            String command = this.scanner.nextLine();
            if (!(command.isEmpty()))
                super.commandManager.execute(command);
        }
        this.scanner.close();
    }

    /**
     * Считать музыкальную группу из консоли. Пользователю выводится приглашение ко вводу: "Введите _имя поля_".
     * При ошибке ввода пользователю предлагается ввести поле повторно.
     *
     * @return MusicBand
     * @throws InputException если пользователь допускает более 10 ошибок при вводе одного и того же поля
     */
    @Override
    public MusicBand askMusicBand() throws InputException { //TODO Возможно этот метод можно объединить с askAlbum и askCoordinates с помощью дженериков? У меня не получилось
        super.print("Введите музыкальную группу");
        Field[] fields = MusicBand.class.getDeclaredFields();
        String[] args = new String[fields.length];
        Coordinates coordinates = null;
        MusicGenre genre = null;
        Album album = null;
        int i = 0;
        for (Field field : fields) {
            switch (field.getName()) { //TODO жесть)
                case "id", "creationDate", "nextId":
                    break;
                case "coordinates":
                    coordinates = askCoordinates();
                    break;
                case "genre":
                    genre = askMusicGenre();
                    break;
                case "bestAlbum": {
                    album = askAlbum();
                    break;
                }
                default: {
                    System.out.println("Введите " + field.getName());
                    args[i] = this.scanner.nextLine();
                    i++;
                    break;
                }
            }
        }
        return formMusicBand(args, coordinates, genre, album, 0);
    }

    private MusicBand formMusicBand(String[] args, Coordinates coordinates, MusicGenre genre, Album album, int recursionDepth) throws InputException {
        if (recursionDepth > 10) throw new InputException() {
            @Override
            public String toString() {
                return "Вы слишком много раз неверно вводили поля";
            }
        };
        try {
            return new MusicBand(args, coordinates, genre, album);
        } catch (FieldException e) {
            super.print(e.toString());
            super.print("Повторите ввод");
            args[e.getFieldNumber()] = this.scanner.nextLine();
            return formMusicBand(args, coordinates, genre, album, recursionDepth + 1);
        }
    }

    private Album formAlbum(String[] args, int recursionDepth) throws InputException {
        if (recursionDepth > 10) throw new InputException() {
            @Override
            public String toString() {
                return "Вы слишком много раз неверно вводили поля";
            }
        };
        try {
            return new Album(args);
        } catch (FieldException e) {
            super.print(e.toString());
            super.print("Повторите ввод");
            args[e.getFieldNumber()] = this.scanner.nextLine();
            return formAlbum(args, recursionDepth + 1);
        }
    }

    private Album askAlbum() throws InputException {
        super.print("Введите альбом");
        Field[] fields = Album.class.getDeclaredFields();
        String[] args = new String[fields.length];

        System.out.println("Введите " + fields[0].getName());
        args[0] = this.scanner.nextLine();
        if (args[0].isEmpty()) return null;

        for (int i = 1; i < fields.length; i++) {
            System.out.println("Введите " + fields[i].getName());
            args[i] = this.scanner.nextLine();
        }
        return formAlbum(args, 0);
    }

    private Coordinates formCoordinates(String[] args, int recursionDepth) throws InputException {
        if (recursionDepth > 10) throw new InputException() {
            @Override
            public String toString() {
                return "Вы слишком много раз неверно вводили поля";
            }
        };
        try {
            return new Coordinates(args);
        } catch (FieldException e) {
            super.print(e.toString());
            super.print("Повторите ввод");
            args[e.getFieldNumber()] = this.scanner.nextLine();
            return formCoordinates(args, recursionDepth + 1);
        }
    }

    private Coordinates askCoordinates() throws InputException {
        super.print("Введите координаты");
        Field[] fields = Coordinates.class.getDeclaredFields();
        String[] args = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            super.print("Введите " + fields[i].getName());
            args[i] = this.scanner.nextLine();
        }
        return formCoordinates(args, 0);
    }


    private MusicGenre fornMusicGenre(String i, int recursionDepth) throws InputException {
        if (recursionDepth > 10) throw new InputException() {
            @Override
            public String toString() {
                return "Вы слишком много раз неверно вводили поля";
            }
        };
        try {
            return MusicGenre.values()[Integer.parseInt(i) - 1];
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e1) {
            super.print("Введите номер жанра из списка");
            return fornMusicGenre(this.scanner.nextLine(), recursionDepth + 1);
        }
    }

    private MusicGenre askMusicGenre() throws InputException {
        super.print("Выберете музыкальный жанр:");
        for (int i = 0; i < MusicGenre.values().length; i++) {
            super.print((i + 1) + ": " + MusicGenre.values()[i]);
        }
        return fornMusicGenre(this.scanner.nextLine(), 0);
    }
}
