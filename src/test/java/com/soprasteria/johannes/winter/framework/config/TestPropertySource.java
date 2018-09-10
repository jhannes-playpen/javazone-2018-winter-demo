package com.soprasteria.johannes.winter.framework.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestPropertySource extends ApplicationPropertySource {



    protected static class Properties implements PropertySource {

        private Map<String, String> properties = new HashMap<>();

        @Override
        public Optional<String> property(String key) {
            return Optional.ofNullable(properties.get(key));
        }

    }

    private Properties properties;

    public TestPropertySource() {
        this(new Properties());
    }

    public TestPropertySource(Properties properties) {
        super(properties);
        this.properties = properties;
    }

    public void add(String key, String value) {
        properties.properties.put(key, value);
    }

}
