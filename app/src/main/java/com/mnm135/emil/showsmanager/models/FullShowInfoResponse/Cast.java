package com.mnm135.emil.showsmanager.models.FullShowInfoResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast {

    @SerializedName("person")
    @Expose
    private Person person;
    @SerializedName("character")
    @Expose
    private Character character;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

}
