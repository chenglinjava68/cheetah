package org.cheetah.bootstrap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cheetah.commons.utils.ObjectMappers;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;

/**
 * Created by maxhuang on 2016/7/20.
 */
public class MyMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    private final static ObjectMapper objectMapper =  ObjectMappers.newborn();

    public MyMappingJackson2HttpMessageConverter() {
        super(objectMapper);
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        super.writeInternal(object, outputMessage);
    }
}
