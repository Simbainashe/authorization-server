package com.zamacloud.commons.uaaserver.clientapplication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Fact S Musingarimi
 * 11/1/18
 * 11:55 AM
 */
@Entity
@Table(name = "oauth_client_details")
public class ClientApplication implements Serializable, ClientDetails {
    @Id
    @Column(name = "client_id", nullable = false)
    @NotBlank(message = "Client id is required")
    @Size(max = 255, message = "Client id must contain not more than 255 characters")
    private String clientId;
    @Column(name = "client_secret", nullable = false)
    @NotBlank(message = "Client secret is required")
    @Size(max = 100, message = "Client secret must contain not more than 100 characters")
    private String clientSecret;
    @Column(name = "resource_ids", nullable = false)
    @Size(min = 1, message = "Resource ids are required")
    @Convert(converter = CollectionAttributeConverter.class)
    private Collection<String> resourceIds = new HashSet<>();
    @Column(name = "scope", nullable = false)
    @Convert(converter = CollectionAttributeConverter.class)
    private Collection<String> scope = new HashSet<>();
    @Column(name = "authorized_grant_types")
    @Convert(converter = CollectionAttributeConverter.class)
    private Collection<String> authorizedGrantTypes = new HashSet<>();
    @Column(name = "web_server_redirect_uri")
    @Convert(converter = CollectionAttributeConverter.class)
    private Collection<String> registeredRedirectUri = new HashSet<>();
    @Column(name = "authorities")
    @Convert(converter = CollectionAttributeConverter.class)
    private Collection<String> authorities = new HashSet<>();
    @Column(name = "access_token_validity")
    private Integer accessTokenValiditySeconds;
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValiditySeconds;
    @Column(name = "autoapprove")
    @Convert(converter = BooleanToStringAttributeConverter.class)
    private Boolean autoApprove;
    @Column(name = "additional_information", length = 4096)
    @Convert(converter = MapToStringConverter.class)
    private Map<String, Object> additionalInformation = new HashMap<>();

    protected ClientApplication() {
    }

    private ClientApplication(String clientId, String clientSecret, Set<String> resourceIds,
                              Set<String> scope, Set<String> authorizedGrantTypes, Set<String> registeredRedirectUri,
                              Collection<String> authorities, Integer accessTokenValiditySeconds,
                              Integer refreshTokenValiditySeconds, Boolean autoApprove,
                              Map<String, Object> additionalInformation) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.resourceIds = resourceIds;
        this.scope = scope;
        this.authorizedGrantTypes = authorizedGrantTypes;
        this.registeredRedirectUri = registeredRedirectUri;
        this.authorities = authorities;
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
        this.autoApprove = autoApprove;
        this.additionalInformation = additionalInformation;
    }

    public static ClientApplication of(ClientApplicationRequest clientApplicationRequest, PasswordEncoder passwordEncoder) {
        return new ClientApplication(clientApplicationRequest.getClientId(),
                passwordEncoder.encode(clientApplicationRequest.getClientSecret()),
                clientApplicationRequest.getResourceIds(),
                clientApplicationRequest.getScope(), clientApplicationRequest.getAuthorizedGrantTypes(),
                clientApplicationRequest.getRegisteredRedirectUri(), clientApplicationRequest.getAuthorities(),
                clientApplicationRequest.getAccessTokenValiditySeconds(),
                clientApplicationRequest.getRefreshTokenValiditySeconds(),
                clientApplicationRequest.getAutoApprove(), clientApplicationRequest.getAdditionalInformation());
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public Boolean getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(Boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    @Override
    public Set<String> getResourceIds() {
        return new HashSet<>(resourceIds);
    }

    public void setResourceIds(Collection<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    @Override
    public Set<String> getScope() {
        return new HashSet<>(scope);
    }

    public void setScope(Collection<String> scope) {
        this.scope = scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return new HashSet<>(authorizedGrantTypes);
    }

    public void setAuthorizedGrantTypes(Collection<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return new HashSet<>(registeredRedirectUri);
    }

    public void setRegisteredRedirectUri(Collection<String> registeredRedirectUri) {
        this.registeredRedirectUri = registeredRedirectUri;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return autoApprove;
    }

    @Override
    public String toString() {
        return "ClientApplication{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", resourceIds=" + resourceIds +
                ", scope=" + scope +
                ", authorizedGrantTypes=" + authorizedGrantTypes +
                ", registeredRedirectUri=" + registeredRedirectUri +
                ", authorities=" + authorities +
                ", accessTokenValiditySeconds=" + accessTokenValiditySeconds +
                ", refreshTokenValiditySeconds=" + refreshTokenValiditySeconds +
                ", autoApprove=" + autoApprove +
                ", additionalInformation=" + additionalInformation +
                '}';
    }
}
