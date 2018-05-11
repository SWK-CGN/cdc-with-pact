package org.softwerkskammer.cdc.swapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SWFilm {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long episodeId;
    private String title;
    private LocalDate releaseDate;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<SWPerson> characters = new ArrayList<>();

    public SWFilm() {
        // used by JPA
    }

    public SWFilm(final long episodeId, final String title, final LocalDate releaseDate) {
        this.episodeId = episodeId;
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public Long getId() {
        return id;
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

    public List<SWPerson> getCharacters() {
        return characters;
    }

    public void setCharacters(final List<SWPerson> characters) {
        this.characters = characters;
    }

}
