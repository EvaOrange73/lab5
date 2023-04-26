package Control;

import Control.Messages.Answer;
import Control.Messages.Question;
import Data.Description.DataFactory;
import Data.Description.DataTypes;
import Data.MusicBand;
import InputExceptions.InputException;
import InputExceptions.RecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Stack;

/**
 * Менеджер ввода и вывода -- класс для чтения команд и их аргументов, а так же записи результатов их выполнения.
 */
public class IOManager {
    private StartFileManager startFileManager;
    private CollectionManager collectionManager;
    private CommandManager commandManager;
    private Input input;

    /**
     * @param startFileManager  для записи результатов программы в файл
     * @param collectionManager общий параметр двух наследников
     */
    public IOManager(CollectionManager collectionManager, StartFileManager startFileManager, Input input) {
        this.startFileManager = startFileManager;
        this.collectionManager = collectionManager;
        this.input = input;
    }

    /**
     * Подкласс, реализующий типы ввода: консоль и исполняемый файл
     */
    public enum Input {
        CONSOLE(new Scanner(System.in)),
        SCRIPT(new Scanner(""));

        Scanner scanner;
        Stack<Scanner> previousScanners;

        Input(Scanner scanner) {
            this.scanner = scanner;
            this.previousScanners = new Stack<>();
        }

        private boolean hasPreviousFile() {
            return !(previousScanners.isEmpty());
        }

        private void setNewFile(String path) throws RecursionException, FileNotFoundException {
            if (this.previousScanners.size() > 10) throw new RecursionException();
            this.previousScanners.push(this.scanner);
            File file = new File((new File("")).getAbsolutePath() + "/src/main/resources/" + path + ".txt");
            this.scanner = new Scanner(file);
        }

        private void continueExecutePreviousFile() {
            this.scanner = this.previousScanners.pop();
        }

        private String nextLine() {
            return this.scanner.nextLine().split("#")[0].trim();
        }

        private boolean hasNext() {
            return this.scanner.hasNext();
        }
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Основной метод программы: считывает команды и запускает их выполнение
     */
    public void start() {
        try {
            startFileManager.readStartFile(this.collectionManager);
        } catch (IOException e) {
            print("стартовый файл не найден");
        }
        if (this.input == Input.CONSOLE) print("""
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
        while (this.input.hasNext()) { //TODO так плохо делать?
            String command = this.input.nextLine();
            if (!(command.isEmpty())) {
                if (this.input == Input.SCRIPT) print("\nкоманда " + command);
                this.commandManager.execute(command);
            }
        }
        if (this.input.hasPreviousFile())
            this.input.continueExecutePreviousFile();
        else
            this.input = Input.CONSOLE;

    }

    /**
     * Запускает выполнение файла
     * @param path - путь до исполняемого файла
     */
    public void startExecuteScript(String path) {
        this.input = Input.SCRIPT;
        try {
            this.input.setNewFile(path);
        } catch (RecursionException | FileNotFoundException e) { //TODO свой файл нот фаунд
            print(e.toString());
            this.input.continueExecutePreviousFile();
        }
        this.start();
    }

    /**
     * Считать музыкальную группу для команд, которые используют её в качестве аргументов
     *
     * @return объект типа MusicBand
     * @throws InputException если объект некорректно записан в файле или пользователь слишком много раз пытался ввести его через консоль
     */
    public MusicBand askMusicBand() throws InputException {
        DataFactory musicBandFactory = new DataFactory(DataTypes.MUSIC_BAND);
        HashMap<Integer, Answer> answers = this.askFields(musicBandFactory.getQuestions());
        musicBandFactory.setAnswers(answers);

        if (musicBandFactory.hasUnsetFields()) {
            if (this.input == Input.CONSOLE) this.reaskFields(0, musicBandFactory);
            else if (this.input == Input.SCRIPT) throw new InputException() {
                @Override
                public String toString() {
                    return "Не все поля введены верно" + musicBandFactory.getExceptionsList(); //TODO печатать ошибки
                }
            };
        }

        return (MusicBand) musicBandFactory.getFormedObject();
    }

    private HashMap<Integer, Answer> askFields(HashMap<Integer, Question> questions) {
        HashMap<Integer, Answer> answers = new HashMap<>();
        questions.forEach((key, question) -> {
            if (this.input == Input.CONSOLE) this.print(question.getQuestion());
            String answer = input.nextLine();
            if (question.isComposite()) {
                Answer compositeAnswer = new Answer(answer, true);
                if (answer.equals("y"))
                    compositeAnswer.addSubAnswers(askFields(question.getSubQuestions()));
                answers.put(key, compositeAnswer);
            } else {
                answers.put(key, new Answer(answer, false));
            }
        });
        return answers;
    }

    private void reaskFields(int reaskCount, DataFactory musicBandFactory) throws InputException {
        if (musicBandFactory.hasUnsetFields()) {
            this.print("Пожалуйста, повторите ввод");
            HashMap<Integer, Answer> answers = this.askFields(musicBandFactory.getQuestions());
            musicBandFactory.setAnswers(answers);
            if (reaskCount > 10)
                throw new InputException() { //TODO поменять InputException
                    @Override
                    public String toString() {
                        return "Вы слишком много раз неверно вводили поля";
                    }
                };
            reaskFields(reaskCount + 1, musicBandFactory);
        }
    }

    /**
     * @param data коллекция, которую нужно сохранить
     */
    public void saveInStartFile(LinkedHashSet<MusicBand> data) {
        try {
            startFileManager.save(data);
        } catch (IOException e) {
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
}
