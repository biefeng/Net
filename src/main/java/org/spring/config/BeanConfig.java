package org.spring.config;
/*
 *@Author BieFeNg
 *@Date 2019/3/29 14:55
 *@DESC
 */

import org.spring.bean.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig implements BeanPostProcessor {

    @Bean
    public Book book() {
        Book b = new Book();
        b.setName("Nico");

        return b;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("the bean has bean created");
        return null;
    }
}
