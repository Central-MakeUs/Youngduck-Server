package com.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
public class HelloJobConfiguration {

    private final JobBuilder jobBuilderFactory;
    private final StepBuilder stepBuilderFactory;
    @Bean
    public Job myJob(JobRepository jobRepository, Step a) {
        return new JobBuilder("myJob", jobRepository)
                .start(a)
                .build();
    }

}