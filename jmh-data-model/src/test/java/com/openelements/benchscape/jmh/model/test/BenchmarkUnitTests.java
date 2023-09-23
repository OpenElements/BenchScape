package com.openelements.benchscape.jmh.model.test;

import static com.openelements.benchscape.jmh.model.BenchmarkUnit.DAYS_PER_OPERATION;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.HOURS_PER_OPERATION;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.MILLISECONDS_PER_OPERATION;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.MINUTES_PER_OPERATION;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.NANOSECONDS_PER_OPERATION;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.OPERATIONS_PER_DAY;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.OPERATIONS_PER_HOUR;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.OPERATIONS_PER_MILLISECOND;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.OPERATIONS_PER_MINUTE;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.OPERATIONS_PER_NANOSECOND;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.OPERATIONS_PER_SECOND;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.SECONDS_PER_OPERATION;
import static com.openelements.benchscape.jmh.model.BenchmarkUnit.UNKNWOWN;

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
        //TODO: Same for all other enum values

        Assertions.assertEquals(BenchmarkUnit.UNKNWOWN, empty);
        Assertions.assertEquals(BenchmarkUnit.UNKNWOWN, bad);
        Assertions.assertEquals(OPERATIONS_PER_DAY, opsPerDay);
    }

    @Test
    void testReadableName() {
        Assertions.assertEquals("ops/day", OPERATIONS_PER_DAY.getReadableName());

        //TODO: Same for all other enum values
    }

    @Test
    void testConversion() {
        Assertions.assertEquals(1.0D, OPERATIONS_PER_DAY.convert(1.0D, OPERATIONS_PER_DAY));
        Assertions.assertEquals(24.0D, OPERATIONS_PER_DAY.convert(1.0D, OPERATIONS_PER_HOUR));
        Assertions.assertEquals(1440.0D, OPERATIONS_PER_DAY.convert(1.0D, OPERATIONS_PER_MINUTE));
        Assertions.assertEquals(86400.0D, OPERATIONS_PER_DAY.convert(1.0D, OPERATIONS_PER_SECOND));
        Assertions.assertEquals(8640.0D, OPERATIONS_PER_DAY.convert(0.0001D, OPERATIONS_PER_MILLISECOND));
        Assertions.assertEquals(8640.0D, OPERATIONS_PER_DAY.convert(0.0000000001D, OPERATIONS_PER_NANOSECOND));
        Assertions.assertEquals(0.1D, OPERATIONS_PER_DAY.convert(10.0D, DAYS_PER_OPERATION));
        Assertions.assertEquals(12.0D, OPERATIONS_PER_DAY.convert(2.0D, HOURS_PER_OPERATION));
        Assertions.assertEquals(720.0D, OPERATIONS_PER_DAY.convert(2.0D, MINUTES_PER_OPERATION));
        Assertions.assertEquals(43200.0D, OPERATIONS_PER_DAY.convert(2.0D, SECONDS_PER_OPERATION));
        Assertions.assertEquals(43200.0D, OPERATIONS_PER_DAY.convert(2000.0D, MILLISECONDS_PER_OPERATION));
        Assertions.assertEquals(4320000.0D, OPERATIONS_PER_DAY.convert(20000000.0D, NANOSECONDS_PER_OPERATION));
        Assertions.assertThrows(IllegalArgumentException.class, () -> OPERATIONS_PER_DAY.convert(1.0D, UNKNWOWN));
        Assertions.assertThrows(NullPointerException.class, () -> OPERATIONS_PER_DAY.convert(1.0D, null));

        //TODO: Same for all other enum values
    }

}
