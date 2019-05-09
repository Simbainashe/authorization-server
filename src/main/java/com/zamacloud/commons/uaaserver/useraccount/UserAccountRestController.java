package com.zamacloud.commons.uaaserver.useraccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author Fact S Musingarimi
 * 5/7/19
 * 8:48 AM
 */
@RestController
@RequestMapping("v1/user-accounts")
public class UserAccountRestController {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserAccountRestController.class);
    private final UserAccountRepository userAccountRepository;
    private final UserDetailsService userDetailsService;

    public UserAccountRestController(UserAccountRepository userAccountRepository, UserDetailsService userDetailsService) {
        this.userAccountRepository = userAccountRepository;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public UserAccount createUserAccount(@Valid @RequestBody UserAccount userAccount){
        LOGGER.trace("Create user account request {}",userAccount);
        return userAccountRepository.save(userAccount);
    }

    @GetMapping
    public Page<UserAccount> getUserAccounts(@PageableDefault Pageable pageable){
        LOGGER.trace("User accounts list request {}",pageable);
        return userAccountRepository.findAll(pageable);
    }

    @GetMapping("/me" )
    public UserAccount user(Principal principal) {
        return (UserAccount) userDetailsService.loadUserByUsername(principal.getName());
    }
}
