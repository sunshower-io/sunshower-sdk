package io.sunshower.sdk.v1.core.security;

import io.sunshower.model.core.auth.Role;
import io.sunshower.model.core.auth.User;
import io.sunshower.sdk.v1.model.core.security.RoleElement;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static io.sunshower.sdk.v1.core.security.Authentications.fromModel;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by haswell on 5/10/17.
 */
public class AuthenticationsTest {

    @Test
    public void ensureActiveIsCopiedOverInFromModel() {
        final User booper = new User();
        booper.setActive(true);
        assertThat(fromModel(booper).isActive(), is(true));
    }

    @Test
    public void ensurePhoneNumberIsCopiedOverInFromModel() {
        final User blorper = new User();
        blorper.getDetails().setPhoneNumber("123");
        assertThat(fromModel(blorper).getPhoneNumber(), is("123"));
    }

    @Test
    public void ensureMappingOverHierarchyProducesExpectedResults() {

        final Role root = new Role("mom");
        final Role brother = new Role("kai");
        final Role sister = new Role("julie");
        final Role wife = new Role("wabbus");

        final Role bee = new Role("bee");
        final Role addy = new Role("addy");
        wife.addChild(bee);
        wife.addChild(addy);

        root.addChild(brother);
        root.addChild(sister);
        root.addChild(wife);


        List<RoleElement> roleElement = Authentications.mapRoles(root).collect(Collectors.toList());
        assertThat(roleElement.size(), is(6));

        assertThat(roleElement
                .stream()
                .map(RoleElement::getAuthority)
                .collect(Collectors.toSet()),
                is(new HashSet<>(Arrays.asList(
                        "mom",
                        "kai",
                        "julie",
                        "wabbus",
                        "bee",
                        "addy"
                ))));
    }

}