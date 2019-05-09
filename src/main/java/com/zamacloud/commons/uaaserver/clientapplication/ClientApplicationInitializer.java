package com.zamacloud.commons.uaaserver.clientapplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Fact S Musingarimi
 * 5/7/19
 * 4:15 PM
 */
@Component
class ClientApplicationInitializer implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientApplicationInitializer.class);
    private final ClientApplicationRepository clientApplicationRepository;
    private final ClientApplicationConfigurableParameters clientApplicationConfigurableParameters;
    private final OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;
    private final PasswordEncoder passwordEncoder;

    public ClientApplicationInitializer(ClientApplicationRepository clientApplicationRepository,
                                        ClientApplicationConfigurableParameters clientApplicationConfigurableParameters,
                                        OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails,
                                        PasswordEncoder passwordEncoder) {
        this.clientApplicationRepository = clientApplicationRepository;
        this.clientApplicationConfigurableParameters = clientApplicationConfigurableParameters;
        this.oAuth2ProtectedResourceDetails = oAuth2ProtectedResourceDetails;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.trace("Running oauth2 client applications initializer");
        if (!clientApplicationRepository.findByClientId(clientApplicationConfigurableParameters.getId()).isPresent()) {
            ClientApplicationRequest adminPortalClientApplicationRequest = new ClientApplicationRequest();
            Set<String> resourceIds = Stream.of(oAuth2ProtectedResourceDetails.getClientId()).collect(Collectors.toSet());
            Set<String> scopes = Stream.of("read", "write").collect(Collectors.toSet());
            Set<String> authorizedGrantTypes = Stream.of("password", "refresh_token", "authorization_code")
                    .collect(Collectors.toSet());
            adminPortalClientApplicationRequest.setResourceIds(resourceIds);
            adminPortalClientApplicationRequest.setScope(scopes);
            adminPortalClientApplicationRequest.setClientSecret(clientApplicationConfigurableParameters.getSecret());
            adminPortalClientApplicationRequest.setAuthorizedGrantTypes(authorizedGrantTypes);
            adminPortalClientApplicationRequest.setAccessTokenValiditySeconds(1200);
            adminPortalClientApplicationRequest.setRefreshTokenValiditySeconds(86400);
            adminPortalClientApplicationRequest.setClientId(clientApplicationConfigurableParameters.getId());
            adminPortalClientApplicationRequest.setRegisteredRedirectUri(new HashSet<>(Arrays.asList(
                    clientApplicationConfigurableParameters.getRedirectUri())));
            adminPortalClientApplicationRequest.setAutoApprove(clientApplicationConfigurableParameters.getAutoApprove());
            ClientApplication adminPortalClientApplication = ClientApplication.of(adminPortalClientApplicationRequest, passwordEncoder);
            adminPortalClientApplication = clientApplicationRepository.save(adminPortalClientApplication);
            LOGGER.info("Admin portal client application was successfully created {}", adminPortalClientApplication);
        }

        if (!clientApplicationRepository.findByClientId(oAuth2ProtectedResourceDetails.getClientId()).isPresent()) {
            ClientApplicationRequest uaaServerClientApplicationRequest = new ClientApplicationRequest();
            Set<String> uaaServerResourceIds = Stream.of(oAuth2ProtectedResourceDetails.getClientId(),
                    clientApplicationConfigurableParameters.getResourceId()).collect(Collectors.toSet());
            Set<String> uaaServerScopes = Stream.of("read", "write").collect(Collectors.toSet());
            Set<String> uaaServerAuthorizedGrantTypes = Stream.of("password", "refresh_token", "authorization_code",
                    "client_credentials").collect(Collectors.toSet());
            uaaServerClientApplicationRequest.setResourceIds(uaaServerResourceIds);
            uaaServerClientApplicationRequest.setScope(uaaServerScopes);
            uaaServerClientApplicationRequest.setClientSecret(oAuth2ProtectedResourceDetails.getClientSecret());
            uaaServerClientApplicationRequest.setAuthorizedGrantTypes(uaaServerAuthorizedGrantTypes);
            uaaServerClientApplicationRequest.setAccessTokenValiditySeconds(1400);
            uaaServerClientApplicationRequest.setRefreshTokenValiditySeconds(86600);
            uaaServerClientApplicationRequest.setClientId(oAuth2ProtectedResourceDetails.getClientId());
            ClientApplication uaaServerClientApplication = ClientApplication.of(uaaServerClientApplicationRequest,
                    passwordEncoder);
            uaaServerClientApplication = clientApplicationRepository.save(uaaServerClientApplication);
            clientApplicationRepository.save(uaaServerClientApplication);
        }
    }
}
