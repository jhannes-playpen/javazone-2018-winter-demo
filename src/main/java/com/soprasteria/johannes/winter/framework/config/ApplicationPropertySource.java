package com.soprasteria.johannes.winter.framework.config;

import java.io.File;
import java.util.Optional;

public class ApplicationPropertySource {

    private PropertySourceList list;

    public ApplicationPropertySource(String profiles) {
        this(new File("."), profiles != null ? profiles.split(",\\*") : new String[0]);
    }

    public ApplicationPropertySource(File configurationDirectory, String[] activeProfiles) {
        this.list = new PropertySourceList();
        for (String profile : activeProfiles) {
            list.addPropertySource(new FilePropertySource(new File(configurationDirectory, "application-" + profile + ".properties")));
            list.addPropertySource(new ClasspathPropertySource("/application-" + profile + ".properties"));
        }
        list.addPropertySource(new FilePropertySource(new File(configurationDirectory, "application.properties")));
        list.addPropertySource(new ClasspathPropertySource("/application.properties"));
    }

    public String required(String key) {
        return property(key).orElseThrow(() -> new RuntimeException("Missing required property " + key));
    }

    private Optional<String> property(String key) {
        return list.property(key);
    }

}
