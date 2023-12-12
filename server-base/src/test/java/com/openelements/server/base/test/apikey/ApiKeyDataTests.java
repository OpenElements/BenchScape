package com.openelements.server.base.test.apikey;

import com.openelements.server.base.apikey.ApiKeyData;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApiKeyDataTests {

    @Test
    public void testConstruction() {
        Assertions.assertDoesNotThrow(() -> {
            new ApiKeyData(null, "name", "user", "key", LocalDateTime.now());
        }, "id can be null");
        Assertions.assertThrows(NullPointerException.class, () -> {
            new ApiKeyData(UUID.randomUUID(), null, "user", "key", LocalDateTime.now());
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new ApiKeyData(UUID.randomUUID(), "name", null, "key", LocalDateTime.now());
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new ApiKeyData(UUID.randomUUID(), "name", "user", null, LocalDateTime.now());
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new ApiKeyData(UUID.randomUUID(), "name", "user", "key", null);
        });
        Assertions.assertDoesNotThrow(() -> {
            new ApiKeyData(UUID.randomUUID(), "name", "user", "key", LocalDateTime.now());
        });
    }
}
