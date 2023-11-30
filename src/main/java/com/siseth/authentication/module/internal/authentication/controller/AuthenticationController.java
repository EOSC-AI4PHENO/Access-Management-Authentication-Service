package com.siseth.authentication.module.internal.authentication.controller;

import com.siseth.authentication.module.internal.authentication.api.request.RefreshTokenReqDTO;
import com.siseth.authentication.module.internal.authentication.api.request.UserSignInReqDTO;
import com.siseth.authentication.module.internal.authentication.service.AuthService;
import com.siseth.authentication.module.keycloak.api.KeyCloakSignInDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/access/authentication")
@Tag(name = "Authentication Controller", description = "Endpoints to manage authentication")
public class AuthenticationController {

    private final AuthService service;

    @PostMapping("/issueSessionToken")
    @Operation(summary = "Get session tokens", description = "Endpoint provides access_token and refresh_token")
    public ResponseEntity<KeyCloakSignInDTO> issueSessionToken(@RequestBody UserSignInReqDTO userSignInReqDTO,
                                                               @Parameter(hidden = true) @RequestHeader(required = false) String realm) {
        return ResponseEntity.ok(service.issueSessionToken(userSignInReqDTO, realm));
    }

    @PostMapping("/endSession")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "End session", description = "Endpoint that ends the session for the user")
    public ResponseEntity<String> endSession(@RequestBody RefreshTokenReqDTO refreshTokenReqDTO,
                                             @Parameter(hidden = true) @RequestHeader(required = false) String realm) {
        return ResponseEntity.ok(service.logout(refreshTokenReqDTO, realm));
    }

    @PostMapping("/refreshToken")
    @Operation(summary = "Refresh token", description = "Endpoint provides access_token and refresh_token")
    public ResponseEntity<KeyCloakSignInDTO> refreshToken(@RequestBody RefreshTokenReqDTO refreshTokenReqDTO,
                                                          @Parameter(hidden = true) @RequestHeader(required = false) String realm) {
        return ResponseEntity.ok(service.refreshToken(refreshTokenReqDTO, realm));
    }
}
