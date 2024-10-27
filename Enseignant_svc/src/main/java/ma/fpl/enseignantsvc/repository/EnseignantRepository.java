package ma.fpl.enseignantsvc.repository;

import ma.fpl.enseignantsvc.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant,Long> {

    Enseignant findEnseignantByEmail(String email);

}
