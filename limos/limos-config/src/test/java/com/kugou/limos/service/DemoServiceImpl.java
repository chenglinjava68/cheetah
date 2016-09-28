package com.kugou.limos.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.URI;
import java.util.List;

/**
 * Created by Max on 2016/7/26.
 */
public class DemoServiceImpl implements DemoService,FactoryBean,InitializingBean {
    @Override
    public String get() {
        return "test";
    }

    @Override
    public List<String> list() {

        return null;
    }

    @Override
    public Integer get(String name) {
        return 123;
    }

    @Override
    public Object getObject() throws Exception {
        return new DemoServiceImpl();
    }

    @Override
    public Class<?> getObjectType() {
        return DemoService.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
