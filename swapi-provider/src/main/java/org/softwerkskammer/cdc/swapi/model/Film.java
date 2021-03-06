package org.softwerkskammer.cdc.swapi.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long episodeId;
    private String title;
    private LocalDate releaseDate;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Person> characters = new ArrayList<>();

    public Film() {
        // used by JPA
    }

    public Film(final long episodeId, final String title, final LocalDate releaseDate) {
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

    public List<Person> getCharacters() {
        return characters;
    }

    public void setCharacters(final List<Person> characters) {
        this.characters = characters;
    }

}
