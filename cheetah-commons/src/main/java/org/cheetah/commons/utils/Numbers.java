package org.cheetah.commons.utils;

/**
 * Created by Max on 2016/9/11.
 */
public class Numbers {
    public static <T extends Number> boolean isGt(T value, T expect) {
        if (value.getClass().equals(int.class) || value.getClass().equals(Integer.class)) {
            if (((Integer) value) > ((Integer) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(byte.class) || value.getClass().equals(Byte.class)) {
            if (((Byte) value) > ((Byte) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(short.class) || value.getClass().equals(Short.class)) {
            if (((Short) value) > ((Short) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(long.class) || value.getClass().equals(Long.class)) {
            if (((Long) value) > ((Long) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(float.class) || value.getClass().equals(Float.class)) {
            if (((Float) value) > ((Float) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(double.class) || value.getClass().equals(Double.class)) {
            if (((Double) value) > ((Double) expect))
                return true;
            else
                return false;
        }
        return false;
    }

    public static <T extends Number> boolean isGte(T value, T expect) {
        if (value.getClass().equals(int.class) || value.getClass().equals(Integer.class)) {
            if (((Integer) value) >= ((Integer) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(byte.class) || value.getClass().equals(Byte.class)) {
            if (((Byte) value) >= ((Byte) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(short.class) || value.getClass().equals(Short.class)) {
            if (((Short) value) >= ((Short) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(long.class) || value.getClass().equals(Long.class)) {
            if (((Long) value) >= ((Long) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(float.class) || value.getClass().equals(Float.class)) {
            if (((Float) value) >= ((Float) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(double.class) || value.getClass().equals(Double.class)) {
            if (((Double) value) >= ((Double) expect))
                return true;
            else
                return false;
        }
        return false;
    }

    public static <T extends Number> boolean isEquals(T value, T expect) {
        if (value.getClass().equals(int.class) || value.getClass().equals(Integer.class)) {
            if (value.equals(expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(byte.class) || value.getClass().equals(Byte.class)) {
            if (value.equals(expect))
                return true;
            else
                return false;

        } else if (value.getClass().equals(short.class) || value.getClass().equals(Short.class)) {
            if (value.equals(expect))
                return true;
            else
                return false;

        } else if (value.getClass().equals(long.class) || value.getClass().equals(Long.class)) {
            if (value.equals(expect))
                return true;
            else
                return false;

        } else if (value.getClass().equals(float.class) || value.getClass().equals(Float.class)) {
            if (value.equals(expect))
                return true;
            else
                return false;

        } else if (value.getClass().equals(double.class) || value.getClass().equals(Double.class)) {
            if (value.equals(expect))
                return true;
            else
                return false;

        }
        return false;
    }

    public static <T extends Number> boolean isLt(T value, T expect) {
        if (value.getClass().equals(int.class) || value.getClass().equals(Integer.class)) {
            if (((Integer) value) < ((Integer) expect))
                return true;
            else
                return false;

        } else if (value.getClass().equals(byte.class) || value.getClass().equals(Byte.class)) {
            if (((Byte) value) < ((Byte) expect))
                return true;
            else
                return false;

        } else if (value.getClass().equals(short.class) || value.getClass().equals(Short.class)) {
            if (((Short) value) < ((Short) expect))
                return true;
            else
                return false;

        } else if (value.getClass().equals(long.class) || value.getClass().equals(Long.class)) {
            if (((Long) value) < ((Long) expect))
                return true;
            else
                return false;

        } else if (value.getClass().equals(float.class) || value.getClass().equals(Float.class)) {
            if (((Float) value) < ((Float) expect))
                return true;
            else
                return false;

        } else if (value.getClass().equals(double.class) || value.getClass().equals(Double.class)) {
            if (((Double) value) < ((Double) expect))
                return true;
            else
                return false;

        }
        return false;
    }

    public static <T extends Number> boolean isLte(T value, T expect) {
        if (value.getClass().equals(int.class) || value.getClass().equals(Integer.class)) {
            if (((Integer) value) <= ((Integer) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(byte.class) || value.getClass().equals(Byte.class)) {
            if (((Byte) value) <= ((Byte) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(short.class) || value.getClass().equals(Short.class)) {
            if (((Short) value) <= ((Short) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(long.class) || value.getClass().equals(Long.class)) {
            if (((Long) value) <= ((Long) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(float.class) || value.getClass().equals(Float.class)) {
            if (((Float) value) <= ((Float) expect))
                return true;
            else
                return false;
        } else if (value.getClass().equals(double.class) || value.getClass().equals(Double.class)) {
            if (((Double) value) <= ((Double) expect))
                return true;
            else
                return false;
        }
        return false;
    }
}
