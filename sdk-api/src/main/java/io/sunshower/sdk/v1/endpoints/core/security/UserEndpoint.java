package io.sunshower.sdk.v1.endpoints.core.security;

import io.sunshower.sdk.v1.model.core.security.PrincipalElement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("security/users")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface UserEndpoint {

    @GET
    @Path("status/{active}")
    List<PrincipalElement> list(@PathParam("active") boolean active);


}
