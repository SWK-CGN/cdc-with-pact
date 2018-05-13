package org.softwerkskammer.cdc.swapi.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.softwerkskammer.cdc.swapi.model.Film;
import org.softwerkskammer.cdc.swapi.model.Person;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SWFilm {

    @JsonProperty("episode_id")
    private final long episodeId;
    @JsonProperty("title")
    private final String title;
    @JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate releaseDate;
    @JsonProperty("characters")
    private final List<String> characters;

    public SWFilm(final Film film, final String peoplePath) {
        this.episodeId = film.getEpisodeId();
        this.title = film.getTitle();
        this.releaseDate = film.getReleaseDate();
        this.characters = film.getCharacters().stream()
                .map(character -> toUrl(peoplePath, character))
                .collect(toList());
    }

    public long getEpisodeId() {
        return episodeId;
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

    private String toUrl(final String baseUrl, final Person character) {
        return String.format("%s/%d", baseUrl, character.getCharacterId());
    }

}
