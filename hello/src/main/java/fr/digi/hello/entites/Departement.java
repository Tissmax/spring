package fr.digi.hello.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.digi.hello.DTO.DepartementDTO;
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
    @OneToMany(mappedBy = "departement")
    @JsonIgnore
    List<Ville> villes = new ArrayList<>();

    public Departement() {
    }

    public Departement(String nomDept, String numero) {
        this.nomDept = nomDept;
        this.numero = numero;
    }

    public DepartementDTO toDTO() {
        return new DepartementDTO(nomDept,
                numero,
                villes.stream()
                        .map(Ville::getNbHabitants)
                        .reduce(0, Integer::sum));
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    protected List<Ville> getVilles() {
        return villes;
    }

    public String getNomDept() {
        return nomDept;
    }

    public void setNomDept(String nomDept) {
        this.nomDept = nomDept;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }
}
