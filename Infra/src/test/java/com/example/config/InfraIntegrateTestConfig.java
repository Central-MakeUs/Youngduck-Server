package com.example.config;

import com.example.CoreApplication;
import com.example.InfraApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {InfraApplication.class, CoreApplication.class})
public class InfraIntegrateTestConfig {
}
