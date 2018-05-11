package org.softwerkskammer.cdc.swapi.repositories;

import org.softwerkskammer.cdc.swapi.model.SWFilm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SWFilmRepository extends JpaRepository<SWFilm, Long> {
}
