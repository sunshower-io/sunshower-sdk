package io.sunshower.sdk.channel;

import io.sunshower.common.Identifier;
import io.sunshower.common.rs.ClassAdapter;
import io.sunshower.common.rs.IdentifierConverter;
import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "message")
public class Message extends AbstractElement<Message> {

  {
    setType(Message.class);
  }

  @XmlAttribute(name = "media-type")
  private String mediaType;

  @XmlAttribute(name = "payload-type")
  @XmlJavaTypeAdapter(ClassAdapter.class)
  private Class<?> payloadType;

  @XmlElement private String payload;

  @XmlAttribute
  @XmlJavaTypeAdapter(IdentifierConverter.class)
  private Identifier channelId;
}
