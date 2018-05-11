package org.softwerkskammer.cdc.swapi.repositories;

import org.softwerkskammer.cdc.swapi.model.SWFilm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SWFilmRepository extends JpaRepository<SWFilm, Long> {

    Optional<SWFilm> findByEpisodeId(long episodeId);

}
