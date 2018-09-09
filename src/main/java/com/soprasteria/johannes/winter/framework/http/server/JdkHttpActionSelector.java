package com.soprasteria.johannes.winter.framework.http.server;

import java.util.function.Consumer;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
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
        if (pathMatches(pathPattern)) {
            handle(action);
        }
    }

    private void handle(Consumer<HttpAction> action) {
        action.accept(new JdkHttpAction(exchange));
        this.isHandled = true;
    }

    private boolean pathMatches(String pathPattern) {
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
            } else if (!patternParts[i].equals(actualParts[i])) {
                return false;
            }
        }

        return true;
    }

    public void onNoMatch(Consumer<HttpAction> action) {
        if (!isHandled) {
            action.accept(new JdkHttpAction(exchange));
        }
    }

}
