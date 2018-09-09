package com.soprasteria.johannes.winter.framework.http.server;

import java.util.function.Consumer;

public interface HttpActionSelector {

    void onGet(String path, Consumer<HttpAction> action);

    void onPost(String path, Consumer<HttpAction> action);

}
