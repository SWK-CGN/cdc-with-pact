package org.softwerkskammer.cdc.swapi.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.softwerkskammer.cdc.swapi.model.Person;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.softwerkskammer.cdc.swapi.controllers.ControllerUtils.toUrl;

public class SWPerson {

    @JsonProperty("id")
    private final long id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("gender")
    private final String gender;
    @JsonProperty("films")
    private final List<String> films;
    @JsonProperty("url")
    private final String url;

    SWPerson(final Person person, final String peoplePath, final String filmsPath) {
        this.id = person.getCharacterId();
        this.name = person.getName();
        this.gender = person.getGender();
        this.films = person.getFilms().stream()
                .map(film -> toUrl(filmsPath, film.getEpisodeId()))
                .collect(toList());
        this.url = toUrl(peoplePath, person.getCharacterId());
    }

    public long getId() {
        return id;
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

    public String getUrl() {
        return url;
    }

}
