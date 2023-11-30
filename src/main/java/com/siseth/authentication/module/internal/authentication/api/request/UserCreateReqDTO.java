package com.siseth.authentication.module.internal.authentication.api.request;

import lombok.Getter;

@Getter
public class UserCreateReqDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String enabled;

    private String username;
}
