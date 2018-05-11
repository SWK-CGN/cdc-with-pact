package org.softwerkskammer.cdc.swapi.controllers;

import org.softwerkskammer.cdc.swapi.model.SWFilm;
import org.softwerkskammer.cdc.swapi.model.SWPerson;
import org.softwerkskammer.cdc.swapi.repositories.SWFilmRepository;
import org.softwerkskammer.cdc.swapi.repositories.SWPersonRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SwapiController {

    private final SWFilmRepository filmRepository;
    private final SWPersonRepository personRepository;

    public SwapiController(final SWFilmRepository filmRepository, final SWPersonRepository personRepository) {
        this.filmRepository = filmRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/films")
    public List<SWFilm> films() {
        return filmRepository.findAll(Sort.by("episodeId"));
    }

    @GetMapping("/films/{episodeId}")
    public ResponseEntity<SWFilm> film(final @PathVariable("episodeId") long episodeId) {
        return filmRepository.findByEpisodeId(episodeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/people")
    public List<SWPerson> people() {
        return personRepository.findAll(Sort.by("characterId"));
    }

    @GetMapping("/people/{characterId}")
    public ResponseEntity<SWPerson> person(final @PathVariable("characterId") long characterId) {
        return personRepository.findByCharacterId(characterId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
