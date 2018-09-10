package com.soprasteria.johannes.winter.demo.cats;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class Cat {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    private String id;

    private String name;

    private LocalDate dateOfBirth;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cat)) {
            return false;
        }
        Cat other = (Cat) o;
        return Objects.equals(name, other.name) && Objects.equals(dateOfBirth, other.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name=" + name + ",dateOfBirth=" + dateOfBirth + "}";
    }

    public void setDateOfBirth(Date date) {
        this.dateOfBirth = date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

}
