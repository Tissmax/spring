package fr.digi.hello.repository;

import fr.digi.hello.entites.Departement;
import fr.digi.hello.entites.Ville;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Integer> {
    List<Ville> findAllByNom(String nom);
    Ville findVilleByNom(String nom);
    List<Ville> findByNbHabitantsGreaterThan(int nbHabitants);
    List<Ville> findByNbHabitantsLessThanAndNbHabitantsGreaterThan(@Min(0) int min, @Min(0) int max);
    List<Ville> findVillesByDepartement_NomDeptAndNbHabitantsGreaterThan(String nomDept, @Min(0) int min);
    List<Ville> findVillesByDepartement_NomDeptAndNbHabitantsBetween(String nomDept, @Min(0) int min, @Min(0) int max);
    List<Ville> findVillesByDepartement_NomDept(String nomDept, Pageable limit);
    List<Ville> findVilleByDepartement_NomDept(@Size(min = 2) @NotEmpty String departementNomDept);
}
