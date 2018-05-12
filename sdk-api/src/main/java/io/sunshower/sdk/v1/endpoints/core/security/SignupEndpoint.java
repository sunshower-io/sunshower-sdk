package io.sunshower.sdk.v1.endpoints.core.security;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.lang.IdentifierElement;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationConfirmationElement;
import io.sunshower.sdk.v1.model.core.security.RegistrationRequestElement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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

  @GET
  @Path("/approved")
  List<PrincipalElement> approvedUsers();

  @GET
  List<RegistrationRequestElement> list();

  @GET
  @Path("{id}/approve")
  IdentifierElement approve(@PathParam("id") String id);

  @GET
  @Path("{id}/revoke")
  String revoke(@PathParam("id") Identifier id);

  @DELETE
  @Path("{id}")
  RegistrationRequestElement delete(@PathParam("id") String id);

  @POST
  RegistrationConfirmationElement signup(RegistrationRequestElement request);
}
