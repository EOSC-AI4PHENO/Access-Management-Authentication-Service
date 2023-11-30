package com.siseth.authentication.module.keycloak.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siseth.authentication.module.keycloak.api.BlacklistedUsersDTO;
import com.siseth.authentication.module.keycloak.api.UserGroupsDTO;
import com.siseth.authentication.module.keycloak.constant.KeyCloakConstant;
import com.siseth.authentication.module.keycloak.model.common.HttpClientKeyCloak;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import javax.ws.rs.NotFoundException;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class BlacklistedUsers extends HttpClientKeyCloak {

    private String accessToken;

    private String realm;

    private String blacklistedGroupId;

    public BlacklistedUsers(String accessToken, String realm) {
        this.accessToken = accessToken;
        this.realm = realm;
        this.blacklistedGroupId = "";
    }

    private final String URL = KeyCloakConstant.KEYCLOAK_ADMIN_URL + "groups/{{blacklistedId}}/members";

    private final String GROUP_LIST_URL = KeyCloakConstant.KEYCLOAK_ADMIN_URL + "groups";

    @SneakyThrows
    @Override
    public List<BlacklistedUsersDTO> getResponse() {

        HttpResponse group_response = responseBuilder(new HttpGet(GROUP_LIST_URL.replace("{{realm}}", realm)), this.accessToken);
        checkStatus(group_response);

        List<UserGroupsDTO> group_list =
        new ObjectMapper().readValue(
                decode(group_response.getEntity()),
                new TypeReference<List<UserGroupsDTO>>() { });


        group_list.get(0).getSubGroups().forEach(x -> {
            if(x.getName().equals("Blacklisted Users")) {
                blacklistedGroupId = x.getId();
            }
        });
        if (blacklistedGroupId.equals(""))
            throw new NotFoundException("Group not found");

        HttpResponse response = responseBuilder(new HttpGet(URL.replace("{{realm}}", realm).replace("{{blacklistedId}}", this.blacklistedGroupId)), this.accessToken);
        checkStatus(response);

        List<BlacklistedUsersDTO> result =
        new ObjectMapper().readValue(
                                    decode(response.getEntity()),
                new TypeReference<List<BlacklistedUsersDTO>>() { });
        return result;
    }
}
