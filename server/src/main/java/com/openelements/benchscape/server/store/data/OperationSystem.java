package com.openelements.benchscape.server.store.data;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Objects;

/**
 * Enum for operation systems.
 */
public enum OperationSystem {
    WINDOWS,
    LINUX,
    MAC_OS,
    UNKNOWN;

    @NonNull
    public static OperationSystem fromName(@NonNull String name) {
        Objects.requireNonNull(name, "name must not be null");
        final String compareName = name.toLowerCase().trim();
        if (Objects.equals(compareName, "windows")) {
            return WINDOWS;
        } else if (Objects.equals(compareName, "win")) {
            return WINDOWS;
        } else if (Objects.equals(compareName, "linux")) {
            return LINUX;
        } else if (Objects.equals(compareName, "mac")) {
            return MAC_OS;
        } else if (Objects.equals(compareName, "mac os")) {
            return MAC_OS;
        } else if (Objects.equals(compareName, "mac-os")) {
            return MAC_OS;
        } else if (Objects.equals(compareName, "mac os x")) {
            return MAC_OS;
        } else {
            return UNKNOWN;
        }
    }
}
