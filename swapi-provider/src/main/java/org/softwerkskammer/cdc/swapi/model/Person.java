package org.softwerkskammer.cdc.swapi.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long characterId;
    private String name;
    private String gender;
    @ManyToMany(mappedBy = "characters")
    private List<Film> films = new ArrayList<>();

    public Person() {
        // used by JPA
    }

    public Person(final long characterId, final String name, final String gender) {
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

    public List<Film> getFilms() {
        return films;
    }

}
