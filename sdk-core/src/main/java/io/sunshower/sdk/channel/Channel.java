package io.sunshower.sdk.channel;

import io.sunshower.lang.Startable;
import io.sunshower.lang.Stoppable;
import org.reactivestreams.Publisher;

public interface Channel<T> extends Publisher<T>, Startable, Stoppable {

  enum Status {
    Started,
    Stopped,
    Starting,
    Stopping,
    Unknown
  }

  /**
   * You probably shouldn't need to implement this--use ManagedChannel (or just submit a channel and
   * let ManagedChannel take care of itself)
   *
   * @return
   */
  default Status getStatus() {
    return Status.Unknown;
  }
}
