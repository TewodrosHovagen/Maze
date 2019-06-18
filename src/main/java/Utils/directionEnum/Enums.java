package Utils.directionEnum;

import java.util.List;
import java.util.Random;

public class Enums {

    public enum MainDirectionsEnum {
        UP, RIGHT, DOWN, LEFT, BOOKMARK;

        private static final List<MainDirectionsEnum> VALUES = List.of(values());
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static MainDirectionsEnum randomMainDirection() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }
}
