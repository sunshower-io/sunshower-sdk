package io.sunshower.sdk.common.jaxb;

import io.sunshower.common.ws.jaxb.JAXBContextResolver;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by haswell on 6/4/17.
 */
public class DefaultJAXBContextResolver implements  JAXBContextResolver {

    private final CachingJAXBContextAwareMOxyJSONProvider provider;

    public DefaultJAXBContextResolver(CachingJAXBContextAwareMOxyJSONProvider provider) {
        this.provider = provider;
    }


    @Override
    public JAXBContext getContext(MediaType mediaType, Class<?>[] classes) {
        try {
            return provider.getJAXBContext(
                    new HashSet<>(Arrays.asList(classes)),
                    new Annotation[0],
                    mediaType,
                    new MultivaluedHashMap<>()
            );
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to resolve context", e);
        }
    }

    @Override
    public JAXBContext getContext(Class<?> aClass, MediaType mediaType) {
        try {
            return provider.getJAXBContext(Collections.singleton(aClass), new Annotation[0], mediaType, new MultivaluedHashMap<>());
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to resolve context", e);
        }
    }
}
