package com.siseth.authentication.module.internal.authentication.service;

import com.siseth.authentication.module.internal.authentication.api.request.RefreshTokenReqDTO;
import com.siseth.authentication.module.internal.authentication.api.request.UserSignInReqDTO;
import com.siseth.authentication.module.keycloak.api.BlacklistedUsersDTO;
import com.siseth.authentication.module.keycloak.api.KeyCloakSignInDTO;
import com.siseth.authentication.module.keycloak.api.TokenDTO;
import com.siseth.authentication.module.keycloak.model.*;
import com.siseth.authentication.module.property.ApplicationProps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotAllowedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ApplicationProps props;

    public KeyCloakSignInDTO issueSessionToken(UserSignInReqDTO userSignInReqDTO, String realm) {
        TokenDTO token = new TokenAuthAdmin(props.getRealm(realm)).getResponse();
        List<BlacklistedUsersDTO> blacklistedUsersDTO = new BlacklistedUsers(token.getAccess_token(), realm).getResponse();

        if (blacklistedUsersDTO.size() > 0) {
            blacklistedUsersDTO.forEach(user -> {
                if (user.getEmail().equals(userSignInReqDTO.getUsername())) {
                    throw new NotAllowedException("Not allowed");
                }
            });
        }

        KeyCloakSignInDTO keyCloakSignInDTO = new TokenAuth(userSignInReqDTO.getUsername(), userSignInReqDTO.getPassword(), props.getRealm(realm))
                .getResponse();

        if (keyCloakSignInDTO.getAccess_token()==null || keyCloakSignInDTO.getRefresh_token()==null)
            throw new RuntimeException("not valid");

        return keyCloakSignInDTO;
    }

    public String logout(RefreshTokenReqDTO refreshTokenReqDTO, String realm) {
        return new Logout(refreshTokenReqDTO.getRefresh_token(), props.getRealm(realm)).getResponse();
    }

    public KeyCloakSignInDTO refreshToken(RefreshTokenReqDTO refreshTokenReqDTO, String realm) {
        return new RefreshToken(refreshTokenReqDTO.getRefresh_token(), props.getRealm(realm)).getResponse();
    }
}
