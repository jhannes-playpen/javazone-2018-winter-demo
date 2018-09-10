package com.soprasteria.johannes.winter.framework.http.server;

public interface HttpAction {

    void respondWithString(String string);

    void respondNotFound();

    void respondWithJson(Object handleGet);

    <T> T readJson(Class<T> targetClass);

    String pathParam(String key);

}
