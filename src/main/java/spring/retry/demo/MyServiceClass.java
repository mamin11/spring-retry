package spring.retry.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class MyServiceClass {

    public void doSomething() {
        log.debug("doSomething");
        retryTemplate().execute(context -> {
            doSomethingElse();
            return null;
        });
    }

    public void doSomethingSafely() {
        log.debug("doSomethingSafely");
        retryTemplate().execute(context -> {
            doSomethingElse();
            return null;
        }, context -> {
            // custom recovery logic
            log.debug("retry exhausted");
            return context.getLastThrowable();
        });
    }

    @SneakyThrows
    private void doSomethingElse() {
        log.debug("doSomethingElse");
        throw new TimeoutException("timeout");
    }

    @Bean
    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(4)
                .fixedBackoff(2000L)
                .retryOn(TimeoutException.class)
                .build();
    }
}