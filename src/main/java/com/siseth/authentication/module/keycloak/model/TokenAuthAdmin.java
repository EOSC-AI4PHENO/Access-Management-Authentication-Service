package com.siseth.authentication.module.keycloak.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siseth.authentication.module.keycloak.api.TokenDTO;
import com.siseth.authentication.module.keycloak.constant.KeyCloakConstant;
import com.siseth.authentication.module.keycloak.model.common.HttpClientKeyCloak;
import com.siseth.authentication.module.property.ApplicationProps;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TokenAuthAdmin extends HttpClientKeyCloak {

    private final ApplicationProps.Realm realm;
    private final String URL = KeyCloakConstant.KEYCLOAK_URL + "token";

    @SneakyThrows
    @Override
    public TokenDTO getResponse() {
        HttpResponse response = responseBuilder(new HttpPost(URL.replace("{{realm}}", realm.getName())),
                new UrlEncodedFormEntity(getParams()), null);
        checkStatus(response);
        return new ObjectMapper().readValue(decode(response.getEntity()), TokenDTO.class);
    }

    protected List<NameValuePair> getParams() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "client_credentials"));
        params.add(new BasicNameValuePair("client_id", realm.getClient_id()));
        params.add(new BasicNameValuePair("client_secret", realm.getClient_secret()));
        return params;
    }

}
