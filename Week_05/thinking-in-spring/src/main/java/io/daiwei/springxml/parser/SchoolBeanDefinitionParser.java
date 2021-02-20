package io.daiwei.springxml.parser;

import io.daiwei.entity.DefaultSchoolImpl;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

/**
 * Created by Daiwei on 2021/2/18
 */
public class SchoolBeanDefinitionParser extends BaseBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return DefaultSchoolImpl.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        setAttr("schoolName", element, builder);
        setReferenceAttr("klassList", element, builder);
    }
}
