package io.sunshower.sdk.v1.model.ext;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import io.sunshower.common.Identifier;
import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChangeIconRequestTest extends SerializationTestCase {

  private ImageElement image;
  private ChangeIconRequest request;

  ChangeIconRequestTest() {
    super(SerializationAware.Format.JSON, ChangeIconRequest.class);
  }

  @BeforeEach
  void setUp() {
    image = new ImageElement();
    request = new ChangeIconRequest();
  }

  @Test
  void ensureImageDefaultsAreCorrect() {
    assertNull("image must initially be null", request.getImageElement());
    assertNull("target id must initially be null", request.getTargetId());
    assertNull("target type must initially be null", request.getTargetType());
  }

  @Test
  void ensureEmptyIconCanBeCopiedCorrectly() {
    assertThat(copy(request).getId(), is(request.getId()));
  }

  @Test
  void ensureTargetTypeIsCopiedCorrectly() {
    request.setTargetType(ChangeIconRequest.class);
    assertThat(copy(request).getTargetType(), is(equalTo((ChangeIconRequest.class))));
  }

  @Test
  void ensureTargetIdIsCopiedCorrectly() {
    final Identifier id = Identifier.random();
    request.setTargetId(id);
    assertThat(copy(request).getTargetId(), is(id));
  }

  @Test
  void ensureImageElementNameIsCopied() {
    image.setName("test");
    request.setImageElement(image);
    assertThat(copy(request).getImageElement().getName(), is("test"));
  }

  @Test
  void ensureImageElementDataIsCopied() {
    image.setData("test");
    request.setImageElement(image);
    assertThat(copy(request).getImageElement().getData(), is("test"));
  }
}
