package org.cheetah.bootstrap.service;

import java.util.List;

/**
 * Created by Max on 2016/7/26.
 */
public interface DemoService {
    String get();

    List<String> list();

    Integer get(String name);
}
