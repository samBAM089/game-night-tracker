package de.sambam.gamenighttracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AppUserDetailsService implements UserDetailsService {


    private final AppUserDb userDb;

    @Autowired
    public AppUserDetailsService(AppUserDb userDb) {
        this.userDb = userDb;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = this.userDb.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " does not exist,."));
        return User.builder()
                .username(username)
                .password(appUser.getPassword())
                .authorities("user")
                .build();
    }
}
