package org.cheetah.commons.excel;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maxhuang on 2016/6/24.
 */
public class ExcelTest {

    @Test
    public void test() throws FileNotFoundException {
        List<FX> list =  ExcelBaseProcessor.Sample.readExcel2ObjsByPath("D:\\对接人旗下艺人账号汇总.xlsx", FX.class
                , 0, 0, 0);
        List<Anchor> source =  ExcelBaseProcessor.Sample.readExcel2ObjsByPath("D:\\2016-06至2016-06-24 16-17-15--手机直播素人主播表.xls", Anchor.class
                , 0, 0, 0);

        System.out.println(source.size());

        List<Long> ls = list.stream().map(FX::getFxId).collect(Collectors.toList());

        source = source.stream().filter(o ->
            !ls.contains(o.getFxId())
        ).collect(Collectors.toList());
        System.out.println(source.size());


        ExcelBaseProcessor.Sample.exportObj2Excel(new FileOutputStream("D:/test.xls"), source, Anchor.class, false);

    }
}
