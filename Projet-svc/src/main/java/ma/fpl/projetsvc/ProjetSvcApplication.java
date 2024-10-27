package ma.fpl.projetsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProjetSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetSvcApplication.class, args);
    }

}
