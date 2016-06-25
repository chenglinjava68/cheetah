package org.cheetah.commons.excel.support;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.cheetah.commons.excel.ExcelHeader;
import org.cheetah.commons.excel.annotation.ExcelTitle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/6/25.
 */
public final class ExcelResourcesHelper {

    public static Map<Integer, String> getHeaderMap(Row titleRow, Class clz) {
        List<ExcelHeader> headers = getHeaderList(clz);
        Map<Integer, String> maps = new HashMap<>();
        for (Cell c : titleRow) {
            String title = c.getStringCellValue();
            for (ExcelHeader eh : headers) {
                if (eh.getTitle().trim().equals(title.trim())) {
                    maps.put(c.getColumnIndex(),
                            eh.getTargetName().replace("get", "set"));
                    break;
                }
            }
        }
        return maps;
    }

    public static List<ExcelHeader> getHeaderList(Class clz) {
        List<ExcelHeader> headers = new ArrayList<>();
        Method[] ms = clz.getDeclaredMethods();
        for (Method m : ms) {
            String mn = m.getName();
            if (mn.startsWith("get")) {
                if (m.isAnnotationPresent(ExcelTitle.class)) {
                    ExcelTitle er = m.getAnnotation(ExcelTitle.class);
                    headers.add(new ExcelHeader(er.title(), er.order(), mn));
                }
            }
        }

        Field[] fs = clz.getDeclaredFields();
        for (Field f : fs) {
            if (f.isAnnotationPresent(ExcelTitle.class)) {
                ExcelTitle er = f.getAnnotation(ExcelTitle.class);
                headers.add(new ExcelHeader(er.title(), er.order(), f.getName()));
            }
        }
        return headers;
    }

    /**
     * 根据标题获取相应的方法名称
     *
     * @param eh
     * @return
     */
    public static String getMethodName(ExcelHeader eh) {
        String mn = eh.getTargetName().substring(3);
        mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
        return mn;
    }

}
