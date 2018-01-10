package io.sunshower.sdk.core;

import io.sunshower.sdk.core.model.ActivationElement;
import io.sunshower.sdk.lang.BooleanElement;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("activate")
public interface ActivationEndpoint {

    @GET
    BooleanElement isActive();
    
    @POST
    @Path("/")
    ActivationElement activate(PrincipalElement element);
    
    
    
}
