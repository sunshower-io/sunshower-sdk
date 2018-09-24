package io.sunshower.sdk.channel;

import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.reactivestreams.Subscriber;

public class Channels {

  public static <T> Subscriber<? super T> current() {
    Map<Class<?>, Object> contextDataMap = ResteasyProviderFactory.getContextDataMap();

    return new CurrentContextSubscriber<T>(
        (Sse) contextDataMap.get(Sse.class),
        (MediaType) contextDataMap.get(MediaType.class),
        (SseEventSink) contextDataMap.get(SseEventSink.class));
  }
}
