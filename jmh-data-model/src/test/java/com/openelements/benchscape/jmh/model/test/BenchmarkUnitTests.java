package com.openelements.benchscape.jmh.model.test;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BenchmarkUnitTests {

    @Test
    void testGetForNullJmhName() {
        Assertions.assertThrows(NullPointerException.class, () -> BenchmarkUnit.getForJmhName(null));
    }

    @Test
    void testGetForJmhName() {
        final BenchmarkUnit empty = BenchmarkUnit.getForJmhName("");
        final BenchmarkUnit bad = BenchmarkUnit.getForJmhName("bad string");
        final BenchmarkUnit opsPerDay = BenchmarkUnit.getForJmhName("ops/day");
        final BenchmarkUnit opsPerHour = BenchmarkUnit.getForJmhName("ops/hr");
        final BenchmarkUnit opsPerMinute = BenchmarkUnit.getForJmhName("ops/min");
        final BenchmarkUnit opsPerSecond = BenchmarkUnit.getForJmhName("ops/s");
        final BenchmarkUnit opsPerMillisecond = BenchmarkUnit.getForJmhName("ops/ms");
        final BenchmarkUnit opsPerNanosecond = BenchmarkUnit.getForJmhName("ops/ns");
        final BenchmarkUnit daysPerOperation = BenchmarkUnit.getForJmhName("day/op");
        final BenchmarkUnit hoursPerOperation = BenchmarkUnit.getForJmhName("hr/op");
        final BenchmarkUnit minutesPerOperation = BenchmarkUnit.getForJmhName("min/op");
        final BenchmarkUnit secondsPerOperation = BenchmarkUnit.getForJmhName("s/op");
        final BenchmarkUnit millisecondsPerOperation = BenchmarkUnit.getForJmhName("ms/op");
        final BenchmarkUnit nanosecondsPerOperation = BenchmarkUnit.getForJmhName("ns/op");
        final BenchmarkUnit unknown = BenchmarkUnit.getForJmhName("unknown");

        Assertions.assertEquals(BenchmarkUnit.UNKNWOWN, empty);
        Assertions.assertEquals(BenchmarkUnit.UNKNWOWN, bad);
        Assertions.assertEquals(BenchmarkUnit.OPERATIONS_PER_DAY, opsPerDay);
        Assertions.assertEquals(BenchmarkUnit.OPERATIONS_PER_HOUR, opsPerHour);
        Assertions.assertEquals(BenchmarkUnit.OPERATIONS_PER_MINUTE, opsPerMinute);
        Assertions.assertEquals(BenchmarkUnit.OPERATIONS_PER_SECOND, opsPerSecond);
        Assertions.assertEquals(BenchmarkUnit.OPERATIONS_PER_MILLISECOND, opsPerMillisecond);
        Assertions.assertEquals(BenchmarkUnit.OPERATIONS_PER_NANOSECOND, opsPerNanosecond);
        Assertions.assertEquals(BenchmarkUnit.DAYS_PER_OPERATION, daysPerOperation);
        Assertions.assertEquals(BenchmarkUnit.HOURS_PER_OPERATION, hoursPerOperation);
        Assertions.assertEquals(BenchmarkUnit.MINUTES_PER_OPERATION, minutesPerOperation);
        Assertions.assertEquals(BenchmarkUnit.SECONDS_PER_OPERATION, secondsPerOperation);
        Assertions.assertEquals(BenchmarkUnit.MILLISECONDS_PER_OPERATION, millisecondsPerOperation);
        Assertions.assertEquals(BenchmarkUnit.NANOSECONDS_PER_OPERATION, nanosecondsPerOperation);
        Assertions.assertEquals(BenchmarkUnit.UNKNWOWN, unknown);
    }

    @Test
    void testReadableName() {
        Assertions.assertEquals("ops/day", BenchmarkUnit.OPERATIONS_PER_DAY.getReadableName());
        Assertions.assertEquals("ops/hr", BenchmarkUnit.OPERATIONS_PER_HOUR.getReadableName());
        Assertions.assertEquals("ops/min", BenchmarkUnit.OPERATIONS_PER_MINUTE.getReadableName());
        Assertions.assertEquals("ops/s", BenchmarkUnit.OPERATIONS_PER_SECOND.getReadableName());
        Assertions.assertEquals("ops/ms", BenchmarkUnit.OPERATIONS_PER_MILLISECOND.getReadableName());
        Assertions.assertEquals("ops/ns", BenchmarkUnit.OPERATIONS_PER_NANOSECOND.getReadableName());
        Assertions.assertEquals("day/op", BenchmarkUnit.DAYS_PER_OPERATION.getReadableName());
        Assertions.assertEquals("hr/op", BenchmarkUnit.HOURS_PER_OPERATION.getReadableName());
        Assertions.assertEquals("min/op", BenchmarkUnit.MINUTES_PER_OPERATION.getReadableName());
        Assertions.assertEquals("s/op", BenchmarkUnit.SECONDS_PER_OPERATION.getReadableName());
        Assertions.assertEquals("ms/op", BenchmarkUnit.MILLISECONDS_PER_OPERATION.getReadableName());
        Assertions.assertEquals("ns/op", BenchmarkUnit.NANOSECONDS_PER_OPERATION.getReadableName());
        Assertions.assertEquals("unknown", BenchmarkUnit.UNKNWOWN.getReadableName());
    }

    @Test
    void testConversion() {
        // Test conversion for OPERATIONS_PER_DAY
        Assertions.assertEquals(1.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(1.0D, BenchmarkUnit.OPERATIONS_PER_DAY));
        Assertions.assertEquals(24.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(1.0D, BenchmarkUnit.OPERATIONS_PER_HOUR));
        Assertions.assertEquals(1440.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(1.0D, BenchmarkUnit.OPERATIONS_PER_MINUTE));
        Assertions.assertEquals(86400.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(1.0D, BenchmarkUnit.OPERATIONS_PER_SECOND));
        Assertions.assertEquals(8640.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(0.0001D, BenchmarkUnit.OPERATIONS_PER_MILLISECOND));
        Assertions.assertEquals(8640.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(0.0000000001D, BenchmarkUnit.OPERATIONS_PER_NANOSECOND));
        Assertions.assertEquals(0.1D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(10.0D, BenchmarkUnit.DAYS_PER_OPERATION));
        Assertions.assertEquals(12.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(2.0D, BenchmarkUnit.HOURS_PER_OPERATION));
        Assertions.assertEquals(720.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(2.0D, BenchmarkUnit.MINUTES_PER_OPERATION));
        Assertions.assertEquals(43200.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(2.0D, BenchmarkUnit.SECONDS_PER_OPERATION));
        Assertions.assertEquals(43200.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(2000.0D, BenchmarkUnit.MILLISECONDS_PER_OPERATION));
        Assertions.assertEquals(4320000.0D, BenchmarkUnit.OPERATIONS_PER_DAY.convert(20000000.0D, BenchmarkUnit.NANOSECONDS_PER_OPERATION));
        Assertions.assertThrows(IllegalArgumentException.class, () -> BenchmarkUnit.OPERATIONS_PER_DAY.convert(1.0D, BenchmarkUnit.UNKNWOWN));
        Assertions.assertThrows(NullPointerException.class, () -> BenchmarkUnit.OPERATIONS_PER_DAY.convert(1.0D, null));

    }

}
