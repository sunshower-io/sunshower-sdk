package io.sunshower.sdk.v1;

import io.sunshower.core.security.InvalidCredentialException;
import io.sunshower.core.security.InvalidTokenException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExceptionMappings implements ExceptionMapper<Throwable> {

    static final Logger logger = Logger.getLogger(ExceptionMappings.class.getName());

    static final Map<Class<? extends Throwable>, ExceptionResponse> mappings = new HashMap<>();

    static {
        register(
                AuthenticationCredentialsNotFoundException.class,
                new AuthenticationFailedResponse());

        register(
                AccessDeniedException.class,
                new ForbiddenResponse());

        register(
                InvalidCredentialException.class,
                new AuthenticationFailedResponse());


        register(
                InvalidTokenException.class,
                new AuthenticationFailedResponse());

        register(IllegalArgumentException.class, new BadRequestResponse());

        register(
                NoResultException.class,
                new NoSuchElementException());

        register(
                java.util.NoSuchElementException.class,
                new NoSuchElementException());

        register(
                EntityNotFoundException.class,
                new NotFoundExceptionResponse()
        );

        register(
                NotFoundException.class,
                new NotFoundExceptionResponse()
        );
        
    }


    static void register(Class<? extends Throwable> key, ExceptionResponse response) {
        mappings.put(key, response);
    }


    @Override
    public Response toResponse(Throwable exception) {
        exception.printStackTrace();
        if(exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }
        final ExceptionResponse exceptionResponse = mappings.get(exception.getClass());
        if(exceptionResponse == null) {
            logger.log(Level.WARNING, "Encountered an exception while processing REST request: {0}", exception.getMessage());
            logger.log(Level.WARNING, "Details:", exception);
            return Response.status(
                    Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(exception.getMessage()).build();
        }
        return exceptionResponse.create(exception);
    }
    public interface ExceptionResponse {
        Response create(Throwable throwable);
    }
}

class BadRequestResponse implements ExceptionMappings.ExceptionResponse {
    @Override
    public Response create(Throwable throwable) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new Message((throwable.getMessage()))).build();
    }
}

class NotFoundExceptionResponse implements ExceptionMappings.ExceptionResponse {

    @Override
    public Response create(Throwable throwable) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new Message((throwable.getMessage()))).build();
    }
}


class RepositoryExceptionResponse implements ExceptionMappings.ExceptionResponse {

    @Override
    public Response create(Throwable throwable) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new Message(throwable.getMessage())).build();
    }
}


class NoSuchElementException implements ExceptionMappings.ExceptionResponse {
    @Override
    public Response create(Throwable throwable) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new Message(throwable.getMessage())).build();
    }
}

class ConstraintViolationResponse implements ExceptionMappings.ExceptionResponse {
    @Override
    public Response create(Throwable throwable) {
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new Message(throwable.getMessage())).build();
    }
}
class AuthenticationFailedResponse implements ExceptionMappings.ExceptionResponse {
    @Override
    public Response create(Throwable throwable) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new Message(throwable.getMessage())).build();
    }
}

class ForbiddenResponse implements ExceptionMappings.ExceptionResponse {
    @Override
    public Response create(Throwable throwable) {
        return Response.status(Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new Message(throwable.getMessage())).build();
    }
}