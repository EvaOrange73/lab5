package Data.Description;

import Data.*;
import Data.Generation.Generator;
import InputExceptions.FieldException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;

public class DataFactory {
    public static DataDescription formObject(DataTypes dataType, String[] args) throws FieldException, InstantiationException, IllegalAccessException {
        DataDescription dataDescription;
        switch (dataType) {
            case ALBUM -> dataDescription = new Album();
            case MUSIC_BAND -> dataDescription = new MusicBand();
            case COORDINATES -> dataDescription = new Coordinates();
            default -> throw new RuntimeException(); //TODO ошибка
        }

        Field[] fields = dataDescription.getClass().getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);

            if (field.getType() == Coordinates.class) {
                if (args[i].isEmpty()) field.set(dataDescription, null); //TODO ошибка
                else {
                    field.set(
                            dataDescription,
                            DataFactory.formObject(DataTypes.COORDINATES, Arrays.copyOfRange(args, i, i + 2))
                    );
                    i += 2;
                }
                continue;
            }

            if (field.getType() == Album.class) {
                if (args[i].isEmpty()) field.set(dataDescription, null);
                else {
                    field.set(
                            dataDescription,
                            DataFactory.formObject(DataTypes.ALBUM, Arrays.copyOfRange(args, i, i + 4))
                    );
                    i += 4;
                }
                continue;
            }

            if (annotation.isGenerate())
                field.set(dataDescription, generate(annotation));
            else {
                field.set(dataDescription, validate(args[i], field, annotation));
                i++; //TODO ошибка
            }

        }

        return dataDescription;
    }

    private static Object generate(FieldAnnotation annotation) throws InstantiationException, IllegalAccessException {
        Generator generator = annotation.generator().newInstance();
        return generator.generate();
    }

    private static Object validate(String arg, Field field, FieldAnnotation annotation) {
        if (arg.isEmpty()) {
            if (annotation.nullable()) return null;
            throw new RuntimeException(); //TODO
        }
        Object argWithType = checkType(field.getType(), arg);
        if (annotation.isValidate() && !(checkMoreThen(argWithType, annotation.moreThen()))) {
            throw new RuntimeException(); //TODO
        }
        return argWithType;
    }

    private static Object checkType(Type type, String arg) {
        switch (type.getTypeName()) {
            case "java.lang.String" -> {
                return arg;
            }
            case "int", "java.lang.Integer" -> {
                try {
                    return Integer.parseInt(arg);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(); //TODO
                }
            }
            case "long", "java.lang.Long" -> {
                try {
                    return Long.parseLong(arg);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(); //TODO
                }
            }
            case "float", "java.lang.Float" -> {
                try {
                    return Float.parseFloat(arg);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(); //TODO
                }
            }
            case "Data.MusicGenre" -> {
                return MusicGenre.values()[Integer.parseInt(arg) - 1];
                //TODO
            }
            default -> throw new RuntimeException(); //TODO
        }
    }

    private static boolean checkMoreThen(Object arg, int moreThen) {
        switch (arg.getClass().getTypeName()) {
            case "java.lang.Integer" -> {
                return (int) arg > moreThen;
            }
            case "long", "java.lang.Long" -> {
                return (long) arg > (long) moreThen;
            }
            case "float", "java.lang.Float" -> {
                return (float) arg > (float) moreThen;
            }
            //TODO default
        }
        return false;
    }


}
