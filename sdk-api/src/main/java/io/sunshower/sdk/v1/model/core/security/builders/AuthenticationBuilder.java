package io.sunshower.sdk.v1.model.core.security.builders;

import io.sunshower.sdk.v1.model.core.security.AuthenticationRequest;

/**
 * Created by haswell on 5/5/17.
 */
public class AuthenticationBuilder {

    private final String username;

    public AuthenticationBuilder(String username) {
        this.username = username;
    }

    public AuthenticationRequest withPassword(String password) {
        return new AuthenticationRequest(username, password);
    }
}
