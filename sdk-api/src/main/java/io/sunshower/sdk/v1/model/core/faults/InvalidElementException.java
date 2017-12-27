package io.sunshower.sdk.v1.model.core.faults;

import io.sunshower.sdk.v1.model.core.element.ClassElement;
import io.sunshower.sdk.v1.model.core.element.MessageElement;

import javax.ws.rs.core.Response;

/**
 * Created by haswell on 3/17/17.
 */
public class InvalidElementException extends Fault  {

    public InvalidElementException(
            String message,
            Class<?> type,
            Object...args
    ) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(new MessageElement<>(
                        String.format(message, args),
                        new ClassElement<>(type))
                ).build());
    }
}
