package com.openelements.benchscape.server.store.math;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import com.openelements.benchscape.server.store.data.Measurement;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public class InterpolationUtils {

    private InterpolationUtils() {
    }

    @NonNull
    public static List<Measurement> withSplineInterpolation(@NonNull final List<Measurement> realMeasurements,
            final int count) {
        Objects.requireNonNull(realMeasurements, "realMeasurements must not be null");
        final List<Measurement> sortedList = new ArrayList<>(realMeasurements);
        sortedList.sort(Comparator.comparing(Measurement::timestamp));

        final SplineInterpolator splineInterpolator = new SplineInterpolator();
        final double[] xTime = new double[sortedList.size()];
        final double[] yValue = new double[sortedList.size()];
        final double[] yMin = new double[sortedList.size()];
        final double[] yMax = new double[sortedList.size()];
        final double[] yError = new double[sortedList.size()];
        for (int i = 0; i < sortedList.size(); i++) {
            final Measurement measurement = sortedList.get(i);
            xTime[i] = measurement.timestamp().toEpochMilli();
            yValue[i] = BenchmarkUnit.OPERATIONS_PER_MILLISECOND.convert(measurement.value(), measurement.unit());
            yMin[i] = Optional.ofNullable(sortedList.get(i).min())
                    .map(m -> BenchmarkUnit.OPERATIONS_PER_MILLISECOND.convert(m, measurement.unit()))
                    .orElse(Double.NaN);
            yMax[i] = Optional.ofNullable(sortedList.get(i).max())
                    .map(m -> BenchmarkUnit.OPERATIONS_PER_MILLISECOND.convert(m, measurement.unit()))
                    .orElse(Double.NaN);
            yError[i] = Optional.ofNullable(sortedList.get(i).error())
                    .map(m -> BenchmarkUnit.OPERATIONS_PER_MILLISECOND.convert(m, measurement.unit()))
                    .orElse(Double.NaN);
        }

        final PolynomialSplineFunction valueFunction = splineInterpolator.interpolate(xTime, yValue);
        final PolynomialSplineFunction minFunction = splineInterpolator.interpolate(xTime, yMin);
        final PolynomialSplineFunction maxFunction = splineInterpolator.interpolate(xTime, yMax);
        final PolynomialSplineFunction errorFunction = splineInterpolator.interpolate(xTime, yError);

        final double step = (xTime[xTime.length - 1] - xTime[0]) / (count - 1);
        final double start = xTime[0];
        double millis = start;

        final List<Measurement> results = new ArrayList<>(count);
        for (int i = 0; i < count - 1; i++) {
            final double valueCalc = valueFunction.value(millis);
            final double minCalc = minFunction.value(millis);
            final double maxCalc = maxFunction.value(millis);
            final double errorCalc = errorFunction.value(millis);

            final Measurement interpolatedMeasurement = new Measurement(null, Instant.ofEpochMilli((long) millis),
                    valueCalc,
                    errorCalc,
                    minCalc, maxCalc, BenchmarkUnit.OPERATIONS_PER_MILLISECOND);
            millis += step;
            results.add(interpolatedMeasurement);
        }
        return results;
    }
}
