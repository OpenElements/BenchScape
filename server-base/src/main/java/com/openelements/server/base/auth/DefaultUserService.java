package com.openelements.server.base.auth;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService{

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public boolean isAuthenticated() {
        return user().isPresent();
    }

    @NonNull
    @Override
    public User getCurrentUser() {
        return user().orElseThrow(() -> new IllegalStateException("No user is authenticated"));
    }

    private Optional<User> user() {
        Authentication authentication = getAuthentication();
        if(authentication == null) {
            return Optional.empty();
        }
        if(authentication instanceof UserAuthentication userAuthentication) {
            return Optional.ofNullable(userAuthentication.getUser());
        } else {
            return Optional.empty();
        }
    }
}
