package io.sunshower.sdk.v1;

import io.sunshower.sdk.core.model.Activations;
import io.sunshower.sdk.core.model.Authentications;
import io.sunshower.sdk.v1.model.core.Users;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {Users.class, Activations.class, Authentications.class})
public class MappingConfiguration {}
