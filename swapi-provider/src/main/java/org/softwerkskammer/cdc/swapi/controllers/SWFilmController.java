package org.softwerkskammer.cdc.swapi.controllers;

import org.softwerkskammer.cdc.swapi.model.Film;
import org.softwerkskammer.cdc.swapi.repositories.FilmRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.function.Function;

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
    public SWCollection<SWFilm> allFilms(final UriComponentsBuilder uriComponentsBuilder) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @GetMapping("/{episodeId}")
    public ResponseEntity<SWFilm> film(final @PathVariable("episodeId") long episodeId,
                                     final UriComponentsBuilder uriComponentsBuilder) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    private Function<Film, SWFilm> toSWFilm(final UriComponentsBuilder uriComponentsBuilder) {
        return film -> new SWFilm(film,
                SWFilmController.path(uriComponentsBuilder),
                SWPersonController.path(uriComponentsBuilder));
    }

}
