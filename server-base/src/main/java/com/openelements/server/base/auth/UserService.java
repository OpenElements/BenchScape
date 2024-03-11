package com.openelements.server.base.auth;

import edu.umd.cs.findbugs.annotations.NonNull;

public interface UserService {

    boolean isAuthenticated();

    @NonNull
    User getCurrentUser();

}
