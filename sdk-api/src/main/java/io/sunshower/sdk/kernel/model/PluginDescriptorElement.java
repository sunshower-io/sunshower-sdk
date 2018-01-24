package io.sunshower.sdk.kernel.model;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plugin")
public class PluginDescriptorElement extends AbstractElement<PluginDescriptorElement> {
    
    {
        setType(PluginDescriptorElement.class);
    }
   

    @XmlElement(name = "coordinates")
    private PluginCoordinateElement coordinates;
    
    
    
    
    
    
    
    
}
