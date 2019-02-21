package org.plu.RESTSpringBoot.rest.controllers;

import org.plu.RESTSpringBoot.entities.Recept;
import org.plu.RESTSpringBoot.repository.KorisnikRepository;
import org.plu.RESTSpringBoot.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recept")
@CrossOrigin
public class ReceptController {
    private final KorisnikRepository korisnikRepository;

    private final ReceptRepository receptRepository;

    @Autowired
    public ReceptController(KorisnikRepository korisnikRepository, ReceptRepository receptRepository) {
        this.korisnikRepository = korisnikRepository;
        this.receptRepository = receptRepository;
    }

    @GetMapping("/{postId}")
    public Optional<Recept> getAllReceptByStudId(@PathVariable(value = "postId") Integer postId) {
        return this.receptRepository.findById(postId);
    }

    @PostMapping()
    public Recept addNewRecept(@RequestBody Recept recept){
        return this.receptRepository.save(recept);
    }

    @GetMapping("/sastojci")
    public List<String> getAllReceptByBAR() {


        List<String> sastojci = new ArrayList<>();
        for (Recept r : receptRepository.findAll()) {
            String s = r.getSastojci();
            String segments[] = s.split(",");
            for (String pom : segments) {
                if (!sastojci.contains(pom)) {
                    sastojci.add(pom);
                }
            }

        }
        return sastojci;
    }


    @GetMapping("/sastojci/bar/{name}")
    public List<Recept> getBARsastojci(@PathVariable(value = "name") String name){

        List<Recept> recPom = new ArrayList<>();
        String segments[] = name.split(",");

        for(Recept r: receptRepository.findAll()){
            boolean sadrzi = true;
            for(String segment: segments){
                if(!r.getSastojci().contains(segment)){
                    sadrzi = false;
                }
            }
            if(sadrzi){
                recPom.add(r);
            }
        }

        return recPom;
    }


//    @PostMapping("/{studId}/recept")
//    public Recept createComment(@PathVariable (value = "studId") Integer studId, @RequestBody Korisnik korisnik) {
//        return this.korisnikRepository.findById(studId).map(korisnik -> {
//            korisnik.set(student);
//            return this.kontaktRepository.save(kontakt);
//       }).orElseThrow(() -> new ResourceNotFoundException("StudId " + studId + " not found"));
//    }
}
