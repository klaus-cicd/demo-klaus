package com.klaus.demo.redis.subscribe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * 使用redis 缓存失效监听会有一定的延迟， 过期事件是在redis服务器删除键的时候生成的，而不是在理论上生存时间到达0值得时候生成的，
 *
 * @author klaus
 * @date 2024/06/05
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     *
     * @param message key的信息，并不包含缓存值。
     * @param pattern 模式
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 获得失效的key
        log.info("过期的Redis key：{}", new String(message.getBody()));

        // TODO 后续的业务处理
    }
}
