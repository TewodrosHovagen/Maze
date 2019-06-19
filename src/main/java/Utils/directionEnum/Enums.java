package Utils.directionEnum;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Enums {

    public enum MainDirectionsEnum {
        UP, RIGHT, DOWN, LEFT, BOOKMARK;

        private static final List<MainDirectionsEnum> VALUES = List.of(values());

        private static final Random RANDOM = new Random();

        public static MainDirectionsEnum randomMainDirection() {
            return VALUES.get(RANDOM.nextInt(VALUES.size()-1));
        }

    }
}
