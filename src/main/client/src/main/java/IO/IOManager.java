package IO;

import IO.InputExceptions.FieldsException;
import IO.InputExceptions.RecursionException;
import commands.CommandManager;
import data.MusicBand;
import data.description.DataTypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Менеджер ввода и вывода -- класс для чтения команд и их аргументов, а так же записи результатов их выполнения.
 */
public class IOManager {
    private CommandManager commandManager;
    private Input input;

    public IOManager(Input input) {
        this.input = input;
    }

    /**
     * Подкласс, реализующий типы ввода: консоль и исполняемый файл
     */
    public enum Input {
        CONSOLE(new Scanner(System.in)),
        SCRIPT(new Scanner(""));

        Scanner scanner;
        final Stack<Scanner> previousScanners;

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
            File file = new File(path);
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
    public void start(boolean isConnected) {
        String startMessage = isConnected ? """
                Здравствуйте! Предлагаю вам тестовые команды:

                Вызов скрипта, содержащего все команды с корректными аргументами:
                execute_script /home/studs/s388052/scripts/script_1.txt
                                
                некорректные аргументы:
                execute_script /home/studs/s388052/scripts/script_2.txt
                                
                рекурсивный вызов скрипта:
                execute_script /home/studs/s388052/scripts/script_3.txt
                                
                Полная справка по командам:
                help
                """
                :
                "сервер временно недоступен";
        if (this.input == Input.CONSOLE) print(startMessage);
        while (this.input.hasNext()) { //TODO так плохо делать?
            String command = this.input.nextLine();
            if (!(command.isEmpty())) {
                if (this.input == Input.SCRIPT) print("\nкоманда " + command);
                this.print(this.commandManager.execute(command));
            }
        }
        if (this.input.hasPreviousFile())
            this.input.continueExecutePreviousFile();
        else
            this.input = Input.CONSOLE;

    }

    /**
     * Запускает выполнение файла
     *
     * @param path - путь до исполняемого файла
     */
    public void startExecuteScript(String path) {
        this.input = Input.SCRIPT;
        try {
            this.input.setNewFile(path);
        } catch (RecursionException e) {
            print(e.toString());
            this.input.continueExecutePreviousFile();
        } catch (FileNotFoundException e) {
            this.print("исполняемый файл не найден");
            this.input.continueExecutePreviousFile();
        }
        this.start(true); //TODO true??
    }

    /**
     * Считать музыкальную группу для команд, которые используют её в качестве аргументов
     *
     * @return объект типа MusicBand
     * @throws FieldsException если объект некорректно записан в файле или пользователь слишком много раз пытался ввести его через консоль
     */
    public MusicBand askMusicBand() throws FieldsException {
        DataFactory musicBandFactory = new DataFactory(DataTypes.MUSIC_BAND);
        HashMap<Integer, Answer> answers = this.askFields(musicBandFactory.getQuestions());
        musicBandFactory.setAnswers(answers);

        if (musicBandFactory.hasUnsetFields()) {
            if (this.input == Input.CONSOLE) this.reaskFields(0, musicBandFactory);
            else if (this.input == Input.SCRIPT) throw new FieldsException(musicBandFactory.getExceptionsList());
        }

        return (MusicBand) musicBandFactory.getFormedObject();
    }

    private HashMap<Integer, Answer> askFields(HashMap<Integer, Question> questions) {
        HashMap<Integer, Answer> answers = new HashMap<>();
        questions.forEach((key, question) -> {
            if (this.input == Input.CONSOLE) this.print(question.getQuestion());
            if (question.isComposite()) {
                String answer = question.isNullableComposite() ? input.nextLine() : "y";
                Answer compositeAnswer = new Answer(answer);
                if (answer.equals("y"))
                    compositeAnswer.addSubAnswers(askFields(question.getSubQuestions()));
                answers.put(key, compositeAnswer);
            } else {
                answers.put(key, new Answer(input.nextLine()));
            }
        });
        return answers;
    }

    private void reaskFields(int reaskCount, DataFactory musicBandFactory) throws FieldsException {
        if (musicBandFactory.hasUnsetFields()) {
            this.print("Пожалуйста, повторите ввод");
            HashMap<Integer, Answer> answers = this.askFields(musicBandFactory.getQuestions());
            musicBandFactory.setAnswers(answers);
            if (reaskCount > 10)
                throw new FieldsException();
            reaskFields(reaskCount + 1, musicBandFactory);
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
