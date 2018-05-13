package org.softwerkskammer.cdc.swapi.repositories;

import org.softwerkskammer.cdc.swapi.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {

    Optional<Film> findByEpisodeId(long episodeId);

}
