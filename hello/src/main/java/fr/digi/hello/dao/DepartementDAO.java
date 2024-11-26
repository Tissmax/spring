package fr.digi.hello.dao;

import fr.digi.hello.entites.Departement;
import fr.digi.hello.entites.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartementDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Departement> extractDepartements(){
        return em.createQuery("SELECT d FROM Departement d", Departement.class).getResultList();
    }

    @Transactional
    public Departement extractDepartementById(int id){
        return em.find(Departement.class, id);
    }

    @Transactional
    public List<Ville> extractBigCityDepartements(int nbVilles, String nomDept){
        return em.createQuery("SELECT d FROM Departement d, Ville v where d.nomDept = :nomDept " +
                "order by v.nbHabitants desc limit :nbVilles", Ville.class).getResultList();
    }

    @Transactional
    public List<Ville> extractVillesRange(String nomDept, int min, int max){
        return em.createQuery("SELECT v FROM Departement d, Ville v where d.nomDept = :nomDept " +
                "and v.nbHabitants > :min and v.nbHabitants < :max ", Ville.class).getResultList();
    }

    @Transactional
    public Departement extractDepartementByNom(String nom){
        return em.createQuery("SELECT d FROM Departement d WHERE d.nomDept = :nom", Departement.class)
                .setParameter("nom", nom)
                .getSingleResult();
    }

    @Transactional
    public void insertDepartement(Departement departement){
        em.persist(departement);
    }

    @Transactional
    public void modifierDepartement(Departement departement){
        em.merge(departement);
    }

    @Transactional
    public void supprimerDepartement(Departement departement){
        em.remove(departement);
    }
}
