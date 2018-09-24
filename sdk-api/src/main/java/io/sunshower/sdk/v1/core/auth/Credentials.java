package io.sunshower.sdk.v1.core.auth;

import io.sunshower.sdk.v1.core.auth.builders.CredentialBuilder;

public class Credentials {

  public static CredentialBuilder forUser() {
    return new CredentialBuilder();
  }

  public static CredentialBuilder keypair() {
    return new CredentialBuilder();
  }
}
