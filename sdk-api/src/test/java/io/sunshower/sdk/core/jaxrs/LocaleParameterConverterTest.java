package io.sunshower.sdk.core.jaxrs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Locale;
import javax.ws.rs.ext.ParamConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocaleParameterConverterTest {

  private ParamConverter<Locale> localeConverter;

  @BeforeEach
  void setUp() {
    localeConverter = new LocaleParameterConverter();
  }

  @Test
  void ensureParamConverterWorksConvertingFromLanguageTag() {
    String tag = Locale.CANADA.toLanguageTag();
    assertThat(localeConverter.fromString(tag), is(Locale.CANADA));
  }

  @Test
  void ensureParamConverterIsIdempotent() {
    assertThat(
        localeConverter.fromString(localeConverter.toString(Locale.CANADA)), is(Locale.CANADA));
  }
}
