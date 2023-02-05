package major.project.receiver.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandsList {
    @JsonProperty("commands")
    @Getter
    @Setter
    private List<String> commands;

}
