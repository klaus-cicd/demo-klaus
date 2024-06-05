package com.klaus.demo.mqttcomm.manager;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Klaus
 */
@Slf4j
public class MqttClientManager {

    private static final Map<String, MqttClient> MQTT_CLIENTS = new ConcurrentHashMap<>();


    public static void addMqttClient(String clientId, MqttClient mqttClient) {
        MQTT_CLIENTS.put(clientId, mqttClient);
    }


    public static void removeMqttClient(String clientId) {
        MqttClient mqttClient = MQTT_CLIENTS.get(clientId);
        if (mqttClient != null) {
            try {
                mqttClient.close();
            } catch (MqttException e) {
                log.error("MqttClientManager#Close mqtt client error, client id: {}", clientId, e);
            }
        }
        MQTT_CLIENTS.remove(clientId);
    }


    public static MqttClient getMqttClient(String clientId) {
        return MQTT_CLIENTS.get(clientId);
    }
}
