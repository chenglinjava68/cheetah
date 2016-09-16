package org.cheetah.commons.excel.processor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.cheetah.commons.excel.ExcelHeader;
import org.cheetah.commons.excel.annotation.ExcelResources;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/6/25.
 */
final class ExcelResourcesHelper {

    static Map<Integer, String> getHeaderMap(Row titleRow, Class clz) {
        List<ExcelHeader> headers = getHeaderList(clz);
        Map<Integer, String> maps = new HashMap<>();
        for (Cell c : titleRow) {
            String title = c.getStringCellValue();
            for (ExcelHeader eh : headers) {
                if (eh.getTitle().trim().equals(title.trim())) {
                    maps.put(c.getColumnIndex(),
                            eh.getTargetName().replace("get", "setValue"));
                    break;
                }
            }
        }
        return maps;
    }

    static List<ExcelHeader> getHeaderList(Class clz) {
        List<ExcelHeader> headers = new ArrayList<>();
        Method[] ms = clz.getDeclaredMethods();
        for (Method m : ms) {
            String mn = m.getName();
            if (mn.startsWith("get")) {
                if (m.isAnnotationPresent(ExcelResources.class)) {
                    ExcelResources er = m.getAnnotation(ExcelResources.class);
                    Type type = m.getGenericReturnType();
                    ExcelHeader header = new ExcelHeader(er.title(), er.order(), mn, type);
                    if(!headers.contains(header))
                        headers.add(header);
                }
            }
        }

        Field[] fs = clz.getDeclaredFields();
        for (Field f : fs) {
            if (f.isAnnotationPresent(ExcelResources.class)) {
                ExcelResources er = f.getAnnotation(ExcelResources.class);
                Type type = f.getGenericType();
                ExcelHeader header = new ExcelHeader(er.title(), er.order(), f.getName(), type);
                if(!headers.contains(header))
                    headers.add(header);
            }
        }
        return headers;
    }

    /**
     * 根据标题获取相应的方法名称或者字段名
     *
     * @param eh
     * @return
     */
    static String getTargetName(ExcelHeader eh) {
        if(eh.targetNameisField())
            return eh.getTargetName();
        String mn = eh.getTargetName().substring(3);
        mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
        return mn;
    }


}
