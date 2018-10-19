package io.sunshower.sdk.core.jaxrs;

import java.util.Locale;
import javax.ws.rs.ext.ParamConverter;

public class LocaleParameterConverter implements ParamConverter<Locale> {
  @Override
  public Locale fromString(String value) {
    return Locale.forLanguageTag(value);
  }

  @Override
  public String toString(Locale value) {
    return value.toLanguageTag();
  }
}
