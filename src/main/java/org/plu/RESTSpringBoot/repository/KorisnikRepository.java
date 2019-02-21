package org.plu.RESTSpringBoot.repository;

import org.plu.RESTSpringBoot.entities.Korisnik;
import org.plu.RESTSpringBoot.entities.Slika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    Korisnik getByUsername(String username);
    Korisnik findByUsername(String username);
    List<Korisnik> getAllByUsernameContains(String partial);
    //List<Slika> getByIdAnd(Integer id);
}
