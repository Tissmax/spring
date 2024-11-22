package fr.digi.hello.entites;

import java.util.Objects;

public class Ville {

    private int nbHabitants;
    private String nom;

    public Ville(int nbHabitants, String nom) {
        this.nbHabitants = nbHabitants;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "nbHabitants=" + nbHabitants +
                ", nom='" + nom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ville ville = (Ville) o;
        return nbHabitants == ville.nbHabitants && Objects.equals(nom, ville.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbHabitants, nom);
    }

    public int getNbHabitants() {
        return nbHabitants;
    }

    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
