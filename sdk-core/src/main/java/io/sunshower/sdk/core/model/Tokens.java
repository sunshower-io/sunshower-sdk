package io.sunshower.sdk.core.model;

import io.sunshower.model.core.auth.Token;
import io.sunshower.sdk.v1.model.core.security.AuthenticationTokenElement;
import org.mapstruct.*;

@Mapper(
        componentModel = "jsr330",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface Tokens {
    @Mappings({
            @Mapping(source = "value", target = "token")
    })
    Token toModel(AuthenticationTokenElement token);
    
    @InheritInverseConfiguration
    AuthenticationTokenElement toElement(Token token);
}
