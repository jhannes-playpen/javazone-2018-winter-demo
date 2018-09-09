package com.soprasteria.johannes.winter.demo;

import com.soprasteria.johannes.winter.framework.config.ApplicationPropertySource;

public class DemoWinterContext implements HelloContext {

    private ApplicationPropertySource props;

    public DemoWinterContext(ApplicationPropertySource propertySource) {
        this.props = propertySource;
    }

    @Override
    public String getGreeting() {
        return props.required("hello.greeting");
    }

}
