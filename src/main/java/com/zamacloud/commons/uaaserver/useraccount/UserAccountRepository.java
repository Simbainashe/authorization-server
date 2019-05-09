package com.zamacloud.commons.uaaserver.useraccount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Fact S Musingarimi
 * 5/7/19
 * 8:48 AM
 */
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
    Optional<UserAccount> findByUsername(String username);
}
