package com.zamacloud.commons.uaaserver.useraccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Fact S Musingarimi
 * 5/7/19
 * 6:04 PM
 */
@Component
class UserAccountInitializer implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountInitializer.class);
    private final UserAccountConfigurableParameters userAccountConfigurableParameters;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountInitializer(UserAccountConfigurableParameters userAccountConfigurableParameters,
                                  UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountConfigurableParameters = userAccountConfigurableParameters;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.trace("Initializing user accounts");
        if(!userAccountRepository.findByUsername(userAccountConfigurableParameters.getUsername()).isPresent()){
            UserAccount userAccount = UserAccount.of(userAccountConfigurableParameters.getUsername(),
                   passwordEncoder.encode(userAccountConfigurableParameters.getPassword()));
            userAccount=userAccountRepository.save(userAccount);
            LOGGER.info("User account was successfully created {}",userAccount);
        }
    }
}
