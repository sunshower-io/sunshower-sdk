package io.sunshower.sdk.test;

import static org.mockito.Mockito.mock;

import io.sunshower.kernel.api.PluginManager;
import io.sunshower.persistence.Dialect;
import io.sunshower.sdk.channel.ChannelSelector;
import io.sunshower.sdk.channel.MediaTypeInterceptor;
import io.sunshower.sdk.channel.ReactiveChannelSelector;
import io.sunshower.service.model.io.FileResolutionStrategy;
import io.sunshower.test.common.TestClasspath;
import io.sunshower.test.common.TestConfigurations;
import io.sunshower.test.persist.ConnectionDetectingJDBCTemplate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.sql.DataSource;
import org.jboss.resteasy.plugins.providers.sse.SseEventProvider;
import org.jboss.resteasy.plugins.providers.sse.SseEventSinkInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SdkTestConfiguration {

  //  @Bean
  //  public MoxySseEventProvider moxySseEventProvider() {
  //    final MoxySseEventProvider provider = new MoxySseEventProvider(new MOXyJsonProvider());
  //    ResteasyProviderFactory.getInstance().registerProviderInstance(provider);
  //    return provider;
  //  }

  @Bean
  public SseEventProvider sseEventProvider() {
    return new SseEventProvider();
  }

  @Bean
  public SseEventSinkInterceptor sseEventInterceptor() {
    return new SseEventSinkInterceptor();
  }

  @Bean
  public ChannelSelector channelSelector(ExecutorService service) {
    return new ReactiveChannelSelector(service);
  }

  @Bean
  public ChannelEndpoint channelEndpoint() {
    return new DefaultChannelEndpoint();
  }

  @Bean
  public MediaTypeInterceptor mediaTypeInterceptor() {
    return new MediaTypeInterceptor();
  }

  @Bean
  @Primary
  public JdbcTemplate jdbcTemplate(DataSource datasource) {
    return new ConnectionDetectingJDBCTemplate(datasource);
  }

  @Bean
  public PluginManager pluginManager() {
    return mock(PluginManager.class);
  }

  @Primary
  @Bean(name = TestConfigurations.TEST_CONFIGURATION_REPOSITORY_PATH)
  public String location() {
    return TestClasspath.rootDir()
        .getParent()
        .resolve("sdk-core/src/test/resources")
        .toFile()
        .getAbsolutePath();
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
