package ma.fpl.chercheur_svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ChercheurSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChercheurSvcApplication.class, args);
    }

}
