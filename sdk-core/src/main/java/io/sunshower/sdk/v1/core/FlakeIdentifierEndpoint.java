package io.sunshower.sdk.v1.core;

import io.sunshower.common.Identifier;
import io.sunshower.persistence.core.DistributableEntity;
import io.sunshower.sdk.core.IdentifierEndpoint;

public class FlakeIdentifierEndpoint implements IdentifierEndpoint {
    
    @Override
    public Identifier create() {
        return DistributableEntity.sequence.next();
    }
}
