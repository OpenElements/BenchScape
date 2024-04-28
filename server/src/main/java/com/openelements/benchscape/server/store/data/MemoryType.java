package com.openelements.benchscape.server.store.data;

/**
 * Represents a memory type.
 */
public enum MemoryType {

    BYTE, KILOBYTE, MEGABYTE, GIGABYTE;

    /**
     * Returns the bytes of 1 unit of the memory type.
     *
     * @return the bytes of 1 unit of the memory type
     */
    public long bytes() {
        return switch (this) {
            case BYTE -> 1;
            case KILOBYTE -> 1024;
            case MEGABYTE -> 1024 * 1024;
            case GIGABYTE -> 1024 * 1024 * 1024;
        };
    }
}
