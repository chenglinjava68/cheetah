package org.cheetah.commons.excel.api;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by Max on 2016/6/25.
 */
public interface StyleHandler {
    void handle(Cell cell, int rowIndex);
}
