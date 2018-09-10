package com.soprasteria.johannes.winter.demo.cats;

import java.util.List;

import com.soprasteria.johannes.winter.framework.http.server.HttpActionSelector;
import com.soprasteria.johannes.winter.framework.http.server.HttpController;

public class CatsController implements HttpController {

    private CatsContext applicationContext;

    public CatsController(CatsContext applicationContext) {
        this.applicationContext = applicationContext;
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
            action.respondWithJson(handlePost(action.readJson(Cat.class)));
        });
    }

    private Cat handlePost(Cat cat) {
        getRepository().create(cat);
        return cat;
    }

    private Cat handleGet(String id) {
        return getRepository().retrieve(id);
    }

    private List<Cat> handleGet() {
        return getRepository().listAll();
    }

    private CatRepository getRepository() {
        return applicationContext.getCatRepository();
    }

}
