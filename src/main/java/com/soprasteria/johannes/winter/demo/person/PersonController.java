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
        selector.onGet("/{id}", action -> {
            action.respondWithJson(handleGet(action.pathParam("id")));
        });
        selector.onPost("/", action -> {
            action.respondWithJson(handlePost(action.readJson(Person.class)));
        });
    }

    private Person handleGet(String id) {
        return context.getPersonRepository().retrieve(id);
    }

    private List<Person> handleGet() {
        return context.getPersonRepository().listAll();
    }

    private Person handlePost(Person person) {
        return context.getPersonRepository().create(person);
    }

}
