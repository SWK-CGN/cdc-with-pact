package org.softwerkskammer.cdc.swapi.controllers;

import org.softwerkskammer.cdc.swapi.model.Film;
import org.softwerkskammer.cdc.swapi.repositories.FilmRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(SWFilmController.PATH)
public class SWFilmController {

    static final String PATH = "/films";

    static String path(final UriComponentsBuilder uriComponentsBuilder) {
        return uriComponentsBuilder.toUriString() + PATH;
    }

    private final FilmRepository filmRepository;

    public SWFilmController(final FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping
    public List<SWFilm> films(final UriComponentsBuilder uriComponentsBuilder) {
        return filmRepository.findAll(Sort.by("episodeId")).stream()
                .map(film -> new SWFilm(film, SWPersonController.path(uriComponentsBuilder)))
                .collect(toList());
    }

    @GetMapping("/{episodeId}")
    public ResponseEntity<SWFilm> film(final @PathVariable("episodeId") long episodeId,
                                     final UriComponentsBuilder uriComponentsBuilder) {
        return filmRepository.findByEpisodeId(episodeId)
                .map(toSWFilm(uriComponentsBuilder))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Function<Film, SWFilm> toSWFilm(final UriComponentsBuilder uriComponentsBuilder) {
        return film -> new SWFilm(film, SWPersonController.path(uriComponentsBuilder));
    }

}
