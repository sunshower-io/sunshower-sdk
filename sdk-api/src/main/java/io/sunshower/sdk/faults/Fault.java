package io.sunshower.sdk.faults;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/** Created by haswell on 3/17/17. */
public class Fault extends WebApplicationException {

  public Fault() {
    super();
  }

  public Fault(String message) {
    super(message);
  }

  public Fault(Response response) {
    super(response);
  }

  public Fault(String message, Response response) {
    super(message, response);
  }

  public Fault(int status) {
    super(status);
  }

  public Fault(String message, int status) {
    super(message, status);
  }

  public Fault(Response.Status status) {
    super(status);
  }

  public Fault(String message, Response.Status status) {
    super(message, status);
  }

  public Fault(Throwable cause) {
    super(cause);
  }

  public Fault(String message, Throwable cause) {
    super(message, cause);
  }

  public Fault(Throwable cause, Response response) {
    super(cause, response);
  }

  public Fault(String message, Throwable cause, Response response) {
    super(message, cause, response);
  }

  public Fault(Throwable cause, int status) {
    super(cause, status);
  }

  public Fault(String message, Throwable cause, int status) {
    super(message, cause, status);
  }

  public Fault(Throwable cause, Response.Status status) throws IllegalArgumentException {
    super(cause, status);
  }

  public Fault(String message, Throwable cause, Response.Status status)
      throws IllegalArgumentException {
    super(message, cause, status);
  }
}
