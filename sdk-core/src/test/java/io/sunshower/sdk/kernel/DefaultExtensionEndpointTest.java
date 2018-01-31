package io.sunshower.sdk.kernel;

import io.sunshower.model.core.auth.Role;
import io.sunshower.sdk.test.SdkTest;
import io.sunshower.service.security.PermissionsService;
import io.sunshower.test.ws.Remote;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

class DefaultExtensionEndpointTest extends SdkTest {
   
    @Remote
    private ExtensionEndpoint extensionEndpoint;
   
    @Inject
    private PermissionsService<?> permissionsService;
    
    @Test
    public void ensureListingExtensionPointWorks() {
        permissionsService.impersonate(() -> {
            assertThat(extensionEndpoint.list().size(), is(0));
        }, new Role("admin"));
    }

}