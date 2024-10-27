package ma.fpl.gatewaysvc;

import ma.fpl.gatewaysvc.configuration.RsaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaConfig.class)
public class GatewaySvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaySvcApplication.class, args);
    }

}
