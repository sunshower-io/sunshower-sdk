package io.sunshower.sdk.channel;

import lombok.Synchronized;
import org.reactivestreams.Subscriber;

import java.util.concurrent.Future;

public class ManagedChannel<I, T> implements Channel<T>, Runnable {

  final I id;
  final Channel<T> delegate;
  final ReactiveChannelSelector selector;

  private volatile Future<T> future;
  private volatile Status status = Status.Unknown;

  ManagedChannel(ReactiveChannelSelector selector, I id, Channel<T> delegate) {
    this.id = id;
    this.selector = selector;
    this.delegate = delegate;
  }

  @Override
  public Status getStatus() {
    return status;
  }

  @Override
  public void start() {
    selector.submit(this);
  }

  @Override
  @Synchronized
  public void stop() {
    if (future == null) {
      throw new IllegalStateException("Task hasn't been started yet");
    }
    status = Status.Stopping;
    if (future.cancel(true)) {
      status = Status.Stopped;
    } else {
      status = Status.Unknown;
    }
  }

  @Override
  public void subscribe(Subscriber<? super T> s) {
    delegate.subscribe(s);
  }

  @Override
  public void run() {
    status = Status.Started;
    try {
      delegate.start();
      status = Status.Stopping;
    } finally {
      selector.remove(id);
      status = Status.Stopped;
    }
  }

  @Synchronized
  public void setFuture(Future<T> future) {
    status = Status.Starting;
    this.future = future;

  }
}
