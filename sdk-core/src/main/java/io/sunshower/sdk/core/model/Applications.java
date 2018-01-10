package io.sunshower.sdk.core.model;


import io.sunshower.model.core.Application;
import io.sunshower.sdk.v1.model.core.Users;
import org.mapstruct.*;

@Mapper(
        uses = Users.class,
        componentModel = "jsr330",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface Applications {
    
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name")
    })
    ApplicationElement toElement(Application application);

    @InheritInverseConfiguration
    Application toModel(ApplicationElement element);
}
