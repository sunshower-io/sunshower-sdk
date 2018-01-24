package io.sunshower.sdk.kernel.model;

import io.sunshower.sdk.v1.model.core.element.AbstractElement;

public class PluginCoordinateElement extends AbstractElement<PluginCoordinateElement> {

  {
    setType(PluginCoordinateElement.class);
  }

  private String group;
  private String name;
  private String namespace;
}
