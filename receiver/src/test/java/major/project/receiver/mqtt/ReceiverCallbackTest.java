package major.project.receiver.mqtt;

import major.project.receiver.json.CommandsList;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ReceiverCallbackTest {

    @Test
    public void commandsTest() {
        MqttMessage mqttMessage = new MqttMessage("{\"commands\":[\"ls>>cc.out\",\"touch test.txt\"]}".getBytes());
        CommandsList commandsList = new CommandsList();
        ReceiverCallback receiverCallback = new ReceiverCallback();
        commandsList = receiverCallback.extractPayloadCommands(mqttMessage);
        List<String> commands = new ArrayList<>();
        commands.add("ls>>cc.out");
        commands.add("touch test.txt");
        Assert.assertEquals(commandsList.getCommands(), commands);
    }
}