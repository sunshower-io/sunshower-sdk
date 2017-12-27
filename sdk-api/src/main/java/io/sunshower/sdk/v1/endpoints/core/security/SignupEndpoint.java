package io.sunshower.sdk.v1.endpoints.core.security;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by haswell on 5/5/17.
 */
@Path("signup")
@Produces({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML,
})

@Consumes({
        MediaType.APPLICATION_JSON,
        MediaType.APPLICATION_XML,
})
public interface SignupEndpoint {


    @PUT
    void signup(RegistrationRequestElement request);

    @GET
    List<RegistrationRequestElement> list();

    @GET
    @Path("{id}/approve")
    String approve(@PathParam("id") String id);


    @GET
    @Path("{id}/revoke")
    String revoke(@PathParam("id") Identifier id);



}
