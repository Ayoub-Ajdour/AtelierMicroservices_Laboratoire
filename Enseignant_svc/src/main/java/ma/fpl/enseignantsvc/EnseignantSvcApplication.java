package ma.fpl.enseignantsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EnseignantSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnseignantSvcApplication.class, args);
    }

}
