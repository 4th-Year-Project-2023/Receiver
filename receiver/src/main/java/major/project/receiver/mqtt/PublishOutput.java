package major.project.receiver.mqtt;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PublishOutput {
    @Getter
    @Setter
    private IMqttClient relayStation;

    public void sendOutputFile(StringBuilder output) throws MqttException {
//        brokerAddress = getParameter.getApplicationProperties().getMqttServer();
//        channel = getParameter.getApplicationProperties().getMqttChannel();
        this.relayStation = new MqttClient("tcp://localhost:1883", MqttAsyncClient.generateClientId(), new MemoryPersistence());

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName("edu");
        mqttConnectOptions.setPassword("arma".toCharArray());
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(3000);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setMaxInflight(10);
        this.relayStation.connect(mqttConnectOptions);
        MqttMessage message = new MqttMessage(String.valueOf(output).getBytes());
        this.relayStation.publish("t2.io/output",message);
        log.info(String.valueOf(message));
        this.relayStation.disconnect();
        this.relayStation.close();
    }
}
