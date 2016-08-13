package org.cheetah.bootstrap.service;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Max on 2016/7/26.
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String get() {
        return "test";
    }

    @Override
    public List<String> list() {
        return Lists.newArrayList("a", "b");
    }

    @Override
    public Integer get(String name) {
        return 123;
    }
}
