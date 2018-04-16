package io.sunshower.sdk.common.jaxb;

import io.sunshower.service.serialization.DynamicResolvingMoxyJsonProvider;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.lang.annotation.Annotation;
import java.util.Set;

@Provider
@Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD, "application/x-javascript"})
@Consumes({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
public class CachingJAXBContextAwareMOxyJSONProvider extends DynamicResolvingMoxyJsonProvider {

  public CachingJAXBContextAwareMOxyJSONProvider(Providers providers) {
    super(providers);
    this.setWrapperAsArrayName(true);
  }

  @Override
  public JAXBContext getJAXBContext(
      Set<Class<?>> domainClasses,
      Annotation[] annotations,
      MediaType mediaType,
      MultivaluedMap<String, ?> httpHeaders)
      throws JAXBException {
    return super.getJAXBContext(domainClasses, annotations, mediaType, httpHeaders);
  }
}
