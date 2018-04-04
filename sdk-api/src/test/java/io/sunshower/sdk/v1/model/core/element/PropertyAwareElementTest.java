package io.sunshower.sdk.v1.model.core.element;

import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class PropertyAwareElementTest extends SerializationTestCase {

  public PropertyAwareElementTest() {
    super(
        SerializationAware.Format.JSON,
        new Class<?>[] {
          String.class,
          Long.class,
          PropertyElement.class,
          TestPropertyAwareElement.class
        });
  }

  static class TestPropertyAwareElement extends PropertyAwareElement<TestPropertyAwareElement> {
    {
      setType(TestPropertyAwareElement.class);
    }
  }

  @Test
  public void ensureWritingElementWorks() {
    TestPropertyAwareElement el = new TestPropertyAwareElement();
    el.addProperty(new PropertyElement(PropertyType.Integer, "frapper", "dapper", "1"));
    el.addProperty(new PropertyElement(PropertyType.String, "hello", "world", "value"));
    TestPropertyAwareElement copy = copy(el);
    assertThat(copy.getProperties().size(), is(2));
    write(el, System.out);
    PropertyElement propertyElement = copy.getProperties().get(0);
    System.out.println(propertyElement.getValue());
    assertThat(propertyElement.getValue(), is("1"));
    propertyElement = copy.getProperties().get(1);
    assertThat(propertyElement.getValue(), is("value"));
  }
}
