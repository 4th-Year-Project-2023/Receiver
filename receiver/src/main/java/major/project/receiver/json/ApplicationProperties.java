package major.project.receiver.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
public class ApplicationProperties {
    @JsonProperty("mqtt.server.name")
    @Getter
    @Setter
    private String mqttServer;
    @JsonProperty("mqtt.channel.name")
    @Getter
    @Setter
    private String mqttChannel;
    @JsonProperty("sh.path")
    @Getter
    @Setter
    private String shPath;
}
