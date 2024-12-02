package fr.digi.hello.repository;

import fr.digi.hello.entites.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DepartementRepository extends JpaRepository<Departement, Integer> {
    Departement findByNomDept(String nomDept);
}
