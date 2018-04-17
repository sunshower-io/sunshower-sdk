package io.sunshower.sdk.channel;

import org.reactivestreams.Publisher;

public interface ChannelSelector {


  <I, T> void create(I id, Channel<T> publisher);

  <I, T> void create(I id, Publisher<T> publisher);

  <T, I> Channel<T> select(I id);
}
