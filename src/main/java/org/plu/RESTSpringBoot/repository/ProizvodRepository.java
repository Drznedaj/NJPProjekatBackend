package org.plu.RESTSpringBoot.repository;

import org.plu.RESTSpringBoot.entities.Proizvod;
import org.plu.RESTSpringBoot.entities.Recept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProizvodRepository  extends JpaRepository<Proizvod, Integer> {
    List<Proizvod> getByNaziv(String naziv);
}
