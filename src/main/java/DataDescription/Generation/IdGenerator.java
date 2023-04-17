package DataDescription.Generation;

public class IdGenerator implements Generator {
    static int nextId = 1;

    @Override
    public Object generate() {
        nextId++;
        return nextId;
    }

    public static void setNextId(int id) {
        nextId = id;
    }
}
