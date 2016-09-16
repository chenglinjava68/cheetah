package org.cheetah.common.excel;

import com.google.common.collect.Lists;
import org.cheetah.commons.excel.Assembly;
import org.cheetah.commons.excel.ExcelTranslator;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by maxhuang on 2016/6/24.
 */
public class ExcelTest {

    @Test
    public void test() throws FileNotFoundException {
//        List<FX> list =  ExcelBaseProcessor.Sample.readExcel2ObjsByPath("D:\\对接人旗下艺人账号汇总.xlsx", FX.class
//                , 0, 0, 0);
//        List<Anchor> source =  ExcelBaseProcessor.Sample.readExcel2ObjsByPath("D:\\2016-06至2016-06-24 16-17-15--手机直播素人主播表.xls", Anchor.class
//                , 0, 0, 0);
//
//        System.out.println(source.size());
//
//        List<Long> ls = list.stream().map(FX::getFxId).collect(Collectors.toList());
//
//        source = source.stream().filter(o ->
//            !ls.contains(o.getFxId())
//        ).collect(Collectors.toList());
//        System.out.println(source.size());
//
//
//        ExcelBaseProcessor.Sample.exportObj2Excel(new FileOutputStream("D:/test.xls"), source, Anchor.class, false);

    }

    /**
     * 复制文档
     * @throws FileNotFoundException
     */
    @Test
    public void test22() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        System.out.println(start);
        ExcelTranslator translator = ExcelTranslator.create();
        List<Anchor> anchors = translator.translate("D:\\test.xlsx", Anchor.class);
        for (Anchor anchor : anchors) {
            System.out.println(anchor);
        }

        List<Anchor> na = Lists.newArrayList();
        for(int i =0; i< 50000; i++) {
            na.addAll(anchors);
        }

        Assembly<Anchor> t = Assembly.<Anchor>newBuilder()
                .toStream(new FileOutputStream("d:/test2.xls"))
                .data(na)
                .entity(Anchor.class)
                .build();
        translator.translate(t);
    }

    /**
     * 基于模板的导出
     * @throws FileNotFoundException
     */
    @Test
    public void templateTest() throws FileNotFoundException {
        InputStream stream = new FileInputStream("D:\\excel_template_v2.xlsx");
        ExcelTranslator translator = ExcelTranslator.create();
        long start = System.currentTimeMillis();
        List<Anchor> anchors = Lists.newArrayList();
        for(int i = 0; i< 5000; i++) {
            Anchor anchor = new Anchor();
            anchor.setFxId(123);
            anchor.setLiveRoom(123);
            anchor.setName("name");

            anchors.add(anchor);

        }
        System.out.println(System.currentTimeMillis() - start);
        translator.translate(Assembly.<Anchor>newBuilder().toStream(new FileOutputStream("d:/test_template.xlsx"))
                .entity(Anchor.class)
                .templateStream(stream)
                .data(anchors)
                .build()
        );
        System.out.println(System.currentTimeMillis() - start);
    }
}
