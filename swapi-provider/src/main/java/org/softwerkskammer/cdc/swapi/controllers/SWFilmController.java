package org.softwerkskammer.cdc.swapi.controllers;

import org.softwerkskammer.cdc.swapi.model.Film;
import org.softwerkskammer.cdc.swapi.repositories.FilmRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/films")
public class SWFilmController {

    private final FilmRepository filmRepository;

    public SWFilmController(final FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping
    public List<Film> films(final UriComponentsBuilder uriComponentsBuilder) {
        System.out.println(uriComponentsBuilder);
        return filmRepository.findAll(Sort.by("episodeId"));
    }

    @GetMapping("/{episodeId}")
    public ResponseEntity<Film> film(final @PathVariable("episodeId") long episodeId) {
        return filmRepository.findByEpisodeId(episodeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
