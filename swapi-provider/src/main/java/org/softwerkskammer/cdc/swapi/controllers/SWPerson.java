package org.softwerkskammer.cdc.swapi.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.softwerkskammer.cdc.swapi.model.Film;
import org.softwerkskammer.cdc.swapi.model.Person;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SWPerson {

    @JsonProperty("name")
    private final String name;
    @JsonProperty("gender")
    private final String gender;
    @JsonProperty("films")
    private final List<String> films;

    public SWPerson(final Person person, final String filmBaseUrl) {
        this.name = person.getName();
        this.gender = person.getGender();
        this.films = person.getFilms().stream()
                .map(film -> toUrl(filmBaseUrl, film))
                .collect(toList());
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getFilms() {
        return Collections.unmodifiableList(films);
    }

    private String toUrl(final String filmBaseUrl, final Film film) {
        return String.format("%s/%d", filmBaseUrl, film.getEpisodeId());
    }

}
