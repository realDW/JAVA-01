package io.daiwei.springxml.parser;

import io.daiwei.entity.DefaultKlassImpl;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

/**
 * Created by Daiwei on 2021/2/15
 */
public class KlassBeanDefinitionParser extends BaseBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return DefaultKlassImpl.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        setAttr("grade", element, builder);
        setAttr("klassNo", element, builder);
        setAttr("monitor", element, builder);
        setReferenceAttr("monitor", element, builder);
    }
}
