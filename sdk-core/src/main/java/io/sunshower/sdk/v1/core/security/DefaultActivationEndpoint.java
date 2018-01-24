package io.sunshower.sdk.v1.core.security;

import io.sunshower.core.security.crypto.EncryptionService;
import io.sunshower.encodings.Base58;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.core.ActivationEndpoint;
import io.sunshower.sdk.core.model.ActivationElement;
import io.sunshower.sdk.core.model.Activations;
import io.sunshower.sdk.lang.BooleanElement;
import io.sunshower.sdk.v1.model.core.Users;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;
import io.sunshower.service.security.ActivationService;

import javax.inject.Inject;
import java.security.SecureRandom;

public class DefaultActivationEndpoint implements ActivationEndpoint {

  @Inject private Users users;
  @Inject private Activations activations;

  @Inject private EncryptionService encryptionService;

  @Inject private ActivationService activationService;

  static final SecureRandom random = new SecureRandom();
  static final char[] alphabet = Base58.Alphabets.Default.getAlphabet();

  String random() {
    char[] ch = new char[20];
    for (int i = 0; i < 20; i++) {
      ch[i] = alphabet[random.nextInt(20)];
    }
    return new String(ch);
  }

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
    User user = users.toModel(element);
    String password = element.getPassword();
    String encryptedPassword = encryptionService.encrypt(password);
    user.setPassword(encryptedPassword);
    user.setActive(true);
    ActivationElement activation = activations.toElement(activationService.activate(user));
    return activation;
  }
}
