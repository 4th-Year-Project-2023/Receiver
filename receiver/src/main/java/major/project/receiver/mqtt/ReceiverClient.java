package major.project.receiver.mqtt;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import major.project.receiver.aws.GetParameter;
import major.project.receiver.json.ApplicationProperties;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ReceiverClient  {
    @Getter
    @Setter
    private IMqttClient relayStation;

    @Getter
    @Setter
    @Autowired
    private ReceiverCallback receiverCallback;

    @Getter
    @Setter
    @Autowired
    private GetParameter getParameter;

    @Getter
    @Setter
    @Autowired
    private ApplicationProperties applicationProperties;

    @Getter
    @Setter
    private String brokerAddress;

    @Getter
    @Setter
    private String channel;

    public void setUpMqtt() throws MqttException {
//        getParameter.extractParams("ccApplication");
        brokerAddress = getParameter.getApplicationProperties().getMqttServer();
        channel = getParameter.getApplicationProperties().getMqttChannel();
        this.relayStation = new MqttClient(brokerAddress, MqttAsyncClient.generateClientId(), new MemoryPersistence());

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName("edu");
        mqttConnectOptions.setPassword("arma".toCharArray());
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(3000);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setMaxInflight(10);
        this.relayStation.connect(mqttConnectOptions);
        this.relayStation.subscribe(this.channel);
        this.relayStation.setCallback(receiverCallback);

    }



}
