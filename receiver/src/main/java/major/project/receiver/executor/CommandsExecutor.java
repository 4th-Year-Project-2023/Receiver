package major.project.receiver.executor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import major.project.receiver.aws.GetParameter;
import major.project.receiver.mqtt.PublishOutput;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Slf4j
public class CommandsExecutor {
    @Setter
    @Getter
    private String shPath;

    @Setter
    @Getter
    private String filePath;

    @Setter
    @Getter
    @Autowired
    private GetParameter getParameter;

    @Setter
    @Getter
    @Autowired
    private PublishOutput publishOutput;

    public void executeCommand() throws IOException {
        try {
            shPath = getParameter.getApplicationProperties().getShPath();
            filePath = "test.txt";
            Process process= Runtime.getRuntime().exec(shPath);
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
//            FileOutputStream fos = new FileOutputStream(filePath);
//            fos.write(String.valueOf(output).getBytes());
//            fos.flush();
//            fos.close();
            publishOutput.sendOutputFile(output);
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                log.info("Success!");
            } else {
                log.info("Command could not be executed");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
