package fr.digi.hello.repository;

import fr.digi.hello.entites.Departement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartementRepository extends JpaRepository<Departement, Integer> {
    Departement findByNomDept(String nomDept);
}
