package io.sunshower.sdk.v1.endpoints.core.security;

import io.sunshower.sdk.v1.model.core.security.AuthenticationElement;
import io.sunshower.sdk.v1.model.core.security.AuthenticationRequest;
import io.sunshower.sdk.v1.model.core.security.AuthenticationTokenElement;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML,
})

@Consumes({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML,
})
@Path("security")
public interface SecurityEndpoint {


    /**
     *
     * @param element
     */
    @PUT
    @Path("validate")
    void validate(AuthenticationTokenElement element);

    /**
     *
     * @param request
     * @return
     */

    @PUT
    @Path("authenticate")
    AuthenticationElement authenticate(AuthenticationRequest request);



    @PUT
    @Path("token/authenticate")
    AuthenticationElement authenticate(AuthenticationTokenElement token);


}
