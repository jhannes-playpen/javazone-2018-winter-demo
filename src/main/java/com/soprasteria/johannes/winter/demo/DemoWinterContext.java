package com.soprasteria.johannes.winter.demo;

import com.soprasteria.johannes.winter.demo.person.PersonContext;
import com.soprasteria.johannes.winter.demo.person.PersonRepository;
import com.soprasteria.johannes.winter.framework.config.ApplicationPropertySource;

public class DemoWinterContext implements HelloContext, PersonContext {

    private ApplicationPropertySource props;

    public DemoWinterContext(ApplicationPropertySource propertySource) {
        this.props = propertySource;
    }

    @Override
    public String getGreeting() {
        return props.required("hello.greeting");
    }

    @Override
    public PersonRepository getPersonRepository() {
        // TODO Auto-generated method stub
        return null;
    }

}
