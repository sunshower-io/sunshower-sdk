package io.sunshower.sdk.channel;

import io.sunshower.lang.Startable;
import io.sunshower.lang.Stoppable;
import org.reactivestreams.Publisher;

public interface ChannelSource<T> extends Publisher<T>, Startable, Stoppable {}
