package major.project.receiver.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import major.project.receiver.aws.GetParameter;
import major.project.receiver.executor.CommandsExecutor;
import major.project.receiver.json.ApplicationProperties;
import major.project.receiver.json.CommandsList;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
@Slf4j
public class ReceiverCallback implements MqttCallback {


    @Setter
    @Getter
    private String shPath;


    @Autowired
    private CommandsExecutor commandsExecutor;

    @Setter
    @Getter
    @Autowired
    private GetParameter getParameter;

    @Override
    public void connectionLost(Throwable throwable) {
        log.info("Connection Lost. Try to reconnect");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("Received message from " + topic+"  "+mqttMessage);
        CommandsList commandsList = extractPayloadCommands(mqttMessage);
        appendCommandsToFile(commandsList);
        commandsExecutor.executeCommand();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("Delivery Completed!!");
    }

    public CommandsList extractPayloadCommands(MqttMessage mqttMessage){
        String strMessage = new String(mqttMessage.getPayload());
        CommandsList commandsList = new CommandsList();
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            commandsList = objectMapper.readValue(strMessage, CommandsList.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return commandsList;
    }

    public void appendCommandsToFile(CommandsList commandsList) throws IOException {
        shPath = getParameter.getApplicationProperties().getShPath();
        FileWriter writer = new FileWriter(shPath);
        for(String str: commandsList.getCommands()) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
        File file = new File(shPath);
        file.setExecutable(true);
        file.setReadable(true);
    }
}
