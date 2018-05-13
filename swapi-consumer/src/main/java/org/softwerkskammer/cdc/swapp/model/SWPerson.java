package org.softwerkskammer.cdc.swapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SWPerson {

    private long id;
    private String name;
    private String gender;
    private List<String> films;

    public SWPerson(long id, String name, String gender, List<String> films) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.films = films;
    }

    public SWPerson() {
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
        return films;
    }
}
