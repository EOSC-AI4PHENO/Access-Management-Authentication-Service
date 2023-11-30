package com.siseth.authentication.module.internal.authentication.api.request;

import lombok.Getter;

@Getter
public class UserSignInReqDTO {

    private String username;

    private String password;
}
