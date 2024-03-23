package com.silas.demo.ws.constant;

/**
 * MQ常量
 *
 * @author Klaus
 */
public class MQConstant {

    public static final String TOPIC_EXCHANGE = "MQ_EXCHANGE_TOPIC";
    public static final String FANOUT_EXCHANGE = "MQ_EXCHANGE_FANOUT";
    public static final String QUEUE_A = "MQ_QUEUE_A";
    public static final String QUEUE_B = "MQ_QUEUE_B";
    public static final String RETRY_ROUTING_KEY_PREFIX = "http_retry.{}";
    public static final String RETRY_ROUTING_KEY = "http_retry.#";

}
