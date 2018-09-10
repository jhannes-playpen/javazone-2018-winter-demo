package com.soprasteria.johannes.winter.demo.person;

import static org.assertj.core.api.Assertions.assertThat;

import com.soprasteria.johannes.winter.demo.SampleData;
import org.junit.Test;

public abstract class PersonRepositoryTest {

    protected abstract PersonRepository getPersonRepository();

    @Test
    public void shouldFindSavedPerson() {
        Person person = SampleData.samplePerson();
        getPersonRepository().create(person);

        assertThat(getPersonRepository().listAll())
            .contains(person);
    }

    @Test
    public void shouldRetriveSavedPerson() {
        Person person = SampleData.samplePerson();
        getPersonRepository().create(person);
        assertThat(person).hasNoNullFieldsOrProperties();

        assertThat(getPersonRepository().retrieve(person.getId()))
            .isEqualToComparingFieldByField(person);
    }
}
