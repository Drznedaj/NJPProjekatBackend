package org.plu.RESTSpringBoot.rest.controllers;

import org.plu.RESTSpringBoot.entities.Korisnik;
import org.plu.RESTSpringBoot.entities.Slika;
import org.plu.RESTSpringBoot.repository.SlikaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sli")
public class SlikaController {

    private SlikaRepository slikaRepository;

    @Autowired
    public SlikaController(SlikaRepository slikaRepository){
        this.slikaRepository=slikaRepository;
    }

    @PostMapping("/lajkuj/{slika}")
    public Integer lajkuj(@PathVariable String slika){
        Slika s = this.slikaRepository.getByIme(slika);
        s.setBrojLajkova(s.getBrojLajkova()+1);
        this.slikaRepository.save(s);
        return s.getBrojLajkova();
    }

    @PostMapping("/unlajkuj/{slika}")
    public Integer unlajkuj(@PathVariable String slika){
        Slika s = this.slikaRepository.getByIme(slika);
        s.setBrojLajkova(s.getBrojLajkova()-1);
        this.slikaRepository.save(s);
        return s.getBrojLajkova();
    }
}
