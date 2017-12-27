package io.sunshower.sdk.v1.core.auth.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by gumerman on 5/24/17.
 */
public class KeyPairCredentialElement extends CredentialElement {

    @XmlElement
    private String key;

    @XmlElement
    private String secret;

    public KeyPairCredentialElement() {
        super();
    }

    public KeyPairCredentialElement(String key, String secret) {
        this();
        this.key = key;
        this.secret = secret;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
