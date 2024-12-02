package fr.digi.hello.entites;

import fr.digi.hello.DTO.VilleDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Ville {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(min = 2, max = 50)
    private String nom;
    @Min(10)
    private int nbHabitants;
    @ManyToOne
    Departement departement;

    public Ville() {
    }

    public Ville(String nom, int nbHabitants) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
    }

    public Ville(String nom, int nbHabitants, Departement departement) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
        this.departement = departement;
    }

    public String getNom() {
        return nom;
    }

    public int getNbHabitants() {
        return nbHabitants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }

    public void setDepartement(Departement departement) {
        if (this.departement != null) {
            this.departement.getVilles().remove(this);
        }
        this.departement = departement;
    }

    public VilleDTO toDto() {
    return new VilleDTO(nbHabitants,
            nom,
            departement.getNomDept(),
            departement.getNumero());
    }

    @Override
    public String toString() {
        return "Ville{" +
                "nom='" + nom + '\'' +
                ", nbHabitants=" + nbHabitants +
                '}';
    }

    public Departement getDepartement() {
        return departement;
    }
}
