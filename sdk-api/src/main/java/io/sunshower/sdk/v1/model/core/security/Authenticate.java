package io.sunshower.sdk.v1.model.core.security;

import io.sunshower.sdk.v1.model.core.security.builders.AuthenticationBuilder;

/** Created by haswell on 5/5/17. */
public class Authenticate {

  public static AuthenticationBuilder as(String username) {
    return new AuthenticationBuilder(username);
  }
}
