package io.sunshower.sdk.channel;

import lombok.Synchronized;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ReactiveChannelSelector implements ChannelSelector {

  final ExecutorService service;
  final Set<Object> active;
  final Set<Object> inactive;
  final Map<Object, Channel<?>> channels;

  public ReactiveChannelSelector(ExecutorService service) {
    channels = new ConcurrentHashMap<>();
    this.service = service;
    this.active = new HashSet<>();
    this.inactive = new HashSet<>();
  }

  @Override
  @Synchronized
  public <I, T> void create(I id, Channel<T> publisher) {
    if (!channels.containsKey(id)) {
      final Channel<T> channel;
      if(publisher instanceof ManagedChannel) {
        channel = publisher;
      } else {
        channel = new ManagedChannel<>(this, id, publisher);
      }
      channels.put(id, channel);
      inactive.add(id);
    }
  }

  @Override
  public <I, T> void create(I id, Publisher<T> publisher) {
    create(id, new ManagedChannel<>(this, id, new PublisherChannel<>(publisher)));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T, I> Channel<T> select(I id) {
    return (Channel<T>) channels.get(id);
  }

  @Synchronized
  <I> void remove(I id) {
    active.remove(id);
    channels.remove(id);
    inactive.remove(id);
  }

  @Synchronized
  @SuppressWarnings("unchecked")
  <I, T> void submit(ManagedChannel<I, T> channel) {
    if (!active.contains(channel.id) && inactive.contains(channel.id)) {
      inactive.remove(channel.id);
      channel.setFuture((Future<T>) service.submit(channel));
      active.add(channel);
    }
  }
}
