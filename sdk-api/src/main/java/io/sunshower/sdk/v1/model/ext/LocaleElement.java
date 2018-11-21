package io.sunshower.sdk.v1.model.ext;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import java.util.Locale;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "locale")
public class LocaleElement extends AbstractElement<LocaleElement> {
  {
    setType(LocaleElement.class);
  }

  @XmlAttribute(name = "display-name")
  private String displayName;

  @XmlAttribute(name = "locale-key")
  private String localeKey;

  public static LocaleElement valueOf(Locale locale) {
    final LocaleElement el = new LocaleElement();
    el.setLocaleKey(locale.toLanguageTag());
    el.setDisplayName(locale.getDisplayName());
    return el;
  }
}
