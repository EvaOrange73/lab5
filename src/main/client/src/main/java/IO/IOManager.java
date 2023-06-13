package IO;

import IO.InputExceptions.FieldsException;
import IO.InputExceptions.RecursionException;
import commands.CommandManager;
import data.MusicBand;
import data.User;
import data.description.DataTypes;
import server.ServerManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Менеджер ввода и вывода -- класс для чтения команд и их аргументов, а так же записи результатов их выполнения.
 */
public class IOManager {

    private Input input;
    private CommandManager commandManager;
    private final ServerManager serverManager;

    private User user;

    public IOManager(ServerManager serverManager) {
        this.input = Input.CONSOLE;
        this.serverManager = serverManager;
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

    public void setUser(User user){
        this.user = user;
    }

    public User authorize() {
        return this.authorize(0);
    }

    public User authorize(int counter) {
        if (counter > 2) {
            this.print("Вы ввели пароль неправильно слишком много раз");
            return null;
        }

        this.print("Логин:");
        User user = new User();
        user.setUsername(this.input.nextLine());
        this.print("Пароль:");
        String password = this.input.nextLine();
        if (!(password.isEmpty())) user.setPassword(password);
        try {
            Integer userId = this.serverManager.authorize(user);
            if (userId == null) {
                this.print("Неверный пароль");
                return this.authorize(counter + 1);
            }
            user.setId(userId);
        } catch (IOException e) {
            this.print("Сервер временно недоступен. Попробуйте позже.");
            return this.authorize(counter);
        }
        return user;
    }

    public void printStartMessage() {
        String startMessage = """
                Здравствуйте, %s! Предлагаю вам тестовые команды:

                Вызов скрипта, содержащего все команды с корректными аргументами:
                execute_script /home/studs/s388052/scripts/script_1.txt
                                
                некорректные аргументы:
                execute_script /home/studs/s388052/scripts/script_2.txt
                                
                рекурсивный вызов скрипта:
                execute_script /home/studs/s388052/scripts/script_3.txt
                                
                Полная справка по командам:
                help
                """.formatted(this.user.getUsername());
        if (this.input == Input.CONSOLE) print(startMessage);
    }

    /**
     * Основной метод программы: считывает команды и запускает их выполнение
     */
    public void readCommands() {
        while (this.input.hasNext()) {
            String command = this.input.nextLine();
            if (!(command.isEmpty())) {
                if (this.input == Input.SCRIPT) print("\nкоманда " + command);
                this.print(this.commandManager.execute(command, user.getId()));
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
        this.readCommands();
    }

    /**
     * Считать музыкальную группу для команд, которые используют её в качестве аргументов
     *
     * @return объект типа MusicBand
     * @throws FieldsException если объект некорректно записан в файле или пользователь слишком много раз пытался ввести его через консоль
     */
    public MusicBand askMusicBand() throws FieldsException {
        DataFactory musicBandFactory = new DataFactory(DataTypes.MUSIC_BAND, user.getId());
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
