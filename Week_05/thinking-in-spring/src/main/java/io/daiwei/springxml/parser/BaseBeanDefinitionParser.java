package io.daiwei.springxml.parser;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * BaseBeanDefinitionParser with some common methods
 * Created by Daiwei on 2021/2/14
 */
public class BaseBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    protected void setAttr(String attrKey, Element element, BeanDefinitionBuilder builder) {
        String attrValue = element.getAttribute(attrKey);
        if (StringUtils.hasText(attrValue)) {
            builder.addPropertyValue(attrKey, attrValue);
        }
    }

    protected void setReferenceAttr(String refAttrKey, Element element, BeanDefinitionBuilder builder) {
        String attrValue = element.getAttribute(refAttrKey);
        if (StringUtils.hasText(attrValue)) {
            builder.addPropertyReference(refAttrKey, attrValue);
        }
    }
}
