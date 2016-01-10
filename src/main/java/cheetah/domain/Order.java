package cheetah.domain;

import java.util.Locale;

/**
 * Created by Max on 2016/1/4.
 */
public class Order {
    private String property;
    private Direction direction = Direction.ASC;

    public Order(String property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    public String getproperty() {
        return property;
    }

    public Direction getDirection() {
        return direction;
    }

    public static enum Direction {
        ASC,
        DESC;

        private Direction() {
        }

        public static Direction fromString(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.US));
            } catch (Exception var2) {
                throw new IllegalArgumentException(String.format("Invalid value \'%s\' for orders given! Has to be either \'desc\' or \'asc\' (case insensitive).", new Object[]{value}), var2);
            }
        }

        public static Direction fromStringOrNull(String value) {
            try {
                return fromString(value);
            } catch (IllegalArgumentException var2) {
                return null;
            }
        }
    }
}
