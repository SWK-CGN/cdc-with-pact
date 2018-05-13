package org.softwerkskammer.cdc.swapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SWFilm {

    private long id;
    private String title;
    private List<String> characters;

    public SWFilm(long id, String title, List<String> characters) {
        this.id = id;
        this.title = title;
        this.characters = characters;
    }

    public SWFilm() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getCharacters() {
        return characters;
    }
}
