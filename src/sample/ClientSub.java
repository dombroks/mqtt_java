package sample;

import org.eclipse.paho.client.mqttv3.*;

public class ClientSub implements MqttCallback {
    private String msg;
    private MqttClient client;
    private String brokerUrl;
    private MqttConnectOptions opt;
    private boolean clean;

    public ClientSub(String brokerUrl, String clientId) throws MqttException {
        this.brokerUrl = brokerUrl;
        try {
            opt = new MqttConnectOptions();
            opt.setCleanSession(clean);
            client = new MqttClient(this.brokerUrl, clientId);
            client.setCallback(this);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void subscribe(String topicName) throws MqttException {
        msg = "Listening on " + topicName.toUpperCase() + " topic\n";
        client.connect(opt);
        msg = msg + "Connected with# " + brokerUrl + " broker , ID : " + client.getClientId();
        client.subscribe(topicName);
        System.out.println();
        client.disconnect();
    }

    public void connectionLost(Throwable cause) {
        System.exit(1);
    }

    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        MqttMessage me = new MqttMessage(message.getPayload());
        int qos = message.getQos();
        // System.out.println("Message received " + me + " qos#" + qos);
        msg = msg + " qos#" + qos + "\n" + me.toString();
    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {

    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }


}
