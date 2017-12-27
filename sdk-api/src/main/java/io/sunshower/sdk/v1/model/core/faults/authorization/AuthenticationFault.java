package io.sunshower.sdk.v1.model.core.faults.authorization;

import io.sunshower.sdk.v1.model.core.faults.Fault;

import javax.ws.rs.core.Response;

/**
 * Created by haswell on 5/5/17.
 */
public class AuthenticationFault extends Fault {

    public AuthenticationFault() {
        super();
    }

    public AuthenticationFault(String message) {
        super(message);
    }

    public AuthenticationFault(Response response) {
        super(response);
    }

    public AuthenticationFault(String message, Response response) {
        super(message, response);
    }

    public AuthenticationFault(int status) {
        super(status);
    }

    public AuthenticationFault(String message, int status) {
        super(message, status);
    }

    public AuthenticationFault(Response.Status status) {
        super(status);
    }

    public AuthenticationFault(String message, Response.Status status) {
        super(message, status);
    }

    public AuthenticationFault(Throwable cause) {
        super(cause);
    }

    public AuthenticationFault(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationFault(Throwable cause, Response response) {
        super(cause, response);
    }

    public AuthenticationFault(String message, Throwable cause, Response response) {
        super(message, cause, response);
    }

    public AuthenticationFault(Throwable cause, int status) {
        super(cause, status);
    }

    public AuthenticationFault(String message, Throwable cause, int status) {
        super(message, cause, status);
    }

    public AuthenticationFault(Throwable cause, Response.Status status) throws IllegalArgumentException {
        super(cause, status);
    }

    public AuthenticationFault(String message, Throwable cause, Response.Status status) throws IllegalArgumentException {
        super(message, cause, status);
    }
}
