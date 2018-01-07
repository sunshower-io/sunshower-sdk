package io.sunshower.sdk.kernel;

import io.sunshower.sdk.kernel.model.PluginState;
import io.sunshower.sdk.kernel.model.PluginUploadFault;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("plugins")
public interface PluginEndpoint {


    @POST
    @Path("deploy")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void deploy(
            @Context HttpServletRequest req,
            @Context HttpServletResponse res
    ) throws PluginUploadFault;


    @PUT
    @Path("{id}/{state}")
    void setState(
            @PathParam("id") String pluginId,
            @PathParam("state") PluginState state
    );

    @GET
    @Path("{id}/state")
    PluginState getState(@PathParam("id") String id);

}
