package io.sunshower.sdk.v1.core.security;

import io.sunshower.core.security.crypto.EncryptionService;
import io.sunshower.sdk.core.ActivationEndpoint;
import io.sunshower.sdk.core.model.ActivationElement;
import io.sunshower.sdk.core.model.Activations;
import io.sunshower.sdk.lang.BooleanElement;
import io.sunshower.sdk.v1.model.core.Users;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.service.ext.IconService;
import io.sunshower.service.security.ActivationService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.val;

public class DefaultActivationEndpoint implements ActivationEndpoint {

  @Inject private Users users;
  @Inject private Activations activations;

  @Inject private IconService iconService;
  @Inject private EncryptionService encryptionService;
  @PersistenceContext private EntityManager entityManager;

  @Inject private ActivationService activationService;

  @Override
  public void deactivate() {
    activationService.deactivate();
  }

  @Override
  public BooleanElement isActive() {
    return BooleanElement.valueOf(activationService.isActive());
  }

  @Override
  public ActivationElement activate(PrincipalElement element) {
    val user = users.toModel(element);
    String password = element.getPassword();
    String encryptedPassword = encryptionService.encrypt(password);
    user.setPassword(encryptedPassword);
    user.setActive(true);
    user.getDetails().setImage(iconService.iconDirect(user.getUsername(), 64, 64));
    val activatedUser = activationService.activate(user);
    return activations.toElement(activatedUser);
  }

  @Override
  public void delete(ActivationElement e) {
    activationService.delete(activations.toModel(e));
  }
}
