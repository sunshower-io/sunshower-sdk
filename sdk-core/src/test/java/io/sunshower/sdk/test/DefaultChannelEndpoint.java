package io.sunshower.sdk.test;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.channel.ChannelSelector;
import io.sunshower.sdk.channel.Channels;
import io.sunshower.sdk.channel.CurrentContextSubscriber;
import io.sunshower.sdk.lang.IdentifierElement;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

public class DefaultChannelEndpoint implements ChannelEndpoint {

  @Inject private ChannelSelector channelSelector;

  @Override
  public String getMediaType(MediaType mediaType) {
    return mediaType.toString();
  }

  @Override
  public IdentifierElement newTicker() {
    final Identifier id = Identifier.random();
    channelSelector.create(id, new Ticker());
    return IdentifierElement.from(id);
  }

  @Override
  public void start(Identifier id) {
    channelSelector.select(id).start();
  }

  @Override
  public void subscribe(Identifier id, SseEventSink sink, Sse see, MediaType mediaType) {
    channelSelector.select(id).subscribe(new CurrentContextSubscriber<>(see, mediaType, sink));
  }
}
