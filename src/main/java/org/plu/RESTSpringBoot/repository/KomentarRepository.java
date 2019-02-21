package org.plu.RESTSpringBoot.repository;

import org.plu.RESTSpringBoot.entities.Komentar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KomentarRepository extends JpaRepository<Komentar,Integer> {
}
