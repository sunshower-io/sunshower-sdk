package io.sunshower.sdk.core.jaxrs;

import io.sunshower.common.Identifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Locale;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 * Eh. Not properly named--this is the place to put all parameter converters. Don't want to refactor
 * right now.
 */
@Provider
public class IdentifierParameterConverter implements ParamConverterProvider {

  @Override
  @SuppressWarnings("unchecked")
  public <T> ParamConverter<T> getConverter(
      Class<T> rawType, Type genericType, Annotation[] annotations) {
    if (Identifier.class.equals(rawType)) {
      return (ParamConverter<T>) new IdentifierParamConverter();
    } else if (Locale.class.equals(rawType)) {
      return (ParamConverter<T>) new LocaleParameterConverter();
    }
    return null;
  }

  private static class IdentifierParamConverter implements ParamConverter<Identifier> {

    @Override
    public Identifier fromString(String value) {
      if (value == null) {
        return null;
      }
      return Identifier.valueOf(value);
    }

    @Override
    public String toString(Identifier value) {
      if (value == null) {
        return null;
      }
      return value.toString();
    }
  }
}
