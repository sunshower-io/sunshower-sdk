package io.sunshower.sdk.channel;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

@Provider
public class MediaTypeInterceptor implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    MediaType mediaType = requestContext.getMediaType();
    if (mediaType == null) {
      String accept = requestContext.getHeaderString("Accept");
      if (accept != null) {
        String[] values = accept.split("\\s*,\\s*");
        if (values.length > 0) {
          mediaType = MediaType.valueOf(values[0]);
        }
      }
    }
    if (mediaType == null) {
      mediaType = MediaType.APPLICATION_JSON_TYPE;
    }
    ResteasyProviderFactory.getContextDataMap().put(MediaType.class, mediaType);
  }
}
