package com.kugou.limos.config.spring.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * spring¿©’π±Í«©Ω‚Œˆ∆˜
 * Created by Max on 2016/9/12.
 */
public class LimosBeanDefinitionParser implements BeanDefinitionParser {
    private final Class<?> beanClass;

    public LimosBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return null;
    }
}
