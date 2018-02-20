package io.sunshower.sdk.test;

import io.sunshower.sdk.v1.model.core.security.PrincipalElement;

public class TestRoles {

  public static PrincipalElement administrator() {
    return PrincipalElement.create()
        .active(true)
        .username("administrator")
        .emailAddress("josiah@gmail.com")
        .firstName("josiah")
        .lastName("haswell")
        .phoneNumber("999-999-9999")
        .password("frapadap")
        .newPrincipal();
  }
  
}
