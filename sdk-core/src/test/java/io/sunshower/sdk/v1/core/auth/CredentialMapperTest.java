package io.sunshower.sdk.v1.core.auth;

import io.sunshower.model.core.auth.Keypair;
import io.sunshower.model.core.auth.UsernamePasswordCredential;
import io.sunshower.sdk.v1.core.auth.model.KeyPairCredentialElement;
import io.sunshower.sdk.v1.core.auth.model.UsernamePasswordCredentialElement;
import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(JUnitPlatform.class)
public class CredentialMapperTest {

    @Test
    public void ensureKeypairToElementSerializesId() {
        final Keypair keypair = new Keypair();
        assertThat(CredentialMapper.toElement(keypair).getId(), is(keypair.getIdentifier()));
    }

    @Test
    public void ensureKeypairToModelSerializesId() {
        final KeyPairCredentialElement keyPairCredentialElement = new KeyPairCredentialElement();
        assertThat(CredentialMapper.toModel(keyPairCredentialElement).getIdentifier(), is(keyPairCredentialElement.getId()));
    }

    @Test
    public void ensureUsernamePasswordToElementSerializesId() {
        final UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredential();
        assertThat(CredentialMapper.toElement(usernamePasswordCredential).getId(), is(usernamePasswordCredential.getIdentifier()));
    }

    @Test
    public void ensureUserNamePasswordToModelSerializesId() {
        final UsernamePasswordCredentialElement usernamePasswordCredentialElement = new UsernamePasswordCredentialElement();
        assertThat(CredentialMapper.toModel(usernamePasswordCredentialElement).getIdentifier(), is(usernamePasswordCredentialElement.getId()));
    }

}