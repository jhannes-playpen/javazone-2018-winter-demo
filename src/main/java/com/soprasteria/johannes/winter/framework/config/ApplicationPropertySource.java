package com.soprasteria.johannes.winter.framework.config;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

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

    public Optional<String> property(String key) {
        return list.property(key);
    }

    private Map<String, DataSource> dataSourceCache = new HashMap<>();

    public DataSource getDataSource(String prefix) {
        String url = required(prefix + ".datasource.url");
        String username = property(prefix + ".datasource.username").orElse(prefix);
        String password = property(prefix + ".datasource.password").orElse(prefix);

        String cacheKey = url + "|" + username + "|" + password;

        return dataSourceCache.computeIfAbsent(cacheKey, key -> {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);

            HikariDataSource dataSource = new HikariDataSource(config);

            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);
            flyway.migrate();

            return dataSource;
        });
    }

    public String propertyChoice(String key, String[] options, String defaultOption) {
        String result = property(key).orElse(defaultOption);
        if (!Arrays.asList(options).contains(result)) {
            throw new IllegalArgumentException("Illegal value for " + key + ": " + result);
        }
        return result;
    }

}
