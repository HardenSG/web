package com.example.demo.service.impl;

import com.example.demo.entity.Dynamic;
import com.example.demo.service.DynamicService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.TableGenerator;

import static org.junit.Assert.*;

/**
 * @author Chu
 * @create 2022-03-18-15:35
 */
@SpringBootTest
public class DynamicServiceImplTest {
    @Autowired
    private DynamicService dynamicService;
   @Test
    void insertlike(){
       System.out.println(dynamicService.updateByColumn("like", 8, 8));
   }

}