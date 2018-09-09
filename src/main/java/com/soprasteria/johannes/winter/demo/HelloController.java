package com.soprasteria.johannes.winter.demo;

import com.soprasteria.johannes.winter.framework.http.server.HttpActionSelector;
import com.soprasteria.johannes.winter.framework.http.server.HttpController;

public class HelloController implements HttpController {

    @Override
    public void handle(HttpActionSelector selector) {
        selector.onGet("/", action -> {
            action.respondWithString("Hello world");
        });
    }

}
