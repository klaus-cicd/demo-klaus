package com.silas.demo.service.impl;

import com.silas.demo.service.SpringAnimalService;
import org.springframework.stereotype.Service;

/**
 * @author Klaus
 * @date 2022/4/29
 * @description
 **/
@Service
public class SpringCatServiceImpl implements SpringAnimalService {
    @Override
    public void shut() {
        System.out.println("IOC cat: Miao!!!");
    }
}
