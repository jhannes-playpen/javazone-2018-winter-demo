package com.soprasteria.johannes.winter.demo;

import com.soprasteria.johannes.winter.framework.http.server.HttpActionSelector;
import com.soprasteria.johannes.winter.framework.http.server.HttpController;

public class HelloController implements HttpController {

    private HelloContext helloContext;

    public HelloController(HelloContext helloContext) {
        this.helloContext = helloContext;
    }

    @Override
    public void handle(HttpActionSelector selector) {
        selector.onGet("/", action -> {
            action.respondWithString(helloContext.getGreeting());
        });
    }

}
