package org.softwerkskammer.cdc.swapi.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.softwerkskammer.cdc.swapi.model.Film;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.softwerkskammer.cdc.swapi.controllers.ControllerUtils.toUrl;

public class SWFilm {

    @JsonProperty("id")
    private final long id;
    @JsonProperty("title")
    private final String title;
    @JsonProperty("releaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate releaseDate;
    @JsonProperty("characters")
    private final List<String> characters;
    @JsonProperty("url")
    private final String url;

    SWFilm(final Film film, final String filmsPath, final String peoplePath) {
        this.id = film.getEpisodeId();
        this.title = film.getTitle();
        this.releaseDate = film.getReleaseDate();
        this.characters = film.getCharacters().stream()
                .map(character -> toUrl(peoplePath, character.getCharacterId()))
                .collect(toList());
        this.url = toUrl(filmsPath, film.getEpisodeId());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public List<String> getCharacters() {
        return Collections.unmodifiableList(characters);
    }

    public String getUrl() {
        return url;
    }
}
