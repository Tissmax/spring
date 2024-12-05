package fr.digi.hello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.digi.hello.DTO.NomDeptDTO;
import fr.digi.hello.DTO.VilleDTO;
import fr.digi.hello.TraitementFichierApplication;
import fr.digi.hello.entites.Ville;
import fr.digi.hello.exeptions.VilleExeption;
import fr.digi.hello.mapper.VilleMapper;
import fr.digi.hello.repository.DepartementRepository;
import fr.digi.hello.repository.VilleRepository;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    @Autowired
    private final VilleRepository villeRepository;
    private final List<VilleDTO> villes;
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    WebClient webClient;
    @Autowired
    private ObjectMapper objectMapper;

    public VilleService(VilleRepository villeRepository) throws VilleExeption {
        this.villeRepository = villeRepository;
        this.villes = getVilles();
    }

    public Page<Ville> getVillesPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return villeRepository.findAll(pageable);
    }

    public List<VilleDTO> getVilles() throws VilleExeption {
        try {
            return villeRepository.findAll()
                    .stream().map(VilleMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            throw new VilleExeption("Il n'y a pas de villes dans la base de données");
        }
    }

    public Optional<VilleDTO> getVilleById(Integer id) throws VilleExeption {
        try {
            return villeRepository.findById(id)
                    .map(Ville::toDto);
        } catch (Exception e) {
            throw new VilleExeption("Il n'y a pas de ville avec l'id: " + id);
        }
    }

    public VilleDTO getVilleByNom(String nom) throws VilleExeption {
        return villeRepository.findAllByNom(nom)
                .stream().map(Ville::toDto)
                .findFirst().orElseThrow(() -> new VilleExeption("Il n'y a pas de ville avec le nom: " + nom));
    }

    public List<Ville> getVillesByDepartement(String nomDept, Pageable limit){
        return villeRepository.findVillesByDepartement_NomDept(nomDept, limit);
    }

    public List<Ville> getVillesByDepartementAndNbHabitantsGreaterThan(String nomDept, int min){
        return villeRepository.findVillesByDepartement_NomDeptAndNbHabitantsGreaterThan(nomDept, min);
    }

    public List<Ville> getVilleBydepartementWithLimit(String nomDept, Pageable limit){
        return villeRepository.findVillesByDepartement_NomDept(nomDept, limit);
    }

    public List<Ville> getVillesByDepartementAndNbHabitantsBetween(String nomDept, int min, int max){
        return villeRepository.findVillesByDepartement_NomDeptAndNbHabitantsBetween(nomDept, min, max);
    }

    public List<Ville> getVillesByNbHabitantsGreaterThan(int nbHabitants){
        return villeRepository.findByNbHabitantsGreaterThan(nbHabitants);
    }

    public List<VilleDTO> getVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan(int min, int max){
        return villeRepository.findByNbHabitantsLessThanAndNbHabitantsGreaterThan(min, max)
                .stream().map(Ville::toDto).toList();
    }

    public boolean insertVille(Ville ville){
        if (villes.stream()
                .noneMatch(v -> v.nom().equals(ville.getNom()))
        ) {
            departementRepository.save(ville.getDepartement());
            villeRepository.save(ville);
            return true;
        }
        return false;
    }

    public boolean modifierVille(int idVille, Ville villeModifiee) throws VilleExeption {
        Optional<Ville> ville = villeRepository.findById(idVille);
        if (ville.isPresent()) {
            ville.get().setNom(villeModifiee.getNom());
            ville.get().setNbHabitants(villeModifiee.getNbHabitants());
            villeRepository.save(ville.get());
            getVilles();
            return true;
        }
        return false;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public boolean supprimerVille(String nom) throws VilleExeption {
        Optional<Ville> ville = Optional.ofNullable(villeRepository.findVilleByNom(nom));
        if (ville.isPresent()) {
            System.out.println("Ville supprimée");
            villeRepository.delete(ville.get());
            getVilles();
            return true;
        }
        return false;
    }


    public String exportVillesCSV(int min) throws Exception {
//        TraitementFichierApplication t = new TraitementFichierApplication();
//        t.run();
//        return t.exportVillesCSV(min);
        StringBuilder sb = new StringBuilder();
        String HEADER = "Nom;Nb habitants;Nom département;Numéro département\n";
        sb.append(HEADER);
        List<VilleDTO> dtos = villeRepository.findByNbHabitantsGreaterThan(min).stream()
                .map(Ville::toDto)
                .toList();
        return dtos.stream()
                .map(v -> {
                    try {
                        return v.nom() + ";" + v.nbHabitants() + ";" + nomDepartement(v.numero()).nom() + ";" + v.numero() + "\n";
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(
                        () -> sb,
                        StringBuilder::append,
                        StringBuilder::append
                ).toString();
    }

    public NomDeptDTO nomDepartement(String codeRegion) throws JsonProcessingException {
        String body = webClient.get()
                .uri("/{codeRegion}?fields=nom", codeRegion)
                .retrieve()
                .bodyToFlux(String.class)
                .blockFirst();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body , NomDeptDTO.class);
    }
}
