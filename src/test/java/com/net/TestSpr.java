package com.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.bean.Book;
import org.spring.config.BeanConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;

/*
 *@Author BieFeNg
 *@Date 2019/3/29 16:02
 *@DESC
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeanConfig.class)
public class TestSpr {

    @Autowired
    private ApplicationContext applicationContext;


    @Test
    public void test1(){
        Book book = (Book) applicationContext.getBean("book");
        System.out.println(book.getName());
    }


}
