package org.softwerkskammer.cdc.swapi.controllers;

import org.softwerkskammer.cdc.swapi.model.SWFilm;
import org.softwerkskammer.cdc.swapi.model.SWPerson;
import org.softwerkskammer.cdc.swapi.repositories.SWFilmRepository;
import org.softwerkskammer.cdc.swapi.repositories.SWPersonRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/people")
    public List<SWPerson> people() {
        return personRepository.findAll(Sort.by("characterId"));
    }

}
