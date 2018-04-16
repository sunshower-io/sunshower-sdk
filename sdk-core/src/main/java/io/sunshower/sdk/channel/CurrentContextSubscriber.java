package io.sunshower.sdk.channel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

public class CurrentContextSubscriber<T> implements Subscriber<T> {
  private final Sse sse;
  private final SseEventSink sink;
  private Subscription subscription;
  private final MediaType mediaType;

  public CurrentContextSubscriber(Sse sse, MediaType mediaType, SseEventSink sink) {
    this.sse = sse;
    this.sink = sink;
    this.mediaType = mediaType;
  }

  @Override
  public void onSubscribe(Subscription s) {
    this.subscription = s;
  }

  @Override
  public void onNext(T t) {
    OutboundSseEvent event =
        sse.newEventBuilder().mediaType(MediaType.APPLICATION_JSON_TYPE).data(t).build();
    sink.send(event);
  }

  @Override
  public void onError(Throwable t) {}

  @Override
  public void onComplete() {
    sink.close();
  }
}
