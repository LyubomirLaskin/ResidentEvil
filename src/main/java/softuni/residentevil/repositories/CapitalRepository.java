package softuni.residentevil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.residentevil.domain.entities.Capital;

import java.util.List;

public interface CapitalRepository extends JpaRepository<Capital, String> {

    @Query("SELECT c FROM capitals c ORDER BY c.name")
    List<Capital> findAllOrOrderByName();
}
