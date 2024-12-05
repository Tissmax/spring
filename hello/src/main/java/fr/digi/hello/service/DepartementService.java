package fr.digi.hello.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import fr.digi.hello.entites.Departement;
import fr.digi.hello.entites.Ville;
import fr.digi.hello.repository.DepartementRepository;
import fr.digi.hello.repository.VilleRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.itextpdf.text.pdf.BaseFont.*;

@Service
public class DepartementService {

    @Autowired
    private final DepartementRepository departementRepository;
    private List<Departement> departements;
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private VilleService villeService;

    public DepartementService(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
        this.departements = getDepartements();
    }

    public List<Ville> getVilleFromDepartement(String nomDept) {
        return departementRepository.findByNomDept(nomDept).getVilles();
    }

    public List<Departement> getDepartements() {
        return this.departements = departementRepository.findAll();
    }

    private Optional<Departement> getDepartementById(int idDepartement) {
        return departements.stream()
                .filter(d -> d.getId().equals(idDepartement))
                .findFirst();
    }

    public Optional<Departement> getDepartementByNom(String nom) {
        return departements.stream()
                .filter(d -> d.getNomDept().equals(nom))
                .findFirst();
    }

    public boolean supprimerDepartement(int idDepartement) {
        Optional<Departement> departement = getDepartementById(idDepartement);
        boolean departementExists = departement.isPresent();
        departement.ifPresent(departementRepository::delete);
        getDepartements();
        return departementExists;
    }

    public void modifierDepartement(int idDepartement, Departement departementModifie) {
        Optional<Departement> departement = getDepartementById(idDepartement);
        departement.ifPresent(d -> {
            d.setNomDept(departementModifie.getNomDept());
            d.setNumero(departementModifie.getNumero());
            departementRepository.save(d);
        });
        getDepartements();
    }

    public void insertDepartement(Departement departement) {
        if (departements.stream()
                .anyMatch(d -> d.getNomDept().equals(departement.getNomDept()))
        ) {
            return;
        }
        departements.add(new Departement(departement.getNomDept(), departement.getNumero()));
        departementRepository.save(departement);
        getDepartements();
    }

    public void exportVillesPDF(String code, HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        String nomDept = villeService.nomDepartement(code).nom();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.addTitle("Liste des villes ayant le plus d'habitants - " + nomDept);
        document.newPage();
        BaseFont baseFont = createFont(HELVETICA, WINANSI, EMBEDDED);
        this.getVilleFromDepartement(nomDept).forEach(ville -> {
            try {
                document.add(new Phrase(ville.getNom() + " - " + ville.getNbHabitants() +"\n",
                        new Font(baseFont, 12.0f, 1, BaseColor.BLACK)));
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
        document.close();
    }
}
