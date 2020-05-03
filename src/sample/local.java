package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

public class local extends Application {
    int Port = 1883;
    String Broker = "localhost";
    String Protocol = "tcp";
    String url = Protocol + "://" + Broker + ":" + Port;
    TextField qos;
    TextField topicField;
    TextField topicFieldSUBSCRIBE;
    Button subButton;
    TextArea Screen;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

//Instantiate buttons,text fields + add jobs to components----------------------------------------
        Button button1 = new Button("Robot 1");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Send("Robot 1 turned the heat");
            }
        });
        Button button2 = new Button("Agent 1");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Send("Agent 1 is On Move");
            }
        });
        Button button3 = new Button("Agent 2");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Send("Robot 2 is On Move");
            }
        });
        Button button4 = new Button("Robot 2");
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Send("Robot 2 opened the window");
            }
        });
        Button button5 = new Button("Maps");
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Send("Map : Robot 1 is far one meter away " +
                        "Robot 2 is far theree meter away" + "Agent 1 and Agent 2 are in front of door");
            }
        });
        Button button6 = new Button("Captor");
        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Send("Captor : I catched something");
            }
        });
        subButton = new Button("Subscribe");
        subButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String TOPIC = topicFieldSUBSCRIBE.getText();
                try {
                    ClientSub sub = new ClientSub(url, "45");
                    sub.subscribe(TOPIC);
                    Screen.setText(sub.getMsg());
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        qos = new TextField();

        Label PublisherSetting = new Label(">> Publisher Setting : ");
        Label SubscriberSetting = new Label(">> Subscriber Setting : ");
        Label topicToSubscribe = new Label("Topic to subscribe");
        Label topicToPublish = new Label("Topic to publish");
        Label qosLabel = new Label("Qos (0<Qos<2):");
        topicField = new TextField();
        topicFieldSUBSCRIBE = new TextField();
        Screen = new TextArea("Please fil the blanks before you test the APP  ");
        Screen.setLayoutX(60);
        Screen.setLayoutY(310);

//About Grid Pane-----------------------------------------------------
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.BOTTOM_CENTER);
        gridPane.setLayoutX(110);
        gridPane.setLayoutY(30);
//----------------------------------------------------------------
        GridPane gridPane1 = new GridPane();
        gridPane1.setLayoutX(100);
        gridPane1.setLayoutY(100);
        gridPane1.setHgap(15);
        gridPane1.setVgap(15);
//--------------------------------------------------------------------------
        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 1, 0, 1, 1);
        gridPane.add(button3, 2, 0, 1, 1);
        gridPane.add(button4, 3, 0, 1, 1);
        gridPane.add(button5, 4, 0, 1, 1);
        gridPane.add(button6, 5, 0, 1, 1);
        gridPane1.add(PublisherSetting, 0, 0, 1, 1);
        gridPane1.add(qosLabel, 0, 1, 2, 1);
        gridPane1.add(qos, 1, 1, 1, 1);
        gridPane1.add(topicToPublish, 0, 2, 1, 1);
        gridPane1.add(topicField, 1, 2, 1, 1);
        gridPane1.add(SubscriberSetting, 0, 3, 1, 1);
        gridPane1.add(topicToSubscribe, 0, 4, 1, 1);
        gridPane1.add(topicFieldSUBSCRIBE, 1, 4, 1, 1);
        gridPane1.add(subButton, 2, 4, 1, 1);
//-----------------------------------------------------------------------
        Pane p = new Pane(gridPane, gridPane1, Screen);
        p.setMaxSize(600, 500);
        Scene scene = new Scene(p, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MQTT");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //Send message to publisher class
    public void Send(String message) {
        int QOS = Integer.parseInt(qos.getText());
        String TOPIC = topicField.getText();
        try {
            ClientPublish2 c = new ClientPublish2(url);
            c.state(TOPIC, message, QOS);
        } catch (MqttException e) {
            e.getMessage();
        }
    }
}
