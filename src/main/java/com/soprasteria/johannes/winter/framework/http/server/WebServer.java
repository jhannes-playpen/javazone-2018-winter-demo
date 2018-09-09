package com.soprasteria.johannes.winter.framework.http.server;

public interface WebServer {

    void start();

    void await();

    int getActualPort();

    void mapPathToController(String path, HttpController controller);

}
