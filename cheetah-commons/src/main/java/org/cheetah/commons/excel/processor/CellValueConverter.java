package org.cheetah.commons.excel.processor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.cheetah.commons.excel.ExcelHeader;
import org.cheetah.commons.utils.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Max on 2016/7/10.
 */
public final class CellValueConverter {

    public static void setValue(Cell cell, Object obj, ExcelHeader header) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        if (header.getType().equals(int.class) || header.getType().equals(Integer.class)) {
            int value = getInt(obj, ExcelResourcesHelper.getTargetName(header));
            cell.setCellValue(value);
        } else if (header.getType().equals(byte.class) || header.getType().equals(Byte.class)) {
            byte value = getByte(obj, ExcelResourcesHelper.getTargetName(header));
            cell.setCellValue(value);
        } else if (header.getType().equals(short.class) || header.getType().equals(Short.class)) {
            short value = getShort(obj, ExcelResourcesHelper.getTargetName(header));
            cell.setCellValue(value);
        } else if (header.getType().equals(long.class) || header.getType().equals(Long.class)) {
            long value = getLong(obj, ExcelResourcesHelper.getTargetName(header));
            cell.setCellValue(value);
        } else if (header.getType().equals(float.class) || header.getType().equals(Float.class)) {
            float value = getFloat(obj, ExcelResourcesHelper.getTargetName(header));
            cell.setCellValue(value);
        } else if (header.getType().equals(double.class) || header.getType().equals(Double.class)) {
            double value = getDouble(obj, ExcelResourcesHelper.getTargetName(header));
            cell.setCellValue(value);
        } else if (header.getType().equals(char.class) || header.getType().equals(Character.class)) {
            char value = getChar(obj, ExcelResourcesHelper.getTargetName(header));
            cell.setCellValue(value);
        } else if (header.getType().equals(Date.class)) {
            Date value = getDate(obj, ExcelResourcesHelper.getTargetName(header));
            if(Objects.isNull(value))
                return;
            String dateTime = LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            cell.setCellValue(dateTime);
        } else {
            cell.setCellValue(getString(obj, ExcelResourcesHelper.getTargetName(header)));
        }
    }

    /**
     * @param obj
     * @param field
     * @return
     */
    static String getString(Object obj, String field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Assert.notNull(obj, "obj must not be null");
        Assert.notBlank(field, "field must not be null or empty");
        return BeanUtils.getProperty(obj, field);
    }

    /**
     * @param obj
     * @param field
     * @return
     */
    static int getInt(Object obj, String field) throws IllegalAccessException {
        Assert.notNull(obj, "obj must not be null");
        Assert.notBlank(field, "field must not be null or empty");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals(field)) {
                if (!f.isAccessible())
                    f.setAccessible(true);
                return f.getInt(obj);
            }
        }
        return 0;
    }

    /**
     * @param obj
     * @param field
     * @return
     */
    static double getDouble(Object obj, String field) throws IllegalAccessException {
        Assert.notNull(obj, "obj must not be null");
        Assert.notBlank(field, "field must not be null or empty");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals(field)) {
                if (!f.isAccessible())
                    f.setAccessible(true);
                return f.getDouble(obj);
            }
        }
        return 0.0;
    }

    /**
     * @param obj
     * @param field
     * @return
     */
    static byte getByte(Object obj, String field) throws IllegalAccessException {
        Assert.notNull(obj, "obj must not be null");
        Assert.notBlank(field, "field must not be null or empty");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals(field)) {
                if (!f.isAccessible())
                    f.setAccessible(true);
                return f.getByte(obj);
            }
        }
        return 0;
    }


    /**
     * @param obj
     * @param field
     * @return
     */
    static float getFloat(Object obj, String field) throws IllegalAccessException {
        Assert.notNull(obj, "obj must not be null");
        Assert.notBlank(field, "field must not be null or empty");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals(field)) {
                if (!f.isAccessible())
                    f.setAccessible(true);
                return f.getFloat(obj);
            }
        }
        return 0;
    }

    /**
     * @param obj
     * @param field
     * @return
     */
    static char getChar(Object obj, String field) throws IllegalAccessException {
        Assert.notNull(obj, "obj must not be null");
        Assert.notBlank(field, "field must not be null or empty");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals(field)) {
                if (!f.isAccessible())
                    f.setAccessible(true);
                return f.getChar(obj);
            }
        }
        return 0;
    }

    /**
     * @param obj
     * @param field
     * @return
     */
    static long getLong(Object obj, String field) throws IllegalAccessException {
        Assert.notNull(obj, "obj must not be null");
        Assert.notBlank(field, "field must not be null or empty");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals(field)) {
                if (!f.isAccessible())
                    f.setAccessible(true);
                return f.getLong(obj);
            }
        }
        return 0;
    }

    /**
     * @param obj
     * @param field
     * @return
     */
    static short getShort(Object obj, String field) throws IllegalAccessException {
        Assert.notNull(obj, "obj must not be null");
        Assert.notBlank(field, "field must not be null or empty");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals(field)) {
                if (!f.isAccessible())
                    f.setAccessible(true);
                return f.getShort(obj);
            }
        }
        return 0;
    }

    /**
     * @param obj
     * @param field
     * @return
     */
    static Date getDate(Object obj, String field) throws IllegalAccessException {
        Assert.notNull(obj, "obj must not be null");
        Assert.notBlank(field, "field must not be null or empty");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals(field)) {
                if (!f.isAccessible())
                    f.setAccessible(true);
                return (Date) f.get(obj);
            }
        }
        return null;
    }
}
