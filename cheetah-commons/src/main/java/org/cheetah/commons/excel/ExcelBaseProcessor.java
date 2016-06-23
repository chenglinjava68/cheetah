package org.cheetah.commons.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Max
 * @date 2015/5/12
 */
public class ExcelBaseProcessor<T> extends ExcelProcessor<T> {
    public ExcelBaseProcessor() {
    }

    public ExcelBaseProcessor(File templateFile) throws IOException, InvalidFormatException {
        super(templateFile);
    }

    public ExcelBaseProcessor(InputStream templateInputStream) throws IOException, InvalidFormatException {
        super(templateInputStream);
    }

    /**
     * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到流
     *
     * @param datas 模板中的替换的常量数据
     * @param objs  对象列表
     * @param clz   对象的类型
     */
    public void exportObj2ExcelByTemplate(Map<String, String> datas, List<T> objs, Class<T> clz) {
        handlerObj2Excel(objs, clz);
        getExcelTemplate().replaceFinalData(datas);
    }

    /**
     * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到流,基于Properties作为常量数据
     *
     * @param prop 基于Properties的常量数据模型
     * @param objs 对象列表
     * @param clz  对象的类型
     */
    public void exportObj2ExcelByTemplate(Properties prop, List<T> objs, Class<T> clz) {
        handlerObj2Excel(objs, clz);
        getExcelTemplate().replaceFinalData(prop);
    }

    /**
     * 读取Excel转为List, 可控制头尾
     *
     * @param inputStream
     * @param clz
     * @param sheetIndex
     * @param readLine
     * @param tailLine
     * @return
     */
    private List<T> readExcel2ObjsByStream(InputStream inputStream, Class<T> clz, int sheetIndex,
                                           int readLine, int tailLine) {
        return handlerExcel2Objs(inputStream, clz, sheetIndex, readLine, tailLine);
    }

    /**
     * 读取Excel转为List
     *
     * @param inputStream
     * @param clz
     * @param sheetIndex
     * @return
     */
    private List<T> readExcel2ObjsByStream(InputStream inputStream, int sheetIndex, Class<T> clz) {
        return handlerExcel2Objs(inputStream, clz, sheetIndex, 0, 0);
    }

    /**
     * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于路径的导出
     *
     * @param outPath 导出路径
     * @param objs    对象列表
     * @param clz     对象类型
     * @param isXssf  是否是2007版本
     */
    private void exportObj2Excel(String outPath, List<T> objs, Class<T> clz,
                                 boolean isXssf) {
        Workbook wb = handlerObj2Excel(objs, clz, isXssf);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outPath);
            wb.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于流
     *
     * @param os     输出流
     * @param objs   对象列表
     * @param clz    对象类型
     * @param isXssf 是否是2007版本
     */
    private void exportObj2Excel(OutputStream os, List<T> objs, Class<T> clz,
                                 boolean isXssf) {
        try {
            Workbook wb = handlerObj2Excel(objs, clz, isXssf);
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从类路径读取相应的Excel文件到对象列表
     *
     * @param path     类路径下的path
     * @param clz      对象类型
     * @param readLine 开始行，注意是标题所在行
     * @param tailLine 底部有多少行，在读入对象时，会减去这些行
     * @return
     */
    private List<T> readExcel2ObjsByClasspath(String path, Class<T> clz, int sheetIndex,
                                              int readLine, int tailLine) {
        InputStream is = ExcelProcessor.class.getResourceAsStream(path);
        return handlerExcel2Objs(is, clz, sheetIndex, readLine, tailLine);
    }

    /**
     * 从文件路径读取相应的Excel文件到对象列表
     *
     * @param path     文件路径下的path
     * @param clz      对象类型
     * @param readLine 开始行，注意是标题所在行
     * @param tailLine 底部有多少行，在读入对象时，会减去这些行
     * @return
     */
    private List<T> readExcel2ObjsByPath(String path, Class<T> clz, int sheetIndex,
                                         int readLine, int tailLine) {
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            return handlerExcel2Objs(is, clz, sheetIndex, readLine, tailLine);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 从类路径读取相应的Excel文件到对象列表，标题行为0，没有尾行
     *
     * @param path 路径
     * @param clz  类型
     * @return 对象列表
     */
    private List<T> readExcel2ObjsByClasspath(String path, int sheetIndex, Class<T> clz) {
        return this.readExcel2ObjsByClasspath(path, clz, sheetIndex, 0, 0);
    }

    /**
     * 从文件路径读取相应的Excel文件到对象列表，标题行为0，没有尾行
     *
     * @param path 路径
     * @param clz  类型
     * @return 对象列表
     */
    private List<T> readExcel2ObjsByPath(String path, int sheetIndex, Class<T> clz) {
        return this.readExcel2ObjsByPath(path, clz, sheetIndex, 0, 0);
    }

    public static class Sample {
        private final static ExcelBaseProcessor baseRepository = new ExcelBaseProcessor();

        public static <T> List<T> readExcel2ObjsByStream(InputStream inputStream, Class<T> clz, int sheetIndex,
                                                         int readLine, int tailLine) {
            return baseRepository.readExcel2ObjsByStream(inputStream, clz, sheetIndex, readLine, tailLine);
        }

        public static <T> List<T> readExcel2ObjsByStream(InputStream inputStream, int sheetIndex, Class<T> clz) {
            return baseRepository.readExcel2ObjsByStream(inputStream, clz, sheetIndex, 0, 0);
        }

        public static <T> void exportObj2Excel(String outPath, List<T> objs, Class<T> clz,
                                               boolean isXssf) {
            baseRepository.exportObj2Excel(outPath, objs, clz, isXssf);
        }

        public static <T> void exportObj2Excel(OutputStream os, List<T> objs, Class<T> clz,
                                               boolean isXssf) {
            baseRepository.exportObj2Excel(os, objs, clz, isXssf);
        }

        public static <T> List<T> readExcel2ObjsByClasspath(String path, Class<T> clz, int sheetIndex,
                                                            int readLine, int tailLine) {
            return baseRepository.readExcel2ObjsByClasspath(path, clz, sheetIndex, readLine, tailLine);
        }

        public static <T> List<T> readExcel2ObjsByPath(String path, Class<T> clz, int sheetIndex,
                                                       int readLine, int tailLine) {
            return baseRepository.readExcel2ObjsByPath(path, clz, sheetIndex, readLine, tailLine);
        }

        public static <T> List<T> readExcel2ObjsByClasspath(String path, int sheetIndex, Class<T> clz) {
            return baseRepository.readExcel2ObjsByClasspath(path, clz, sheetIndex, 0, 0);
        }

        public static <T> List<T> readExcel2ObjsByPath(String path, int sheetIndex, Class<T> clz) {
            return baseRepository.readExcel2ObjsByPath(path, sheetIndex, clz);
        }
    }
}
