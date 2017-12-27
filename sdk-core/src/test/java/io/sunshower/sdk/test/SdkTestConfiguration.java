package io.sunshower.sdk.test;

import io.sunshower.persistence.Dialect;
import io.sunshower.service.model.io.FileResolutionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by haswell on 5/5/17.
 */
@Configuration
public class SdkTestConfiguration {
    



    @Bean
    public FileResolutionStrategy fileResolutionStrategy() {
        return new MockRepositoryResolutionStrategy();
    }

    @Bean
    public Dialect databaseDialect() {
        return Dialect.Postgres;
    }


    @Bean
    public ExecutorService executorService() {
        return new ScheduledThreadPoolExecutor(4);
    }
}
