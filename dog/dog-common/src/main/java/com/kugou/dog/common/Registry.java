package com.kugou.dog.common;

import java.lang.annotation.*;

/**
 * Created by Max on 2016/8/20.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Registry {
    /**
     * �ӿ���
     * @return
     */
    String value();

    /**
     * ������
     * @return
     */
    int executes() default 0;

    /**
     * �����ʣ���period��أ����ʹ��Ĭ�ϵ�period������1������������
     * @return
     */
    int throughput() default 0;

    /**
     * ��λΪ��
     * @return
     */
    int period() default 1;


}
