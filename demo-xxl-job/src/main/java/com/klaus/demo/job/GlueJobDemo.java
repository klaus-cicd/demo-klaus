package com.klaus.demo.job;

import com.xxl.job.core.handler.IJobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * GLUE模式(Java)
 *
 * @author klaus
 * @date 2024/01/02
 */
@Slf4j
@Component
public class GlueJobDemo extends IJobHandler {


    @Override
    public void execute() throws Exception {
        // XxlJobExecutor.registJobHandler("GlueJobDemo", this);
        log.info("GlueJobDemo#execute");
    }

    @Override
    public void init() throws Exception {
        log.info("init");
    }

    @Override
    public void destroy() throws Exception {
        log.info("destroy");
    }
}
