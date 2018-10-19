package io.sunshower.sdk.core.jaxrs;

import java.util.Locale;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocaleAdapter extends XmlAdapter<String, Locale> {

  @Override
  public Locale unmarshal(String v) throws Exception {
    if (v == null) {
      return null;
    }
    return Locale.forLanguageTag(v);
  }

  @Override
  public String marshal(Locale v) throws Exception {
    if (v == null) {
      return null;
    }
    return v.toLanguageTag();
  }
}
