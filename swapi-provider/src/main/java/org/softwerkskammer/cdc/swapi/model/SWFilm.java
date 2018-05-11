package org.softwerkskammer.cdc.swapi.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class SWFilm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long episodeId;
    private String title;
    private LocalDate releaseDate;
    @ManyToMany
    private List<SWPerson> characters;

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

}
