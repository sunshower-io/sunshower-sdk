package io.sunshower.sdk.channel;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReactiveChannelSelector implements ChannelSelector {

    private final Map<Object, Channel<?>> channels;

    public ReactiveChannelSelector() {
        channels = new ConcurrentHashMap<>();
    }


    @Override
    public <I, T> void create(I id, Publisher<T> publisher) {
        channels.put(id, new PublisherChannel<>(publisher));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, I> Channel<T> select(I id) {
        return (Channel<T>) channels.get(id);
    }
}
