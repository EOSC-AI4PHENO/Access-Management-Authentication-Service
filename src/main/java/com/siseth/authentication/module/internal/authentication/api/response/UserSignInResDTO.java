package com.siseth.authentication.module.internal.authentication.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignInResDTO {
    private String refresh_token;
    private String access_token;
}
