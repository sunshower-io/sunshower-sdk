package io.sunshower.sdk.v1.core.auth;

import io.sunshower.common.Identifier;
import io.sunshower.model.core.auth.Keypair;
import io.sunshower.model.core.auth.UsernamePasswordCredential;
import io.sunshower.sdk.v1.core.auth.builders.CredentialBuilder;
import io.sunshower.sdk.v1.core.auth.model.KeyPairCredentialElement;
import io.sunshower.sdk.v1.core.auth.model.UsernamePasswordCredentialElement;

/**
 * Created by gumerman on 5/24/17.
 */
public class CredentialMapper {

    public static KeyPairCredentialElement toElement(Keypair keypair) {
        final KeyPairCredentialElement keyPairCredentialElement = new KeyPairCredentialElement();
        keyPairCredentialElement.setId(keypair.getId());
        keyPairCredentialElement.setKey(keypair.getKey());
        keyPairCredentialElement.setSecret(keypair.getSecret());
        return keyPairCredentialElement;
    }

    public static Keypair toModel(KeyPairCredentialElement keyPairCredentialElement) {
        final Keypair keypair = new Keypair();
        keypair.setId(keyPairCredentialElement.getId());
        keypair.setKey(keyPairCredentialElement.getKey());
        keypair.setSecret(keyPairCredentialElement.getSecret());
        return keypair;
    }

    public static UsernamePasswordCredentialElement toElement(UsernamePasswordCredential usernamePasswordCredential) {
        final UsernamePasswordCredentialElement usernamePasswordCredentialElement = new UsernamePasswordCredentialElement();
        usernamePasswordCredentialElement.setId(usernamePasswordCredential.getId());
        usernamePasswordCredentialElement.setUsername(usernamePasswordCredential.getUsername());
        usernamePasswordCredentialElement.setPassword(usernamePasswordCredential.getPassword());
        return usernamePasswordCredentialElement;
    }

    public static UsernamePasswordCredential toModel(UsernamePasswordCredentialElement usernamePasswordCredentialElement) {
        final UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredential();
        usernamePasswordCredential.setId(usernamePasswordCredentialElement.getId());
        usernamePasswordCredential.setUsername(usernamePasswordCredentialElement.getUsername());
        usernamePasswordCredential.setPassword(usernamePasswordCredentialElement.getPassword());
        return usernamePasswordCredential;
    }
}
