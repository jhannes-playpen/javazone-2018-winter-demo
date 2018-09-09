package com.soprasteria.johannes.winter.framework.http.server;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.soprasteria.johannes.winter.framework.ExceptionUtil;
import com.sun.net.httpserver.HttpExchange;


@SuppressWarnings("restriction")
public class JdkHttpAction implements HttpAction {

    private HttpExchange exchange;

    public JdkHttpAction(HttpExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void respondWithString(String string) {
        sendContent(200, "text/plain", string.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void respondNotFound() {
        sendContent(404, "text/plain", ("Not Found - " + exchange.getRequestURI().getPath()).getBytes());
    }

    private void sendContent(int responseCode, String contentType, byte[] bytes) {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        try {
            exchange.sendResponseHeaders(responseCode, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();
        } catch (IOException e) {
            throw ExceptionUtil.soften(e);
        }
    }

}
