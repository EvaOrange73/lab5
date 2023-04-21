package Control;

import Control.Messages.Answer;
import Control.Messages.Question;
import Data.Description.*;
import Data.MusicBand;
import InputExceptions.InputException;

import java.io.IOException;
import java.util.HashMap;
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
    public MusicBand askMusicBand() throws InputException {
        DataFactory musicBandFactory = new DataFactory(DataTypes.MUSIC_BAND);
        int reaskCount = 0;
        while (musicBandFactory.hasUnsetFields() || reaskCount == 0) {
            if (reaskCount == 0) super.print("Введите музыкальную группу");
            else super.print("Пожалуйста, повторите ввод");
            HashMap<Integer, Answer> answers = this.askFields(musicBandFactory.getQuestions());
            musicBandFactory.setAnswers(answers);
            reaskCount++;
            if (reaskCount > 10)
                throw new InputException() { //TODO поменять InputException
                    @Override
                    public String toString() {
                        return "Вы слишком много раз неверно вводили поля";
                    }
                };
        }
        return (MusicBand) musicBandFactory.getFormedObject();
    }

    private HashMap<Integer, Answer> askFields(HashMap<Integer, Question> questions) {
        HashMap<Integer, Answer> answers = new HashMap<>();
        questions.forEach((key, question) -> {
            super.print(question.getQuestion());
            String answer = scanner.nextLine();
            if (question.isComposite()) {
                Answer compositeAnswer = new Answer(answer, true);
                if (answer.equals("y")) {
                    compositeAnswer.addSubAnswers(askFields(question.getSubQuestions()));
                    answers.put(key, compositeAnswer);
                }
            } else {
                answers.put(key, new Answer(answer, false));
            }
        });
        return answers;
    }
}
