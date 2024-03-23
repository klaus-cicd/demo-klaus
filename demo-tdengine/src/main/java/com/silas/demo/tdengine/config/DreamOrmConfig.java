package com.silas.demo.tdengine.config;

import com.dream.drive.listener.DebugListener;
import com.dream.system.core.listener.Listener;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.resultsethandler.SimpleResultSetHandler;
import com.dream.system.core.session.Session;
import com.dream.tdengine.mapper.DefaultFlexTdMapper;
import com.dream.tdengine.mapper.FlexTdMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Klaus
 */
@Configuration
public class DreamOrmConfig {

    /**
     * 配置SQL输出
     *
     * @return
     */
    @Bean
    public Listener[] listeners() {
        return new Listener[]{new DebugListener()};
    }

    @Bean
    public FlexTdMapper flexTdMapper(Session session) {
        return new DefaultFlexTdMapper(session);
    }

    @Bean
    public ResultSetHandler resultSetHandler() {
        return new SimpleResultSetHandler();
    }
}
