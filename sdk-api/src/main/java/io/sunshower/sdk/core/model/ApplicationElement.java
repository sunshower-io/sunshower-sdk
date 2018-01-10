package io.sunshower.sdk.core.model;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@XmlRootElement
public class ApplicationElement extends AbstractElement<ApplicationElement> {
   
    @XmlElement
    private String name;

    @XmlAttribute
    private boolean enabled;


    @XmlElement
    private String location;

    
    @XmlAttribute(name = "instance-id")
    private String instanceId;
    
    @XmlAttribute(name = "started-on")
    private Date instanceStarted;
    
    @XmlAttribute(name = "last-shutdown")
    private Date lastSunshower;
   
    
    @XmlElement
    private VersionElement version;
    
    
}
