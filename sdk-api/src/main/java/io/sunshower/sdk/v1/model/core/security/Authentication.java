package io.sunshower.sdk.v1.model.core.security;

/**
 * Created by haswell on 5/5/17.
 */
public final class Authentication {

    private Authentication() {
        throw new UnsupportedOperationException("no authentication instances for you!");
    }


    public static AuthenticationTokenElement token(String token) {
        return new AuthenticationTokenElement(token);
    }
}
