package org.softwerkskammer.cdc.swapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SWPerson {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("id")
    private long characterId;
    private String name;
    private String gender;
    @JsonIgnore
    @ManyToMany(mappedBy = "characters")
    private List<SWFilm> films = new ArrayList<>();

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
