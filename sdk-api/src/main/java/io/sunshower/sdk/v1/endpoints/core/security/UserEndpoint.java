package io.sunshower.sdk.v1.endpoints.core.security;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.lang.BooleanElement;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.sdk.v1.model.ext.LocaleElement;
import java.util.List;
import java.util.Locale;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("security/users")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public interface UserEndpoint {

  @GET
  @Path("locales")
  List<LocaleElement> getLocales();

  @GET
  @Path("locales/{key}")
  List<LocaleElement> getLocales(Locale locale);

  @GET
  @Path("{id}/details")
  PrincipalElement get(@PathParam("id") Identifier userId);

  @PUT
  @Path("{id}/details")
  void update(@PathParam("id") Identifier userId, PrincipalElement element);

  @DELETE
  @Path("/{id}")
  BooleanElement delete(@PathParam("id") Identifier userid);

  @GET
  @Path("status/{active}")
  List<PrincipalElement> list(@PathParam("active") boolean active);
}
