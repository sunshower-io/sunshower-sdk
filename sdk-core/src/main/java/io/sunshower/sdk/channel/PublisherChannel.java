package io.sunshower.sdk.channel;

import io.sunshower.lang.Startable;
import io.sunshower.lang.Stoppable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class PublisherChannel<T> implements Channel<T> {

  final Publisher<T> publisher;

  public PublisherChannel(Publisher<T> publisher) {
    this.publisher = publisher;
  }

  @Override
  public void start() {
    if (publisher instanceof Startable) {
      ((Startable) publisher).start();
    }
  }

  @Override
  public void stop() {
    if (publisher instanceof Stoppable) {
      ((Stoppable) publisher).stop();
    }
  }

  @Override
  public void subscribe(Subscriber<? super T> s) {
    publisher.subscribe(s);
  }
}
