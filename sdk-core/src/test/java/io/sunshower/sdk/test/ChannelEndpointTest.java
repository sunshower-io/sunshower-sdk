package io.sunshower.sdk.test;

import io.sunshower.sdk.lang.IdentifierElement;
import io.sunshower.sdk.v1.core.auth.model.UsernamePasswordCredentialElement;
import io.sunshower.test.ws.Remote;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.SseEventSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

class ChannelEndpointTest extends SdkTest {

  @Inject private WebTarget webTarget;

  @Remote private ChannelEndpoint channelEndpoint;

  @Test
  void ensureChannelInterceptorProvidesCorrectMediaType() {
    final ResteasyWebTarget target = (ResteasyWebTarget) webTarget;
    String resp =
        target
            .path(ChannelEndpoint.class)
            .path("mediatype")
            .request(MediaType.APPLICATION_ATOM_XML)
            .accept(MediaType.TEXT_PLAIN)
            .buildGet()
            .invoke(String.class);
    assertThat(resp, is(MediaType.APPLICATION_ATOM_XML));
  }

  @Test
  void ensureStartingAndStoppingWork() throws InterruptedException {
    final ResteasyWebTarget target = (ResteasyWebTarget) webTarget;
    IdentifierElement ticker =
        target
            .path(ChannelEndpoint.class)
            .path("ticker")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .buildGet()
            .invoke(IdentifierElement.class);

    ResteasyWebTarget channel =
        target.path(ChannelEndpoint.class).path("" + ticker.getValue()).path("channel");

    SseEventSource source = SseEventSource.target(channel).build();
    final List<Frap> e = new ArrayList<>();
    source.register(
        c -> {
          Frap frap = c.readData(Frap.class, MediaType.APPLICATION_JSON_TYPE);
          e.add(frap);
        });
    target
        .path(ChannelEndpoint.class)
        .path("" + ticker.getValue())
        .path("start")
        .request(MediaType.APPLICATION_JSON_TYPE)
        .buildGet()
        .invoke();

    source.open();
    while (e.size() < 10) {
      Thread.yield();
    }

    assertThat(e.stream().allMatch(Objects::nonNull), is(true));
    assertThat(e.size(), is(10));
  }
}
