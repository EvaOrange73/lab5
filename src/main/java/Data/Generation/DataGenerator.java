package Data.Generation;

import java.util.Date;

public class DataGenerator implements Generator {
    @Override
    public Object generate() {
        return new Date();
    }
}