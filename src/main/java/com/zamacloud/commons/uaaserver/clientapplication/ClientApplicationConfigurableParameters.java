package com.zamacloud.commons.uaaserver.clientapplication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Fact S Musingarimi
 * 5/7/19
 * 4:21 PM
 */
@Component
@ConfigurationProperties("oauth2.clients.admin-portal")
class ClientApplicationConfigurableParameters {
    @NotBlank(message = "Default client id is required")
    private String id;
    @NotBlank(message = "Default client secret is required")
    private String secret;
    private String redirectUri;
    @NotBlank(message = "Default client resource id is required")
    private String resourceId;
    @NotNull(message = "Default client auto approve is required")
    private Boolean autoApprove;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Boolean getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(Boolean autoApprove) {
        this.autoApprove = autoApprove;
    }
}
