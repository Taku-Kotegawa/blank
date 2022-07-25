package com.example.blank.config;

import com.example.blank.PrintOutTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    Job job(Step step0) {
        return jobBuilderFactory.get("JOB_ID")
                .incrementer(new RunIdIncrementer())
                .start(step0)
                .build();
    }

    @Bean
    Step step0() {
        return stepBuilderFactory.get("step0")
                .tasklet(new PrintOutTasklet("step0を実行します。"))
                .build();
    }

}
