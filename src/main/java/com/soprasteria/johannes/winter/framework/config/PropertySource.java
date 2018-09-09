package com.soprasteria.johannes.winter.framework.config;

import java.util.Optional;

public interface PropertySource {

    Optional<String> property(String key);

}
