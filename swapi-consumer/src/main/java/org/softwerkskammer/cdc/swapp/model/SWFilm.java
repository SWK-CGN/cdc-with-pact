package org.softwerkskammer.cdc.swapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SWFilm {

    private Long id;
    private String title;

    public SWFilm(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public SWFilm() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
