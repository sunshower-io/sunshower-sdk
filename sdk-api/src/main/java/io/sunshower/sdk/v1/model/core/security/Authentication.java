package io.sunshower.sdk.v1.model.core.security;

public final class Authentication {

    private Authentication() {
        throw new UnsupportedOperationException("no authentication instances for you!");
    }


    public static AuthenticationTokenElement token(String token) {
        return new AuthenticationTokenElement(token);
    }
}
