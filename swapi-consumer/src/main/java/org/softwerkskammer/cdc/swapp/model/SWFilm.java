package org.softwerkskammer.cdc.swapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SWFilm {

    private long id;
    private String title;

    public SWFilm(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public SWFilm() {}

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
