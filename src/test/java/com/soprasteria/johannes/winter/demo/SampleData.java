package com.soprasteria.johannes.winter.demo;

import java.time.LocalDate;
import java.util.Random;

import com.soprasteria.johannes.winter.demo.cats.Cat;
import com.soprasteria.johannes.winter.demo.person.Person;

public class SampleData {

    private static Random random = new Random();

    public static Person samplePerson() {
        Person person = new Person();
        person.setGivenName(sampleGivenName());
        person.setFamilyName(sampleFamilyName());
        person.setDateOfBirth(sampleDateAfter(LocalDate.now().minusYears(50), 50));
        return person;
    }

    public static String sampleFamilyName() {
        return pickOne(new String[] { "Olsen", "Singh", "Jones", "Lee" });
    }

    public static String sampleGivenName() {
        return pickOne(new String[] {"James", "Jack", "Jill", "Joanne" });
    }

    public static LocalDate sampleDateAfter(LocalDate startingDate, int yearsRange) {
        return startingDate.plusDays(random.nextInt(yearsRange * 365));
    }

    static String pickOne(String[] strings) {
        return strings[random.nextInt(strings.length)];
    }

    public static Cat sampleCat() {
        Cat cat = new Cat();
        cat.setName(pickOne(new String[] { "Garfield", "Whiskers", "Socks", "Boots" }));
        cat.setDateOfBirth(sampleDateAfter(LocalDate.now().minusYears(20), 20));
        return cat;
    }

}
