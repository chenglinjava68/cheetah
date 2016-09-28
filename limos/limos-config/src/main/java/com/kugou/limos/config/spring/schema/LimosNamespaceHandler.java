package com.kugou.limos.config.spring.schema;

import com.kugou.limos.config.spring.ReferenceConfigBean;
import com.kugou.limos.config.spring.ServiceConfigBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by Max on 2016/9/12.
 */
public class LimosNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("service", new LimosBeanDefinitionParser(ServiceConfigBean.class));
        registerBeanDefinitionParser("reference", new LimosBeanDefinitionParser(ReferenceConfigBean.class));
    }
}
