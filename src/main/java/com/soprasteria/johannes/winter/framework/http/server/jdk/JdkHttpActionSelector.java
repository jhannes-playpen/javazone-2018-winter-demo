package com.soprasteria.johannes.winter.framework.http.server.jdk;

import java.util.HashMap;
import java.util.function.Consumer;

import com.soprasteria.johannes.winter.framework.http.server.HttpAction;
import com.soprasteria.johannes.winter.framework.http.server.HttpActionSelector;
import com.sun.net.httpserver.HttpExchange;

class JdkHttpActionSelector implements HttpActionSelector {

    private String path;
    private HttpExchange exchange;
    private boolean isHandled = false;

    public JdkHttpActionSelector(String path, HttpExchange exchange) {
        this.path = path;
        this.exchange = exchange;
    }

    @Override
    public void onGet(String pathPattern, Consumer<HttpAction> action) {
        HashMap<String, String> pathParameters = new HashMap<>();
        if (exchange.getRequestMethod().equals("GET") && pathMatches(pathPattern, pathParameters)) {
            handle(action, pathParameters);
        }
    }

    @Override
    public void onPost(String pathPattern, Consumer<HttpAction> action) {
        HashMap<String, String> pathParameters = new HashMap<>();
        if (exchange.getRequestMethod().equals("POST") && pathMatches(pathPattern, pathParameters)) {
            handle(action, pathParameters);
        }
    }

    private void handle(Consumer<HttpAction> action, HashMap<String, String> pathParameters) {
        action.accept(new JdkHttpAction(exchange, pathParameters));
        this.isHandled = true;
    }

    private boolean pathMatches(String pathPattern, HashMap<String, String> pathParameters) {
        String pathInfo = exchange.getRequestURI().getPath();
        if (!pathInfo.startsWith(path)) {
            return false;
        }
        pathInfo = pathInfo.substring(path.length());
        if (pathInfo == null || pathInfo.isEmpty()) {
            return pathPattern.equals("/");
        }
        if (pathPattern.startsWith("/") && !pathInfo.startsWith("/")) {
            pathInfo = "/" + pathInfo;
        }
        String[] actualParts = pathInfo.split("/");
        String[] patternParts = pathPattern.split("/");

        if (actualParts.length != patternParts.length) {
            return false;
        }
        for (int i = 0; i < patternParts.length; i++) {
            if (patternParts[i].startsWith("{") && patternParts[i].endsWith("}")) {
                pathParameters.put(patternParts[i].substring(1, patternParts[i].length()-1),
                        actualParts[i]);
            } else if (!patternParts[i].equals(actualParts[i])) {
                return false;
            }
        }

        return true;
    }

    public void onNoMatch(Consumer<HttpAction> action) {
        if (!isHandled) {
            action.accept(new JdkHttpAction(exchange, new HashMap<>()));
        }
    }

}
