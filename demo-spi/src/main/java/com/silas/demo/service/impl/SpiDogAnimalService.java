package com.silas.demo.service.impl;

import com.silas.demo.service.SpiAnimalService;

/**
 * @author Klaus
 * @date 2022/4/29
 * @description
 **/
public class SpiDogAnimalService implements SpiAnimalService {

    @Override
    public void shut() {
        System.out.println("Spi dog: Wang!!!");
    }
}
