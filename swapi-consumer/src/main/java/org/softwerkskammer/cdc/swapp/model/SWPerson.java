package org.softwerkskammer.cdc.swapp.model;

public class SWPerson {

    private String id;
    private String name;
    private String gender;

    public SWPerson(String id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public SWPerson(){}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
}
