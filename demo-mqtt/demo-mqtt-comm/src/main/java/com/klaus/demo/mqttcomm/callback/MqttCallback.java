package com.klaus.demo.mqttcomm.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Klaus
 */
public class MqttCallback implements org.eclipse.paho.client.mqttv3.MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
