package io.sunshower.sdk.v1;

import io.sunshower.sdk.common.jaxb.CachingJAXBContextAwareMOxyJSONProvider;
import io.sunshower.sdk.common.jaxb.DefaultJAXBContextResolver;
import io.sunshower.sdk.core.IdentifierEndpoint;
import io.sunshower.sdk.v1.core.FlakeIdentifierEndpoint;
import io.sunshower.sdk.v1.core.security.DefaultSignupEndpoint;
import io.sunshower.sdk.v1.core.security.DefaultUserEndpoint;
import io.sunshower.sdk.v1.core.security.DefaultSecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SecurityEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.SignupEndpoint;
import io.sunshower.sdk.v1.endpoints.core.security.UserEndpoint;
import io.sunshower.service.serialization.DynamicJaxrsProviders;
import io.sunshower.service.serialization.DynamicResolvingMoxyJsonProvider;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SdkConfiguration {
    
    
    @Bean
    public IdentifierEndpoint identifierEndpoint() {
        return new FlakeIdentifierEndpoint();
    }


    @Bean
    public UserEndpoint userEndpoint() {
        return new DefaultUserEndpoint();
    }

    @Bean
    public SignupEndpoint signupEndpoint() {
        return new DefaultSignupEndpoint();
    }

    @Bean
    public SecurityEndpoint securityEndpoint() {
        return new DefaultSecurityEndpoint();
    }



    @Bean
    public ExceptionMappings exceptionMappings() {
        return new ExceptionMappings();
    }

    @Bean
    public DynamicResolvingMoxyJsonProvider configurableMoxyJsonProvider(DynamicJaxrsProviders providers) {
        return new CachingJAXBContextAwareMOxyJSONProvider(providers);
    }

    @Bean
    public DefaultJAXBContextResolver jaxbContextResolver(final MOXyJsonProvider provider) {
        return new DefaultJAXBContextResolver(((CachingJAXBContextAwareMOxyJSONProvider) provider));
    }

}
