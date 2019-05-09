package com.zamacloud.commons.uaaserver.useraccount;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

/**
 * @author Fact S Musingarimi
 * 5/7/19
 * 6:06 PM
 */
@Component
@ConfigurationProperties("superuser")
class UserAccountConfigurableParameters {
    @NotBlank(message = "Superuser username is required")
    private String username;
    @NotBlank(message = "Superuser password is required")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
