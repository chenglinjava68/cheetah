package org.cheetah.commons.utils;

/**
 *数字工具
 * Created by Max on 2016/9/11.
 */
public final class Numbers {
    private static final Comparer equalsComparer;
    private static final Comparer greaterThanComparer;
    private static final Comparer greaterThanEqualsComparer;
    private static final Comparer lessThanComparer;
    private static final Comparer lessThanEqualsComparer;

    static {
        equalsComparer = new EqualsComparer();
        greaterThanComparer = new GreaterThanComparer();
        greaterThanEqualsComparer = new GreaterThanEqualsComparer();
        lessThanComparer = new LessThanComparer();
        lessThanEqualsComparer = new LessThanEqualsComparer();
    }

    /**
     * 是否大于
     * @param value
     * @param expect
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean isGt(T value, T expect) {
        return greaterThanComparer.compare(value, expect);
    }
    /**
     * 是否大于等于
     * @param value
     * @param expect
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean isGte(T value, T expect) {
        return greaterThanEqualsComparer.compare(value, expect);
    }
    /**
     * 是否等于
     * @param value
     * @param expect
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean isEquals(T value, T expect) {
        return equalsComparer.compare(value, expect);
    }
    /**
     * 是否小于
     * @param value
     * @param expect
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean isLt(T value, T expect) {
        return lessThanComparer.compare(value, expect);
    }
    /**
     * 是否小于等于
     * @param value
     * @param expect
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean isLte(T value, T expect) {
        return lessThanEqualsComparer.compare(value, expect);
    }

    /**
     * 数值比较器
     */
    static abstract class Comparer {
        <T extends Number> boolean compare(T value, T expect) {
            if (value.getClass().equals(int.class) || value.getClass().equals(Integer.class)) {
                if (doIntCompare((Integer) value, (Integer) expect))
                    return true;
                else
                    return false;
            } else if (value.getClass().equals(byte.class) || value.getClass().equals(Byte.class)) {
                if (doByteCompare((Byte) value, (Byte) expect))
                    return true;
                else
                    return false;
            } else if (value.getClass().equals(short.class) || value.getClass().equals(Short.class)) {
                if (doShortCompare((Short) value, (Short) expect))
                    return true;
                else
                    return false;
            } else if (value.getClass().equals(long.class) || value.getClass().equals(Long.class)) {
                if (doLongCompare((Long) value, (Long) expect))
                    return true;
                else
                    return false;
            } else if (value.getClass().equals(float.class) || value.getClass().equals(Float.class)) {
                if (doFloatCompare((Float) value, (Float) expect))
                    return true;
                else
                    return false;
            } else if (value.getClass().equals(double.class) || value.getClass().equals(Double.class)) {
                if (doDoubleCompare((Double) value, (Double) expect))
                    return true;
                else
                    return false;
            }
            return false;
        }

        protected abstract boolean doLongCompare(Long value, Long expect);

        protected abstract boolean doDoubleCompare(Double value, Double expect);

        protected abstract boolean doFloatCompare(Float value, Float expect);

        protected abstract boolean doShortCompare(Short value, Short expect);

        protected abstract boolean doByteCompare(Byte value, Byte expect);

        protected abstract boolean doIntCompare(Integer value, Integer expect);
    }

    /**
     * 大于比较器
     */
    static class GreaterThanComparer extends Comparer {

        @Override
        protected boolean doLongCompare(Long value, Long expect) {
            if (value > expect)
                return true;
            return false;
        }

        @Override
        protected boolean doDoubleCompare(Double value, Double expect) {
            if (value > expect)
                return true;
            return false;
        }

        @Override
        protected boolean doFloatCompare(Float value, Float expect) {
            if (value > expect)
                return true;
            return false;
        }

        @Override
        protected boolean doShortCompare(Short value, Short expect) {
            if (value > expect)
                return true;
            return false;
        }

