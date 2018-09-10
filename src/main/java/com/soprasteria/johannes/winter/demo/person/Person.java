package com.soprasteria.johannes.winter.demo.person;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class Person {

    private String id;

    private String familyName, givenName;

    private LocalDate dateOfBirth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person)) {
            return false;
        }
        Person other = (Person) o;
        return Objects.equals(givenName, other.givenName) && Objects.equals(familyName, other.familyName)
                && Objects.equals(dateOfBirth, other.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenName, familyName, dateOfBirth);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{givenName=" + givenName + ",familyName=" + familyName + ",dateOfBirth=" + dateOfBirth + "}";
    }

    public void setDateOfBirth(Date date) {
        this.dateOfBirth = date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

    }

}
