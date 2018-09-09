package com.soprasteria.johannes.winter.demo.person;

import java.util.List;

import com.soprasteria.johannes.winter.framework.http.server.HttpActionSelector;
import com.soprasteria.johannes.winter.framework.http.server.HttpController;

public class PersonController implements HttpController {

    private PersonContext context;

    public PersonController(PersonContext context) {
        this.context = context;
    }

    @Override
    public void handle(HttpActionSelector selector) {
        selector.onGet("/", action -> {
            action.respondWithJson(handleGet());
        });
        selector.onPost("/", action -> {
            action.respondWithJson(handlePost(action.readJson(Person.class)));
        });
    }

    private List<Person> handleGet() {
        return context.getPersonRepository().listAll();
    }

    private Person handlePost(Person person) {
        return context.getPersonRepository().create(person);
    }

}
