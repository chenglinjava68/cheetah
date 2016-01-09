package cheetah.domain;

import java.util.List;
import java.util.Locale;

/**
 * Created by Max on 2016/1/4.
 */
public class Sort {
    private List<String> properties;
    private Order order;

    public Sort(List<String> properties, Order order) {
        this.properties = properties;
        this.order = order;
    }

    public static enum Order {
        ASC,
        DESC;

        private Order() {
        }

        public static Sort.Order fromString(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.US));
            } catch (Exception var2) {
                throw new IllegalArgumentException(String.format("Invalid value \'%s\' for orders given! Has to be either \'desc\' or \'asc\' (case insensitive).", new Object[]{value}), var2);
            }
        }

        public static Sort.Order fromStringOrNull(String value) {
            try {
                return fromString(value);
            } catch (IllegalArgumentException var2) {
                return null;
            }
        }
    }
}
