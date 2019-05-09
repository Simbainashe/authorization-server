package com.zamacloud.commons.uaaserver.configuration;

import com.zamacloud.commons.uaaserver.useraccount.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Fact S Musingarimi
 * 5/7/19
 * 9:00 AM
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    public UserDetailsServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(
                "User account doest not exist"));
    }
}
