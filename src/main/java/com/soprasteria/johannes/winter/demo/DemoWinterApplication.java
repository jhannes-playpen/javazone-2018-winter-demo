package com.soprasteria.johannes.winter.demo;

import java.util.Optional;

import com.soprasteria.johannes.winter.framework.config.ApplicationPropertySource;
import com.soprasteria.johannes.winter.framework.http.server.WebServer;
import com.soprasteria.johannes.winter.framework.http.server.jdk.JdkWebServer;

public class DemoWinterApplication {

    private int port;
    private DemoWinterContext applicationContext;
    private WebServer server;

    public DemoWinterApplication(int port) {
        server = new JdkWebServer(port);
    }

    public static void main(String[] args) {
        int port = Optional.ofNullable(System.getenv("HTTP_PORT")).map(Integer::parseInt).orElse(8080);
        new DemoWinterApplication(port).run(args);
    }

    private void run(String[] args) {
        setApplicationContext(new DemoWinterContext(new ApplicationPropertySource(System.getProperty("PROFILES"))));
        server.mapPathToController("/", new HelloController(applicationContext));
        server.start();
        System.out.println("Started on " + getActualPort());
        server.await();
    }

    private void setApplicationContext(DemoWinterContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public int getActualPort() {
        return server.getActualPort();
    }

}
