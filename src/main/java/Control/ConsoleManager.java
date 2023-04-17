package Control;

import Data.*;
import Data.Description.DataFactory;
import Data.Description.DataTypes;
import InputExceptions.FieldException;
import InputExceptions.InputException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
        ArrayList<String> args = new ArrayList<>();
        for (Field field : fields) {
            switch (field.getName()) {
                case "id", "creationDate", "nextId":
                    break;
                case "coordinates":
                    args.addAll(askCoordinates());
                    break;
                case "genre":
                    args.add(askMusicGenre());
                    break;
                case "bestAlbum": {
                    args.addAll(askAlbum());
                    break;
                }
                default: {
                    System.out.println("Введите " + field.getName());
                    args.add(this.scanner.nextLine());
                    break;
                }
            }
        }
        return formMusicBand(args, 0);
    }

    private MusicBand formMusicBand(ArrayList<String> args, int recursionDepth) throws InputException {
        if (recursionDepth > 10) throw new InputException() {
            @Override
            public String toString() {
                return "Вы слишком много раз неверно вводили поля";
            }
        };
        try {
            return (MusicBand) DataFactory.formObject(DataTypes.MUSIC_BAND, args.toArray(new String[0]));
        } catch (FieldException e) {
            super.print(e.toString());
            super.print("Повторите ввод");
            args.set(e.getFieldNumber(), this.scanner.nextLine());
            return formMusicBand(args, recursionDepth + 1);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private ArrayList<String> askAlbum() throws InputException {
        super.print("Введите альбом");
        Field[] fields = Album.class.getDeclaredFields();
        ArrayList<String> args = new ArrayList<>();

        System.out.println("Введите " + fields[0].getName());
        args.add(this.scanner.nextLine());
        if (args.get(0).isEmpty()) return null;

        for (int i = 1; i < fields.length; i++) {
            System.out.println("Введите " + fields[i].getName());
            args.add(this.scanner.nextLine());
        }
        return args;
    }


    private ArrayList<String> askCoordinates() {
        super.print("Введите координаты");
        Field[] fields = Coordinates.class.getDeclaredFields();
        ArrayList<String> args = new ArrayList<>();
        for (Field field : fields) {
            super.print("Введите " + field.getName());
            args.add(this.scanner.nextLine());
        }
        return args;
    }


    private String askMusicGenre() {
        super.print("Выберете музыкальный жанр:");
        for (int i = 0; i < MusicGenre.values().length; i++) {
            super.print((i + 1) + ": " + MusicGenre.values()[i]);
        }
        return this.scanner.nextLine();
    }
}
