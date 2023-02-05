package major.project.receiver.executor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import major.project.receiver.json.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
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
    @Autowired
    private ApplicationProperties applicationProperties;

    public void executeCommand() throws IOException {
        try {
            shPath = applicationProperties.getShPath();
            Process process= Runtime.getRuntime().exec(shPath);
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
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
        }
    }
}
