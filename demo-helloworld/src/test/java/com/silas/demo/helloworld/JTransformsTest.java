package com.silas.demo.helloworld;

import org.jtransforms.fft.DoubleFFT_1D;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Klaus
 */
@SpringBootTest
public class JTransformsTest {
    @Test
    void test() {
        // 定义一个实数数组
        double[] data = new double[]{1, 2, 3, 4, 5, 6, 7, 8};

        // 创建一个傅里叶变换对象
        DoubleFFT_1D fft = new DoubleFFT_1D(data.length);

        // 进行傅里叶变换
        fft.realForward(data);

        // 打印傅里叶变换结果
        for (double d : data) {
            System.out.println(d);
        }

    }
}
