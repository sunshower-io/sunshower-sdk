package io.sunshower.sdk.v1.core.auth;

import io.sunshower.sdk.v1.core.auth.model.CredentialElement;
import io.sunshower.sdk.v1.core.auth.model.KeyPairCredentialElement;
import io.sunshower.sdk.v1.core.auth.model.UsernamePasswordCredentialElement;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class CredentialsTest {

    @Test
    public void ensureCredentialBuilderAPIForKeypairMakesSense() {
        final KeyPairCredentialElement keypair = Credentials.keypair()
                .withKey("key").andSecret("secret");



    }


    @Test
    public void ensureCredentialBuilderAPIForUsernamePasswordCredentialMakesSense() {
        final UsernamePasswordCredentialElement cred =
                Credentials.forUser().withUsername("booper")
                .andPassword("blorp");


    }


    @Test
    public void ensureCredentialBuilderAPIForKeypairMakesSenseAndReturnsSuperclass() {
        final CredentialElement keypair = Credentials.keypair()
                .withKey("key").andSecret("secret");
    }


    @Test
    public void ensureCredentialBuilderAPIForUsernamePasswordCredentialMakesSenseAndReturnsSuperclass() {
        final CredentialElement cred =
                Credentials.forUser().withUsername("booper")
                        .andPassword("blorper");


    }


}