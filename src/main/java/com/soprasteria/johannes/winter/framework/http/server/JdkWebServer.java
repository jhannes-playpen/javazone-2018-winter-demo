package com.soprasteria.johannes.winter.framework.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.soprasteria.johannes.winter.framework.ExceptionUtil;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class JdkWebServer implements WebServer {

    private HttpServer server;

    public JdkWebServer(int port) {
        try {
            server = com.sun.net.httpserver.HttpServer.create();
            server.bind(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            throw ExceptionUtil.soften(e);
        }
    }

    @Override
    public void start() {
        server.start();
    }

    @Override
    public void await() {
    }

    @Override
    public int getActualPort() {
        return server.getAddress().getPort();
    }

    @Override
    public void mapPathToController(String path, HttpController controller) {
        String pathTranslated = sanitizePath(path);
        server.createContext(pathTranslated, exchange -> {
            JdkHttpActionSelector selector = new JdkHttpActionSelector(pathTranslated, exchange);
            controller.handle(selector);
            selector.onNoMatch(a -> a.respondNotFound());
        });
    }

    static String sanitizePath(String path) {
        String pathTranslated = path;
        if (path.endsWith("/*")) {
            pathTranslated = path.substring(0, path.length() - 2);
        }
        if (pathTranslated.isEmpty()) {
            pathTranslated = "/";
        }
        return pathTranslated;
    }

}
