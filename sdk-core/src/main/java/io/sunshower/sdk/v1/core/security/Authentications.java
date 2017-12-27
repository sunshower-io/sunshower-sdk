package io.sunshower.sdk.v1.core.security;

import io.sunshower.model.core.auth.Authentication;
import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.Token;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.model.core.security.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by haswell on 5/5/17.
 */
public class Authentications {

    public static AuthenticationElement fromModel(Authentication authentication) {
        if(authentication == null) {
            return null;
        }
        final AuthenticationElement result = new AuthenticationElement();
        result.setPrincipal(fromModel(authentication.getUser()));
        result.setToken(fromModel(authentication.getToken()));
        return result;
    }

    public static AuthenticationTokenElement fromModel(Token token) {
        if(token == null) {
            return null;
        }
        return new AuthenticationTokenElement(token.getToken());
    }

    public static RoleElement fromModel(Role role) {
        if(role == null) {
            return null;
        }
        final RoleElement result = new RoleElement();
        result.setAuthority(role.getAuthority());
        return result;
    }

    static Stream<RoleElement> mapRoles(Role role) {
        if(role == null) {
            return Stream.empty();
        }
        return Stream.concat(Stream.of(fromModel(role)),
                role.getChildren() == null ? Stream.empty() :
                        role.getChildren()
                                .stream()
                                .flatMap(Authentications::mapRoles));

    }

    public static PrincipalElement fromModel(User user) {
        if(user == null) {
            return null;
        }

        final PrincipalElement e = new PrincipalElement();
        e.setId(user.getIdentifier());
        e.setActive(user.isActive());
        e.setUsername(user.getUsername());
        e.setEmailAddress(user.getDetails().getEmailAddress());
        e.setPhoneNumber(user.getDetails().getPhoneNumber());
        e.setFirstName(user.getDetails().getFirstname());
        e.setLastName(user.getDetails().getLastname());
        if(user.getRoles() != null) {
            final List<RoleElement> roles = user
                    .getRoles()
                    .stream()
                    .flatMap(Authentications::mapRoles)
                    .collect(Collectors.toList());
            e.setRoles(roles);
        }

        return e;
    }

    public static AuthenticationElement toElement(Authentication value) {
        if(value != null) {
            final AuthenticationElement result = new AuthenticationElement();
            result.setPrincipal(fromModel(value.getUser()));
            result.setToken(fromModel(value.getToken()));
            return result;
        }
        return null;
    }
}
