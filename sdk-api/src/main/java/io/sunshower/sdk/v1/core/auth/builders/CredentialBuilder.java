package io.sunshower.sdk.v1.core.auth.builders;

import io.sunshower.sdk.v1.core.auth.model.KeyPairCredentialElement;
import io.sunshower.sdk.v1.core.auth.model.UsernamePasswordCredentialElement;

/**
 * Created by gumerman on 5/24/17.
 */
public class CredentialBuilder {



    public UsernamePasswordCredentialBuilder withUsername(String username) {
        return new UsernamePasswordCredentialBuilder(username);
    }

    public KeyPairCredentialBuilder withKey(String key) {
        return new KeyPairCredentialBuilder(key);

    }

    public class UsernamePasswordCredentialBuilder {
        final String username;

        public UsernamePasswordCredentialBuilder(String username) {
            this.username = username;
        }

        public UsernamePasswordCredentialElement andPassword(String password) {
            return new UsernamePasswordCredentialElement(username, password);
        }



    }

    public class KeyPairCredentialBuilder {
        final String key;

        public KeyPairCredentialBuilder(String key) {
            this.key = key;
        }

        public KeyPairCredentialElement andSecret(String secret) {
            return new KeyPairCredentialElement(key, secret);
        }


    }

}
