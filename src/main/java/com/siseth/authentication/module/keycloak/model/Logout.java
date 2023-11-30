package com.siseth.authentication.module.keycloak.model;

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
public class Logout extends HttpClientKeyCloak {

    private String refreshToken;

    private final ApplicationProps.Realm realm;

    private final String URL = KeyCloakConstant.KEYCLOAK_URL + "logout";

    @SneakyThrows
    @Override
    public String getResponse() {
        HttpPost request = new HttpPost(URL);
        request.setEntity(new UrlEncodedFormEntity(getParams()));
        HttpResponse response = builder().execute(request);
        checkStatus(response);
        return "Successfully logged out";
    }

    protected List<NameValuePair> getParams() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("refresh_token", this.refreshToken));
        params.add(new BasicNameValuePair("client_id", realm.getClient_id()));
        params.add(new BasicNameValuePair("client_secret", realm.getClient_secret()));
        return params;
    }

}
