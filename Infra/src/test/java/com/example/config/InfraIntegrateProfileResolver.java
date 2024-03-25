package com.example.config;

import org.springframework.test.context.ActiveProfilesResolver;

public class InfraIntegrateProfileResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(Class<?> testClass) {
        // some code to find out your active profiles
        return new String[] { "local","infra","core"};
    }
}