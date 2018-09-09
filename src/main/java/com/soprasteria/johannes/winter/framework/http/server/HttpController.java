package com.soprasteria.johannes.winter.framework.http.server;

public interface HttpController {

    void handle(HttpActionSelector selector);

}
