package io.sunshower.sdk.v1.model.core.element;

import io.sunshower.common.rs.TypeAttributeClassExtractor;
import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class PropertyAwareElementTest extends SerializationTestCase {

  public PropertyAwareElementTest() {
    super(
        SerializationAware.Format.JSON,
        new Class<?>[] {
          String.class,
          Long.class,
          LongPropertyElement.class,
          StringPropertyElement.class,
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
    el.addProperty(new LongPropertyElement("frapper", "dapper", 1L));
    el.addProperty(new StringPropertyElement("hello", "world", "value"));
    TestPropertyAwareElement copy = copy(el);
    assertThat(copy.getProperties().size(), is(2));
    write(el, System.out);
    PropertyElement<?, ?> propertyElement = copy.getProperties().get(0);
    assertThat(propertyElement, is(instanceOf(LongPropertyElement.class)));
    System.out.println(propertyElement.getValue());
    assertThat(propertyElement.getValue(), is(1L));
    propertyElement = copy.getProperties().get(1);
    assertThat(propertyElement, is(instanceOf(StringPropertyElement.class)));
    assertThat(propertyElement.getValue(), is("value"));
  }
}
