package org.cheetah.commons.excel.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Max on 2016/6/25.
 */
public interface ExcelProcessor<T> {

    List<T> read(InputStream inputStream, Class<T> clz, int sheetIndex,
                 int readLine, int tailLine);

    void write(List<T> datas, Class<T> clz);

    void addStyleHandler(StyleHandler styleHandler);

    void export(OutputStream desc);

    void export(String descPath);
}
