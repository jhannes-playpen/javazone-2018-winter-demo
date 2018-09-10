package com.soprasteria.johannes.winter.demo.cats;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.soprasteria.johannes.winter.demo.SampleData;
import org.junit.Test;

public abstract class CatRepositoryTest {

    protected abstract CatRepository getRepository();

    @Test
    public void shouldFindSavedPerson() {
        Cat cat = SampleData.sampleCat();
        getRepository().create(cat);

        List<Cat> all = getRepository().listAll();
        assertThat(all)
            .contains(cat);
    }

    @Test
    public void shouldRetriveSavedPerson() {
        Cat cat = SampleData.sampleCat();
        getRepository().create(cat);
        assertThat(cat).hasNoNullFieldsOrProperties();

        assertThat(getRepository().retrieve(cat.getId()))
            .isEqualToComparingFieldByField(cat);
    }

}
