package com.zamacloud.commons.uaaserver.clientapplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Fact S Musingarimi
 * 5/7/19
 * 4:03 PM
 */
@RestController
@RequestMapping("v1/client-applications")
public class ClientApplicationRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientApplicationRestController.class);
    private final ClientApplicationRepository clientApplicationRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientApplicationRestController(ClientApplicationRepository clientApplicationRepository,
                                           PasswordEncoder passwordEncoder) {
        this.clientApplicationRepository = clientApplicationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ClientApplication createClientApplication(@Valid @RequestBody ClientApplicationRequest clientApplicationRequest) {
        LOGGER.trace("Create client application request {}", clientApplicationRequest);
        ClientApplication clientApplication = ClientApplication.of(clientApplicationRequest, passwordEncoder);
        return clientApplicationRepository.save(clientApplication);
    }

    @GetMapping
    public Page<ClientApplication> getClientApplications(@PageableDefault Pageable pageable) {
        LOGGER.trace("Get client applications request {}", pageable);
        return clientApplicationRepository.findAll(pageable);
    }
}