        @Override
        protected boolean doByteCompare(Byte value, Byte expect) {
            if (value > expect)
                return true;
            return false;
        }

        @Override
        protected boolean doIntCompare(Integer value, Integer expect) {
            if (value > expect)
                return true;
            return false;
        }
    }

    /**
     * 大于等于比较器
     */
    static class GreaterThanEqualsComparer extends Comparer {

        @Override
        protected boolean doLongCompare(Long value, Long expect) {
            if (value >= expect)
                return true;
            return false;
        }

        @Override
        protected boolean doDoubleCompare(Double value, Double expect) {
            if (value >= expect)
                return true;
            return false;
        }

        @Override
        protected boolean doFloatCompare(Float value, Float expect) {
            if (value >= expect)
                return true;
            return false;
        }

        @Override
        protected boolean doShortCompare(Short value, Short expect) {
            if (value >= expect)
                return true;
            return false;
        }

        @Override
        protected boolean doByteCompare(Byte value, Byte expect) {
            if (value >= expect)
                return true;
            return false;
        }

        @Override
        protected boolean doIntCompare(Integer value, Integer expect) {
            if (value >= expect)
                return true;
            return false;
        }
    }

    /**
     * 小于比较器
     */
    static class LessThanComparer extends Comparer {

        @Override
        protected boolean doLongCompare(Long value, Long expect) {
            if (value < expect)
                return true;
            return false;
        }

        @Override
        protected boolean doDoubleCompare(Double value, Double expect) {
            if (value < expect)
                return true;
            return false;
        }

        @Override
        protected boolean doFloatCompare(Float value, Float expect) {
            if (value < expect)
                return true;
            return false;
        }

        @Override
        protected boolean doShortCompare(Short value, Short expect) {
            if (value < expect)
                return true;
            return false;
        }

        @Override
        protected boolean doByteCompare(Byte value, Byte expect) {
            if (value < expect)
                return true;
            return false;
        }

        @Override
        protected boolean doIntCompare(Integer value, Integer expect) {
            if (value < expect)
                return true;
            return false;
        }
    }

    /**
     * 小于等于比较器
     */
    static class LessThanEqualsComparer extends Comparer {

        @Override
        protected boolean doLongCompare(Long value, Long expect) {
            if (value <= expect)
                return true;
            return false;
        }

        @Override
        protected boolean doDoubleCompare(Double value, Double expect) {
            if (value <= expect)
                return true;
            return false;
        }

        @Override
        protected boolean doFloatCompare(Float value, Float expect) {
            if (value <= expect)
                return true;
            return false;
        }

        @Override
        protected boolean doShortCompare(Short value, Short expect) {
            if (value <= expect)
                return true;
            return false;
        }

        @Override
        protected boolean doByteCompare(Byte value, Byte expect) {
            if (value <= expect)
                return true;
            return false;
        }

        @Override
        protected boolean doIntCompare(Integer value, Integer expect) {
            if (value <= expect)
                return true;
            return false;
        }
    }


    /**
     * 等于比较器
     */
    static class EqualsComparer extends Comparer {

        @Override
        protected boolean doLongCompare(Long value, Long expect) {
            if (value.equals(expect))
                return true;
            return false;
        }

        @Override
        protected boolean doDoubleCompare(Double value, Double expect) {
            if (value.equals(expect))
                return true;
            return false;
        }

        @Override
        protected boolean doFloatCompare(Float value, Float expect) {
            if (value.equals(expect))
                return true;
            return false;
        }

        @Override
        protected boolean doShortCompare(Short value, Short expect) {
            if (value.equals(expect))
                return true;
            return false;
        }

        @Override
        protected boolean doByteCompare(Byte value, Byte expect) {
            if (value.equals(expect))
                return true;
            return false;
        }

        @Override
        protected boolean doIntCompare(Integer value, Integer expect) {
            if (value.equals(expect))
                return true;
            return false;
        }
    }
}
