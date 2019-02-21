package org.plu.RESTSpringBoot.repository;

import org.plu.RESTSpringBoot.entities.Korisnik;
import org.plu.RESTSpringBoot.entities.Slika;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlikaRepository extends JpaRepository<Slika, Integer> {
    Slika getById(Integer id);
    Slika getByIme(String ime);
}
