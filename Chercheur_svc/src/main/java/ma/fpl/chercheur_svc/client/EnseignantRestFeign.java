package ma.fpl.chercheur_svc.client;


import ma.fpl.chercheur_svc.module.Enseignant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Enseignant-service",url = "http://localhost:8081")
public interface EnseignantRestFeign {

    @GetMapping("/Enseignants/{id}")
    Enseignant Enseignant_ByID(@PathVariable Long id);

}
