package io.sunshower.sdk.v1.endpoints.core.security;

import io.sunshower.sdk.v1.model.core.security.PrincipalElement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * Created by haswell on 5/11/17.
 */
@Path("security/users")
public interface UserEndpoint {

    @GET
    @Path("status/{active}")
    List<PrincipalElement> list(@PathParam("active") boolean active);


}
