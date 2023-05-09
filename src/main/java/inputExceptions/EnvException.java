package inputExceptions;

public class EnvException extends Exception {
    @Override
    public String toString(){
        return """
                Необходимо задать путь до стартового файла через переменную окружения:
                Пример:
                start_file=/home/studs/s388052/data.json
                export start_file
                """;
    }
}
