package com.klaus.demo.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * BEAN模式（方法形式）
 *
 * @author klaus
 * @date 2024/01/02
 */
@Component
@Slf4j
public class MethodJobDemo {

    private int countA;
    private int countB;
    private int countC;

    @XxlJob(value = "xxl-job-demo-a", init = "init", destroy = "destroy")
    public void testJobA() {
        log.info("DemoJob#testJobA exdcute, count:{}", ++countA);
    }

    @XxlJob(value = "xxl-job-demo-b")
    public void testJobB() {
        log.info("DemoJob#testJobB exdcute, count:{}", ++countB);
    }

    @XxlJob(value = "xxl-job-demo-c1")
    public void testJobC1() {
        log.info("DemoJob#testJobC1 exdcute, count:{}", ++countC);
    }

    @XxlJob(value = "xxl-job-demo-c2")
    public void testJobC2() {
        log.info("DemoJob#testJobC2 exdcute, count:{}", ++countC);
    }

    public void init(){
        log.info("DemoJob init......");
    }

    public void destroy(){
        log.info("DemoJob destroy......");
    }
}
