package org.spring;

import org.spring.bean.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 *@Author BieFeNg
 *@Date 2019/3/29 14:55
 *@DESC
 */
public class TestBeanPostProcesser implements ApplicationContextAware {


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Book book = (Book) applicationContext.getBean("book");
        book.setName("a");
    }
}
