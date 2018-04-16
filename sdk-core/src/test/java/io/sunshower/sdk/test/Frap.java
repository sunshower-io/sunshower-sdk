package io.sunshower.sdk.test;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Frap {

  @XmlAttribute private String name = "hello world";
}
