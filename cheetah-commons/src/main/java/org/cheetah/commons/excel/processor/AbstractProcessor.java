package org.cheetah.commons.excel.processor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.cheetah.commons.ExcelProcessor;
import org.cheetah.commons.excel.ExcelException;

import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/6/25.
 */
public abstract class AbstractProcessor<T> implements ExcelProcessor<T> {
    protected Workbook workbook;
    protected Sheet sheet;
    protected boolean isXssf;

    public AbstractProcessor() {
    }

    public AbstractProcessor(boolean isXssf) {
        this.isXssf = isXssf;
    }

    @Override
    public List<T> read(InputStream inputStream, Class<T> clz, int sheetIndex,
                        int readLine, int tailLine) {
        List<T> datas;
        try {
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(sheetIndex);
            Row row = sheet.getRow(readLine);
            datas = new ArrayList<>();
            Map<Integer, String> maps = ExcelResourcesHelper.getHeaderMap(row, clz);
            if (maps == null || maps.size() <= 0)
                throw new ExcelException("要读取的Excel的格式不正确，检查是否设定了合适的行");
            for (int i = readLine + 1; i <= sheet.getLastRowNum() - tailLine; i++) {
                row = sheet.getRow(i);
                if (row == null) break;
                T obj = clz.newInstance();
                for (Cell c : row) {
                    int ci = c.getColumnIndex();
                    if (!maps.containsKey(ci))
                        break;
                    String mn = maps.get(ci).substring(3);
                    mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
                    BeanUtils.copyProperty(obj, mn, this.getCellValue(c));
                }
                datas.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcelException(e);
        }
        return datas;
    }

    protected String getCellValue(Cell c) {
        String o;
        switch (c.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                o = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                o = String.valueOf(c.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                o = String.valueOf(c.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(c)) {
                    double d = c.getNumericCellValue();
                    Date date = HSSFDateUtil.getJavaDate(d);
                    SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    o = dformat.format(date);
                } else {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setGroupingUsed(false);// true时的格式：1,234,567,890
                    o = nf.format(c.getNumericCellValue());// 数值类型的数据为double，所以需要转换一下
                }
                break;
            case Cell.CELL_TYPE_STRING:
                o = c.getStringCellValue();
                break;
            default:
                o = null;
                break;
        }
        return o;
    }
}
