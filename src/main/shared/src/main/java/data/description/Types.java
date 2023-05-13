package data.description;

import data.MusicGenre;

/**
 * Типы простых аргументов команд и их валидация
 */
public enum Types {
    STRING(new String[]{"java.lang.String"}) {
        @Override
        public Object validateType(String input) {
            return input;
        }

        @Override
        public boolean checkMoreThen(Object input, int moreThen) {
            return false;
        }
    },
    INTEGER(new String[]{"int", "java.lang.Integer"}) {
        @Override
        public Object validateType(String input) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        @Override
        public boolean checkMoreThen(Object input, int moreThen) {
            return ((int) input < moreThen);
        }
    },
    LONG(new String[]{"long", "java.lang.Long"}) {
        @Override
        public Object validateType(String input) {
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        @Override
        public boolean checkMoreThen(Object input, int moreThen) {
            return ((long) input < (long) moreThen);
        }
    },
    FLOAT(new String[]{"float", "java.lang.Float"}) {
        @Override
        public Object validateType(String input) {
            try {
                return Float.parseFloat(input);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        @Override
        public boolean checkMoreThen(Object input, int moreThen) {
            return ((float) input < (float) moreThen);
        }
    },
    MUSIC_GENRE(new String[]{"data.MusicGenre"}) {
        @Override
        public Object validateType(String input) {
            try {
                return MusicGenre.values()[Integer.parseInt(input) - 1];
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                return null;
            }
        }

        @Override
        public boolean checkMoreThen(Object input, int moreThen) {
            return false;
        }
    };

    final String[] typeNames;

    Types(String[] typeNames) {
        this.typeNames = typeNames;
    }


    public static Types getTypeByName(String typeName) {
        for (Types types : Types.values()) {
            for (String name : types.typeNames) {
                if (name.equals(typeName)) return types;
            }
        }
        throw new RuntimeException("Что-то пошло не так при преобразовании типов");
    }

    public abstract Object validateType(String input);

    public abstract boolean checkMoreThen(Object input, int moreThen);
}
