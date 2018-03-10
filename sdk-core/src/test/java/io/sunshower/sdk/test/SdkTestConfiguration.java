package io.sunshower.sdk.test;

import io.sunshower.kernel.api.PluginManager;
import io.sunshower.persistence.Dialect;
import io.sunshower.service.model.io.FileResolutionStrategy;
import io.sunshower.test.common.TestConfigurations;
import io.sunshower.test.persist.ConnectionDetectingJDBCTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class SdkTestConfiguration {
    
    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate(DataSource datasource) {
        return new ConnectionDetectingJDBCTemplate(datasource);
    }
    
    
    
    @Bean
    public PluginManager pluginManager() {
        return new MockPluginManager();
    }
    

    @Primary
    @Bean(name = TestConfigurations.TEST_CONFIGURATION_REPOSITORY_PATH)
    public String location() {
        return "/sdk-core/src/test/resources";
    }

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
