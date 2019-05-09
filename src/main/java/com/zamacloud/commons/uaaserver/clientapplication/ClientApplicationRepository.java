package com.zamacloud.commons.uaaserver.clientapplication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Fact S Musingarimi
 * 5/7/19
 * 4:02 PM
 */
interface ClientApplicationRepository extends JpaRepository<ClientApplication,String> {
    Optional<ClientApplication> findByClientId(String clientId);
}
