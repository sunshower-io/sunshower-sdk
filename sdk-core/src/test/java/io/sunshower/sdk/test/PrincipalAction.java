package io.sunshower.sdk.test;

import io.sunshower.sdk.core.ActivationEndpoint;
import io.sunshower.sdk.core.model.ActivationElement;
import io.sunshower.service.security.Action;
import java.util.HashSet;
import java.util.Set;

public class PrincipalAction implements TestAction {
  final Set<ActivationElement> activationElements;
  private final ActivationEndpoint endpoint;

  public PrincipalAction(final ActivationEndpoint endpoint) {
    this.endpoint = endpoint;
    activationElements = new HashSet<>();
  }

  public void addPrincipal(ActivationElement activate, String password) {
    this.activationElements.add(activate);
    SdkTest.passwords.put(activate.getActivator().getUsername(), password);
  }

  @Override
  public void perform(Action action) {
    try {
      action.apply();
    } finally {
      for (ActivationElement e : activationElements) {
        try {
          endpoint.delete(e);
          SdkTest.passwords.remove(e.getActivator().getUsername());
        } catch (Exception ex) {
          System.out.println("Whoops");
        }
      }
    }
  }
}
