package org.softwerkskammer.cdc.swapp.model;

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
