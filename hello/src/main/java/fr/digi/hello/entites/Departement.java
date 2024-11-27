package fr.digi.hello.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Departement {

    @Id
    private Integer id;
    private String numero;
    private String nomDept;
    @JsonIgnore
    @OneToMany(mappedBy = "departement")
    List<Ville> villes = new ArrayList<>();

    public Departement() {
    }

    public Departement(String nomDept, String numero) {
        this.nomDept = nomDept;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Departement{" +
                ", nom='" + nomDept + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Departement that = (Departement) o;
        return Objects.equals(id, that.id) && Objects.equals(nomDept, that.nomDept) && Objects.equals(numero, that.numero) && Objects.equals(villes, that.villes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomDept, numero, villes);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nomDept;
    }

    public void setNom(String nom) {
        this.nomDept = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    protected List<Ville> getVilles() {
        return villes;
    }
}
