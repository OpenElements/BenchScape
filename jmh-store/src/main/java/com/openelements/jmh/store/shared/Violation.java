package com.openelements.jmh.store.shared;

public record Violation(String id, String message) {

  public static final String MAX_ALLOWED_VALUE_FAILED_ID = "MAX_ALLOWED_VALUE_FAILED";

  public static final String MIN_ALLOWED_VALUE_FAILED_ID = "MIN_ALLOWED_VALUE_FAILED";

  public static final String MAX_ALLOWED_ERROR_FAILED_ID = "MAX_ALLOWED_ERROR_FAILED";

  public static final String BENCHMARK_PROCESSOR_COUNT_UNDEFINED_ID = "BENCHMARK_PROCESSOR_COUNT_UNDEFINED";

  public static final String RESULT_PROCESSOR_COUNT_UNDEFINED_ID = "RESULT_PROCESSOR_COUNT_UNDEFINED";

  public static final String WRONG_PROCESSOR_COUNT_ID = "WRONG_PROCESSOR_COUNT";

  public static final String BENCHMARK_MEMORY_SIZE_UNDEFINED_ID = "BENCHMARK_MEMORY_SIZE_UNDEFINED";

  public static final String RESULT_MEMORY_SIZE_UNDEFINED_ID = "RESULT_MEMORY_SIZE_UNDEFINED";

  public static final String WRONG_MEMORY_SIZE_ID = "WRONG_MEMORY_SIZE";

  public static final String BENCHMARK_JVM_VERSION_UNDEFINED_ID = "BENCHMARK_JVM_VERSION_UNDEFINED";

  public static final String RESULT_JVM_VERSION_UNDEFINED_ID = "RESULT_JVM_VERSION_UNDEFINED";

  public static final String WRONG_JVM_VERSION_ID = "WRONG_JVM_VERSION";
}
