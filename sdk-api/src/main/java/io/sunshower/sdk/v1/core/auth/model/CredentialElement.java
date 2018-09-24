package io.sunshower.sdk.v1.core.auth.model;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import javax.xml.bind.annotation.XmlRootElement;

/** Created by gumerman on 5/22/17. */
@XmlRootElement(name = "credential")
public class CredentialElement extends AbstractElement<CredentialElement> {

  public CredentialElement() {
    super(CredentialElement.class);
  }
}
