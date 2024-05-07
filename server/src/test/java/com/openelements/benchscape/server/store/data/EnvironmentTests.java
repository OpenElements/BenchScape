package com.openelements.benchscape.server.store.data;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnvironmentTests {

    @Test
    void testNullName() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new Environment(UUID.randomUUID(), null, "", "",
                    "", "",
                    3, 1,
                    5, new SystemMemory(10),
                    new SystemMemory(1), new SystemMemory(20),
                    "",
                    "", OperationSystem.MAC_OS, "", "", "");
        });
    }
}
