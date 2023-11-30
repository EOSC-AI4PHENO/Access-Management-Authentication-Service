package com.siseth.authentication.module.keycloak.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlacklistedUsersDTO {
    private String id;
    private String email;
}
