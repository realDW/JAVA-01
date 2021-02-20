package io.daiwei.springxml.parser;

import io.daiwei.entity.Student;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

/**
 * Created by Daiwei on 2021/2/14
 */
@NoArgsConstructor
public class StudentBeanDefinitionParser extends BaseBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return Student.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        setAttr("stuName", element, builder);
        setAttr("stuAddr", element, builder);
        setAttr("stuId", element, builder);
    }


}
