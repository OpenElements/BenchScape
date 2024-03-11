package com.openelements.server.base.apikey;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.LocalDateTime;

public interface ApiKeyService {

    @NonNull
    ApiKey createKey(@NonNull final LocalDateTime validUntil);

    void check(@NonNull final String user, @NonNull final String key);
}
