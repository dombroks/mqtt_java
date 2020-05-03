package sample;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientPublish2 {

    private MqttClient client;
    private String brokerUrl;
    private MqttConnectOptions conOpt;
    private boolean clean;


    public ClientPublish2(String brokerUrl) throws MqttException {
        this.brokerUrl = brokerUrl;
        try {
            conOpt = new MqttConnectOptions();
            conOpt.setCleanSession(clean);
            client = new MqttClient(this.brokerUrl, "ID12");

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void publish(String topicName) throws MqttException {
        System.out.println("Connecter au broker# " + brokerUrl + " avec client id#" + client.getClientId());
        client.connect(conOpt);
        System.out.println("Connected");

        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));

        String ligne = null;

        do {
            try {
                System.out.println("Entrer votre texte:");
                ligne = entree.readLine();
                MqttMessage message = new MqttMessage(ligne.getBytes());
                client.publish(topicName, message);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } while (!ligne.equals("exit"));
        client.disconnect();
    }

    public void connectionLost(Throwable cause) {
        System.out.println("Connection to " + brokerUrl + " lost!" + cause);
        System.exit(1);
    }

    public void state(String topicName, String msg, int QOS) {

        try {
            client.connect(conOpt);
            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(QOS);
            // message.setRetained(true);
            client.publish(topicName, message);
            while (!msg.equals("exit")) {
                client.disconnect();
            }
        } catch (MqttException e) {
            System.out.println("Sending the state ...");
        }


    }

}
