package io.sunshower.sdk.v1.model.core.faults;

import javax.ws.rs.core.Response;

/**
 * Created by haswell on 3/21/17.
 */
public class DuplicateElementException extends Fault {


    public DuplicateElementException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
