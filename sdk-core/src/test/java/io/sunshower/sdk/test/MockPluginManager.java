package io.sunshower.sdk.test;

import io.sunshower.kernel.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockPluginManager implements PluginManager {

  private final Map<Class<?>, ExtensionPointDefinition<?>> definitions;

  public MockPluginManager() {
    this.definitions = new HashMap<>();
  }

  @Override
  public List<ExtensionPointDefinition<?>> getExtensionPoints() {
    return new ArrayList<>(definitions.values());
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T resolve(Class<T> extension) {
    try {
      return (T) definitions.get(extension).getExtensionPoint().newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T> void register(Class<T> extensionPoint, T instance) {
    definitions.put(extensionPoint, new MockDefinition(extensionPoint, instance));
  }

  @Override
  public <T> void register(Class<T> extensionPoint, T instance, ExtensionMetadata metadata) {}

  private class MockDefinition<T> implements ExtensionPointDefinition<T> {
    private final T instance;
    private final Class<T> extensionPoint;

    public  MockDefinition(Class<T> extensionPoint, T instance) {
      this.instance = instance;
      this.extensionPoint = extensionPoint;
    }

    @Override
    public Class<T> getExtensionPoint() {
        return extensionPoint;
    }

    @Override
    public ExtensionCoordinate getCoordinate() {
        return ExtensionCoordinate.builder().build();
    }

    @Override
    public T load(PluginStorage storage) {
        return instance;
    }

    @Override
    public ExtensionMetadata getMetadata() {
        return new ServletExtensionMetadata("hello");
    }
  }
}
