package com.prism.db;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;



public class PrismNamespaceHandler extends NamespaceHandlerSupport{
public void init() {
    	
        registerBeanDefinitionParser("prism", new PrismBeanDefinitionParser());
    }
}
