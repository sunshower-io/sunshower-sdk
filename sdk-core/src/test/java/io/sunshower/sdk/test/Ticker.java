package io.sunshower.sdk.test;

import io.sunshower.sdk.channel.ChannelSource;
import org.reactivestreams.Subscriber;

public class Ticker implements ChannelSource<Frap>, Runnable {

  volatile boolean running;
  private volatile Subscriber<? super Frap> subscriber;

  @Override
  public void start() {
    this.running = true;
    run();
  }

  @Override
  public void stop() {
    running = false;
  }

  @Override
  public void subscribe(Subscriber<? super Frap> s) {
    this.subscriber = s;
  }

  @Override
  public void run() {
    int i = 0;
    while (running) {
      if (subscriber != null) {
        try {
          this.subscriber.onNext(new Frap());
        } catch (Exception ex) {
          System.out.println("Got" + ex.getMessage());
        }
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
