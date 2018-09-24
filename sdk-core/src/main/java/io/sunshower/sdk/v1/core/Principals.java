package io.sunshower.sdk.v1.core;

import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.core.model.Transformer;
import io.sunshower.sdk.v1.model.core.security.PrincipalElement;

public interface Principals extends Transformer<User, PrincipalElement> {}
