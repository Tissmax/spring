package fr.digi.hello.dao;

import fr.digi.hello.entites.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Ville> extractVilles(){
        return em.createQuery("SELECT v FROM Ville v", Ville.class).getResultList();
    }

    @Transactional
    public Ville extractVilleById(Integer id){
        return em.createQuery("SELECT v FROM Ville v WHERE v.id = :id", Ville.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public Ville extractVilleByNom(String nom){
        return em.createQuery("SELECT v FROM Ville v WHERE v.nom = :nom", Ville.class)
                .setParameter("nom", nom)
                .getSingleResult();
    }

    @Transactional
    public void insertVille(Ville ville){
        em.persist(ville);
    }

    @Transactional
    public void modifierVille(Ville ville){
        em.merge(ville);
    }

    @Transactional
    public void supprimerVille(Ville ville){
        em.remove(ville);
    }
}
