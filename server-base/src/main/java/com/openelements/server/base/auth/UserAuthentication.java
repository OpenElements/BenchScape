package com.openelements.server.base.auth;

import java.util.Optional;
import org.springframework.security.core.Authentication;

public interface UserAuthentication extends Authentication {

    User getUser();
}
