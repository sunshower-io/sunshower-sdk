package io.sunshower.sdk.v1.core.auth;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import io.sunshower.sdk.v1.MappingConfiguration;
import io.sunshower.sdk.v1.core.auth.model.CredentialElement;
import io.sunshower.sdk.v1.core.auth.model.KeyPairCredentialElement;
import io.sunshower.sdk.v1.core.auth.model.UsernamePasswordCredentialElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MappingConfiguration.class)
public class CredentialsTest {

  @Test
  public void ensureCredentialBuilderAPIForKeypairMakesSense() {
    final KeyPairCredentialElement keypair =
        Credentials.keypair().withKey("key").andSecret("secret");
    assertThat(keypair.getKey(), is("key"));
  }

  @Test
  public void ensureCredentialBuilderAPIForUsernamePasswordCredentialMakesSense() {
    final UsernamePasswordCredentialElement cred =
        Credentials.forUser().withUsername("booper").andPassword("blorp");
    assertThat(cred.getPassword(), is("blorp"));
  }

  @Test
  public void ensureCredentialBuilderAPIForKeypairMakesSenseAndReturnsSuperclass() {
    final KeyPairCredentialElement keypair =
        Credentials.keypair().withKey("key").andSecret("secret");
    assertThat(keypair.getKey(), is("key"));
    assertThat(keypair.getSecret(), is("secret"));
  }

  @Test
  public void
      ensureCredentialBuilderAPIForUsernamePasswordCredentialMakesSenseAndReturnsSuperclass() {
    final CredentialElement cred =
        Credentials.forUser().withUsername("booper").andPassword("blorper");
  }
}
