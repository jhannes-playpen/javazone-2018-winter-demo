package com.soprasteria.johannes.winter.demo.person;

import java.time.LocalDate;
import java.util.Random;

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

}
