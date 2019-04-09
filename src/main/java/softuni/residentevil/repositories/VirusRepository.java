package softuni.residentevil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.residentevil.domain.entities.Virus;

import java.util.List;

public interface VirusRepository extends JpaRepository<Virus, String> {

    @Override
    List<Virus> findAll();
}
