package io.sunshower.sdk.v1.core;


import io.sunshower.common.Identifier;
import io.sunshower.sdk.core.IdentifierEndpoint;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@EnableAutoConfiguration(exclude = {
        SecurityAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        FlywayAutoConfiguration.class,
})
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlakeIdentifierEndpointTest extends SdkTest {
    
    @Remote
    private IdentifierEndpoint identifierEndpoint;
    
    
    @Test
    public void ensureIdentifierEndpointReturnsId() {
        Identifier id = identifierEndpoint.create();
        assertThat(id, is(not(nullValue())));
    }

}