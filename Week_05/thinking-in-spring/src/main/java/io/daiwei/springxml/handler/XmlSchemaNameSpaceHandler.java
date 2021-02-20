package io.daiwei.springxml.handler;

import io.daiwei.springxml.parser.KlassBeanDefinitionParser;
import io.daiwei.springxml.parser.SchoolBeanDefinitionParser;
import io.daiwei.springxml.parser.StudentBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by Daiwei on 2021/2/14
 */
public class XmlSchemaNameSpaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("student", new StudentBeanDefinitionParser());
        registerBeanDefinitionParser("klass", new KlassBeanDefinitionParser());
        registerBeanDefinitionParser("school", new SchoolBeanDefinitionParser());
    }
}
