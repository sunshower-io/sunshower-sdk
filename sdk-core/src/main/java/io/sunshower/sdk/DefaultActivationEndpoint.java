package io.sunshower.sdk;

import io.sunshower.sdk.core.ActivationEndpoint;
import io.sunshower.sdk.core.model.ActivationElement;
import io.sunshower.sdk.lang.BooleanElement;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.service.security.ActivationService;

import javax.inject.Inject;

public class DefaultActivationEndpoint implements ActivationEndpoint {
  @Inject private ActivationService activationService;

  @Override
  public BooleanElement isActive() {
    return null;
  }

  @Override
  public ActivationElement activate(PrincipalElement element) {
      return null;
  }
}
