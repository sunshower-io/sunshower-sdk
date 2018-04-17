package io.sunshower.sdk.test;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.channel.Channel;
import io.sunshower.sdk.channel.Message;
import org.reactivestreams.Subscriber;

public class MessageTicker implements Channel<Message> {

  private volatile boolean running;
  private Subscriber<? super Message> subscriber;
  private Identifier id;

  @Override
  public void subscribe(Subscriber<? super Message> s) {
    this.id = Identifier.random();
    this.subscriber = s;
  }

  @Override
  public void start() {
    running = true;
    while (running) {
      Message frapper =
          Message.builder().channelId(id).payload("frapper").payloadType(String.class).build();
      subscriber.onNext(frapper);
      try {
        Thread.sleep(400);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void stop() {
    running = false;
  }
}
