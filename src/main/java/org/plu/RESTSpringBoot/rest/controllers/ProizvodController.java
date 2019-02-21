package org.plu.RESTSpringBoot.rest.controllers;

import org.plu.RESTSpringBoot.entities.Proizvod;
import org.plu.RESTSpringBoot.entities.Recept;
import org.plu.RESTSpringBoot.repository.KorisnikRepository;
import org.plu.RESTSpringBoot.repository.ProizvodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proizvod")
@CrossOrigin
public class ProizvodController {
    private final KorisnikRepository korisnikRepository;

    private final ProizvodRepository proizvodRepository;

    @Autowired
    public ProizvodController(KorisnikRepository korisnikRepository, ProizvodRepository proizvodRepository) {
        this.korisnikRepository = korisnikRepository;
        this.proizvodRepository = proizvodRepository;
    }

    @GetMapping("/{postId}")
    public Optional<Proizvod> getAllProizvodByStudId(@PathVariable(value = "postId") Integer postId) {
        return this.proizvodRepository.findById(postId);
    }

    @PostMapping()
    public Proizvod addNewProizvod(@RequestBody Proizvod proizvod){
        return this.proizvodRepository.save(proizvod);
    }

}
