package io.sunshower.sdk.test;

import io.sunshower.sdk.channel.Message;
import io.sunshower.test.common.SerializationAware;
import io.sunshower.test.common.SerializationTestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTickerTest extends SerializationTestCase {

  public MessageTickerTest() {
    super(SerializationAware.Format.JSON, new Class[] {Message.class});
  }

  @Test
  void ensureMessageIsMappedCorrectly() {
    assertNotNull(copy(new Message()));
  }
}
