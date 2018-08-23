package io.sunshower.sdk.core.model;

import io.sunshower.kernel.api.ExtensionCoordinate;
import io.sunshower.kernel.api.ExtensionPointDefinition;
import io.sunshower.sdk.kernel.model.ExtensionPointCoordinateElement;
import io.sunshower.sdk.kernel.model.ExtensionPointDescriptorElement;

public interface Extensions {
  default ExtensionPointDescriptorElement toElement(ExtensionPointDefinition<?> e) {

    if (e == null) {
      return null;
    }
    final ExtensionPointDescriptorElement ele = new ExtensionPointDescriptorElement();
    ele.setCoordinates(toElement(e.getCoordinate()));
    return ele;
  }

  default ExtensionPointCoordinateElement toElement(ExtensionCoordinate coordinate) {
    if (coordinate == null) {
      return null;
    }
    final ExtensionPointCoordinateElement ele = new ExtensionPointCoordinateElement();
    ele.setGroup(coordinate.getGroup());
    ele.setName(coordinate.getName());
    ele.setNamespace(coordinate.getNamespace());
    return ele;
  }
}
