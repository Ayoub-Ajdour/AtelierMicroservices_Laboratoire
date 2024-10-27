package ma.fpl.configurationsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationSvcApplication.class, args);
    }

}
