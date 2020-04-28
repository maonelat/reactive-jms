package africa.luna;

import io.quarkus.arc.config.ConfigProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@ConfigProperties(prefix = "ibm.mq")
public class MQConfig {

    private Optional<String> hostname;
    private Optional<String> port;
    private Optional<String> channel;
    private Optional<String> queueManager;
    private Optional<String> user;
    private Optional<String> password;

}
