package org.softwerkskammer.cdc.swapi.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class SWPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long characterId;
    private String name;
    private String gender;
    @ManyToMany(mappedBy = "characters")
    private List<SWFilm> films;

    public SWPerson() {
        // used by JPA
    }

    public SWPerson(final long characterId, final String name, final String gender) {
        this.characterId = characterId;
        this.name = name;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public long getCharacterId() {
        return characterId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public List<SWFilm> getFilms() {
        return films;
    }

}
