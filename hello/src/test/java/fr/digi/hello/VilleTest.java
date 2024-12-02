package fr.digi.hello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.digi.hello.DTO.VilleDTO;
import fr.digi.hello.entites.Departement;
import fr.digi.hello.entites.Ville;
import fr.digi.hello.exeptions.VilleExeption;
import fr.digi.hello.mapper.VilleMapper;
import fr.digi.hello.repository.DepartementRepository;
import fr.digi.hello.repository.VilleRepository;
import fr.digi.hello.service.VilleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class VilleTest {

    @Autowired
    private VilleService villesService;
    @Mock
    private VilleRepository villeRepository;
    @Mock
    private DepartementRepository departementRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetVilles() throws Exception {
        assertTrue(villesService.getVilles().iterator().hasNext());
    }

    @Test
    void testGetVillesPage() {
        assertTrue(villesService.getVillesPage(0, 10).hasContent());
    }

    @Test
    void testGetVilleById() throws VilleExeption {
        assertTrue(villesService.getVilleById(1).isPresent());
    }

    @Test
    void testGetVilleByNom() throws VilleExeption {
        assertNotNull(villesService.getVilleByNom("Paris"));
    }

    @Test
    void testGetVillesByDepartement() {
        assertFalse(villesService.getVillesByDepartement("Ain", PageRequest.of(0, 10)).isEmpty());
    }

    @Test
    void testGetVillesByDepartementAndNbHabitantsGreaterThan() {
        assertTrue(villesService.getVillesByDepartementAndNbHabitantsGreaterThan("Ain", 1000000).isEmpty());
    }

    @Test
    void testGetVilleBydepartementWithLimit() {
        assertFalse(villesService.getVilleBydepartementWithLimit("Ain", PageRequest.of(0, 10)).isEmpty());
    }

    @Test
    void testGetVillesByDepartementAndNbHabitantsBetween() {
        assertTrue(villesService.getVillesByDepartementAndNbHabitantsBetween("Ain", 500000, 2000000).isEmpty());
    }

    @Test
    void testGetVillesByNbHabitantsGreaterThan() {
        assertFalse(villesService.getVillesByNbHabitantsGreaterThan(1000000).isEmpty());
    }

    @Test
    void testGetVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan() {
        assertFalse(villesService.getVillesByNbHabitantsLessThanAndNbHabitantsGreaterThan(2000000, 500000).isEmpty());
    }

    @Test
    void testInsertVille() throws Exception {
        Departement departement = new Departement("Gironde", "33");
        Ville ville = new Ville("Bordeaux", 1000000);

        when(departementRepository.findByNomDept(anyString())).thenReturn(departement);
        mockMvc.perform(MockMvcRequestBuilders.post("/villes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new VilleDTO(ville.getNbHabitants(), ville.getNom(), departement.getNomDept(), departement.getNumero())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSupprimerVille() throws VilleExeption {
        assertTrue(villesService.supprimerVille("TestVille"));
    }

    @Test
    void testExportVillesCSV() throws Exception {
        assertNotNull(villesService.exportVillesCSV(1000));
    }

}
