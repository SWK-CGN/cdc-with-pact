package org.softwerkskammer.cdc.swapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SWPerson {

    private long id;
    private String name;
    private String gender;

    public SWPerson(long id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public SWPerson(){}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
}
