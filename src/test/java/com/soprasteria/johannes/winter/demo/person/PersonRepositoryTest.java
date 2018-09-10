package com.soprasteria.johannes.winter.demo.person;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Random;

import org.junit.Test;

public abstract class PersonRepositoryTest {

    protected abstract PersonRepository getPersonRepository();

    private Random random = new Random();

    @Test
    public void shouldFindSavedPerson() {
        Person person = samplePerson();
        getPersonRepository().create(person);

        assertThat(person).hasNoNullFieldsOrProperties();
        assertThat(getPersonRepository().listAll())
            .contains(person);
    }

    private Person samplePerson() {
        Person person = new Person();
        person.setGivenName(pickOne(new String[] {"James", "Jack", "Jill", "Joanne" }));
        person.setFamilyName(pickOne(new String[] { "Olsen", "Singh", "Jones", "Lee" }));
        person.setDateOfBirth(sampleDateAfter(LocalDate.now().minusYears(50), 50));
        return person;
    }

    private LocalDate sampleDateAfter(LocalDate startingDate, int yearsRange) {
        return startingDate.plusDays(random.nextInt(yearsRange * 365));
    }

    private String pickOne(String[] strings) {
        return strings[random.nextInt(strings.length)];
    }

}
