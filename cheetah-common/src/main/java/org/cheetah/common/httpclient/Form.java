package org.cheetah.common.httpclient;

import com.google.common.collect.ImmutableMap;
import org.cheetah.common.utils.Assert;

import java.util.Map;

/**
 * form表单模型
 * Created by maxhuang on 2016/7/8.
 */
public class Form {
    private ImmutableMap<String, String> parameters = ImmutableMap.of();

    Form() {
    }

    Form(ImmutableMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public Form parameter(String name, String value) {
        Assert.notBlank(name, "name must not be null or empty");
        Assert.notBlank(value, "value must not be null or empty");
        ImmutableMap<String, String> newparameters = ImmutableMap.<String, String>builder().putAll(parameters).put(name, value).build();
        return new Form(newparameters);
    }

    public Form parameters(Map<String, String> $parameters) {
        Assert.notNull($parameters, "$parameters must not be null");
        ImmutableMap<String, String> newparameters = ImmutableMap.<String, String>builder().putAll(this.parameters).putAll($parameters).build();
        return new Form(newparameters);
    }

    public ImmutableMap<String, String> parameters() {
        return parameters;
    }

    public static Form create() {
        return new Form();
    }
}
