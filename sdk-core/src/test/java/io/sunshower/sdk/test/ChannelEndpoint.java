package io.sunshower.sdk.test;

import io.sunshower.common.Identifier;
import io.sunshower.sdk.lang.IdentifierElement;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

@Path("channeltest")
@Produces({
  MediaType.APPLICATION_JSON,
  MediaType.APPLICATION_XML,
})
@Consumes({
  MediaType.APPLICATION_JSON,
  MediaType.APPLICATION_XML,
})
public interface ChannelEndpoint {

  @GET
  @Path("mediatype")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes("*/*")
  String getMediaType(@Context MediaType mediaType);


  @GET
  @Path("ticker")
  IdentifierElement newTicker();

  @GET
  @Path("newMessages")
  IdentifierElement newMessages();

  @GET
  @Path("{id}/start")
  void start(@PathParam("id") Identifier id);

  @GET
  @Path("{id}/messages")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  void messages(
      @PathParam("id") Identifier id,
      @Context SseEventSink sink,
      @Context Sse see,
      @Context MediaType mediaType);

  @GET
  @Path("{id}/channel")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  void subscribe(
      @PathParam("id") Identifier id,
      @Context SseEventSink sink,
      @Context Sse see,
      @Context MediaType mediaType);
}
