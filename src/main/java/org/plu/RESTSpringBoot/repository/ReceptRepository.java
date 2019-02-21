package org.plu.RESTSpringBoot.repository;

import org.plu.RESTSpringBoot.entities.Recept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceptRepository  extends JpaRepository<Recept, Integer> {
    List<Recept> getByNaziv(String naziv);
}
