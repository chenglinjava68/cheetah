package org.cheetah.bootstrap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cheetah.commons.utils.ObjectMappers;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by maxhuang on 2016/7/20.
 */
public class MyMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    private final static ObjectMapper objectMapper =  ObjectMappers.newborn();

    public MyMappingJackson2HttpMessageConverter() {
        super(objectMapper);
    }
}
