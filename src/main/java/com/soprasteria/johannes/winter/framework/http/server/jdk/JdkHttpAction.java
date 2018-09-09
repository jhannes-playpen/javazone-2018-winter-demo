package com.soprasteria.johannes.winter.framework.http.server.jdk;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.soprasteria.johannes.winter.framework.ExceptionUtil;
import com.soprasteria.johannes.winter.framework.http.server.HttpAction;
import com.sun.net.httpserver.HttpExchange;
import org.jsonbuddy.JsonNode;
import org.jsonbuddy.JsonObject;
import org.jsonbuddy.parse.JsonParser;
import org.jsonbuddy.pojo.JsonGenerator;
import org.jsonbuddy.pojo.PojoMapper;


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

    @Override
    public void respondWithJson(Object object) {
        JsonNode jsonObject = JsonGenerator.generate(object);

        String string = jsonObject.toJson();
        sendContent(200, "application/json", string.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public <T> T readJson(Class<T> targetClass) {
        JsonObject jsonObject = JsonParser.parseToObject(exchange.getRequestBody());
        return PojoMapper.map(jsonObject, targetClass);
    }

    public void respondServerError(Exception e) {
        sendContent(500, "text/plain", e.toString().getBytes());
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
