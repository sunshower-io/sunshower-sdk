package io.sunshower.sdk.test;

import io.sunshower.service.security.Action;

@FunctionalInterface
public interface TestAction {

  void perform(Action action);
}
