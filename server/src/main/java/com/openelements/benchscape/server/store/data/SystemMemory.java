package com.openelements.benchscape.server.store.data;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a system memory value.
 *
 * @param value the memory value
 * @param type  the memory type
 */
public record SystemMemory(long value, @NonNull MemoryType type) {

    public SystemMemory {
        if (value < 0) {
            throw new IllegalArgumentException("Memory value must be positive");
        }
        Objects.requireNonNull(type, "Memory type must be provided");
    }

    public SystemMemory(final long value) {
        this(value, MemoryType.BYTE);
    }

    /**
     * Converts the memory value to bytes.
     *
     * @return the memory value in bytes
     */
    public long toBytes() {
        return value * type.bytes();
    }

    /**
     * Checks if the memory value is greater than the other memory value.
     *
     * @return true if the memory value is greater than the other memory value, false otherwise
     */
    public boolean isGreaterThan(final @NonNull SystemMemory other) {
        Objects.requireNonNull(other, "Memory value must be provided");
        return toBytes() > other.toBytes();
    }

    /**
     * Checks if the memory value is less than the other memory value.
     *
     * @param other the other memory value
     * @return true if the memory value is less than the other memory value, false otherwise
     */
    public boolean isLessThan(final @NonNull SystemMemory other) {
        Objects.requireNonNull(other, "Memory value must be provided");
        return toBytes() < other.toBytes();
    }

    /**
     * Checks if the memory value is greater than or equal to the other memory value.
     *
     * @param other the other memory value
     * @return true if the memory value is greater than or equal to the other memory value, false otherwise
     */
    public boolean isGreaterThanOrEqual(final @NonNull SystemMemory other) {
        Objects.requireNonNull(other, "Memory value must be provided");
        return toBytes() >= other.toBytes();
    }

    /**
     * Checks if the memory value is less than or equal to the other memory value.
     *
     * @param other the other memory value
     * @return true if the memory value is less than or equal to the other memory value, false otherwise
     */
    public boolean isLessThanOrEqual(final @NonNull SystemMemory other) {
        Objects.requireNonNull(other, "Memory value must be provided");
        return toBytes() <= other.toBytes();
    }

    /**
     * Simplifies the memory value to the highest possible unit.
     *
     * @return the simplified memory value
     */
    @NonNull
    public SystemMemory simplify() {
        if (value == 0) {
            return new SystemMemory(0);
        }
        long bytesValue = toBytes();
        if (bytesValue % 1024 * 1024 * 1024 == 0) {
            return new SystemMemory(bytesValue / 1024 / 1024 / 1024, MemoryType.GIGABYTE);
        }
        if (bytesValue % 1024 * 1024 == 0) {
            return new SystemMemory(bytesValue / 1024 / 1024, MemoryType.MEGABYTE);
        }
        if (bytesValue % 1024 == 0) {
            return new SystemMemory(bytesValue / 1024, MemoryType.KILOBYTE);
        }
        return this;
    }

    @Override
    public String toString() {
        return value + " " + type;
    }

    /**
     * Returns a function that converts a memory value in bytes to a system memory value.
     *
     * @return the function that converts a memory value in bytes to a system memory value
     */
    @NonNull
    public static Function<Long, SystemMemory> getFromByteConverter() {
        return value -> {
            if (value == null) {
                return null;
            }
            return new SystemMemory(value).simplify();
        };
    }

    /**
     * Returns a function that converts a system memory value to a memory value in bytes.
     *
     * @return the function that converts a system memory value to a memory value in bytes
     */
    @NonNull
    public static Function<SystemMemory, Long> getToByteConverter() {
        return memory -> {
            if (memory == null) {
                return null;
            }
            return memory.toBytes();
        };
    }
}
