package io.sunshower.sdk.v1.model.core.element;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/** Created by haswell on 3/17/17. */
@XmlRootElement(name = "message")
public class MessageElement<C> {

  @XmlElement(name = "contents")
  private String message;

  @XmlAnyElement(lax = true)
  private Object payload;

  public MessageElement() {}

  public MessageElement(String message, Object payload) {
    this.message = message;
    this.payload = payload;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @SuppressWarnings("unchecked")
  public C getPayload() {
    return (C) payload;
  }

  public void setPayload(C payload) {
    this.payload = payload;
  }
}
