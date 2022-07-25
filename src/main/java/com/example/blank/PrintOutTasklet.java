package com.example.blank;


import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


@StepScope
public class PrintOutTasklet implements Tasklet {

    private String message;

    public PrintOutTasklet(String message) {
        this.message = message;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println(message);
        return RepeatStatus.FINISHED;
    }
}
