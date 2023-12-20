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
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.AkimaSplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.DividedDifferenceInterpolator;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.LoessInterpolator;
import org.apache.commons.math3.analysis.interpolation.NevilleInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;

public class InterpolationUtils {

    private InterpolationUtils() {
    }

    @NonNull
    public static List<InterpolatedMeasurement> withSplineInterpolation(
            @NonNull final List<Measurement> realMeasurements,
            final int count) {
        return interpolate(realMeasurements, new SplineInterpolator(), count);
    }

    @NonNull
    public static List<InterpolatedMeasurement> withLoessInterpolation(
            @NonNull final List<Measurement> realMeasurements,
            final int count) {
        return interpolate(realMeasurements, new LoessInterpolator(), count);
    }

    @NonNull
    public static List<InterpolatedMeasurement> withNevilleInterpolator(
            @NonNull final List<Measurement> realMeasurements,
            final int count) {
        return interpolate(realMeasurements, new NevilleInterpolator(), count);
    }

    public static List<InterpolatedMeasurement> withAkimaSplineInterpolator(
            @NonNull final List<Measurement> realMeasurements,
            final int count) {
        return interpolate(realMeasurements, new AkimaSplineInterpolator(), count);
    }

    public static List<InterpolatedMeasurement> withDividedDifferenceInterpolator(
            @NonNull final List<Measurement> realMeasurements,
            final int count) {
        return interpolate(realMeasurements, new DividedDifferenceInterpolator(), count);
    }

    public static List<InterpolatedMeasurement> withLinearInterpolator(
            @NonNull final List<Measurement> realMeasurements,
            final int count) {
        return interpolate(realMeasurements, new LinearInterpolator(), count);
    }

    @NonNull
    public static List<Measurement> smooth(@NonNull final List<Measurement> realMeasurements) {
        Objects.requireNonNull(realMeasurements, "realMeasurements must not be null");
        final LoessInterpolator interpolator = new LoessInterpolator();
        final List<Measurement> sortedList = new ArrayList<>(realMeasurements);
        sortedList.sort(Comparator.comparing(Measurement::timestamp));
        final MeasurementInterpolationData data = createMeasurementInterpolationData(sortedList);

        final double[] yValuesSmooth = interpolator.smooth(data.xTime(), data.yValue());
        final double[] yMinSmooth = interpolator.smooth(data.xTime(), data.yMin());
        final double[] yMaxSmooth = interpolator.smooth(data.xTime(), data.yMax());
        final double[] yErrorSmooth = interpolator.smooth(data.xTime(), data.yError());

        final List<Measurement> results = new ArrayList<>(sortedList.size());
        for (int i = 0; i < sortedList.size(); i++) {
            final Measurement interpolatedMeasurement = new Measurement(sortedList.get(i).id(),
                    sortedList.get(i).timestamp(),
                    yValuesSmooth[i],
                    yErrorSmooth[i],
                    yMinSmooth[i], yMaxSmooth[i], sortedList.get(i).unit());
            results.add(interpolatedMeasurement);
        }
        return results;
    }

    public static List<InterpolatedMeasurement> interpolate(List<Measurement> measurements,
            InterpolationType interpolationType, int interpolationPoints) {
        Objects.requireNonNull(measurements, "measurements must not be null");
        Objects.requireNonNull(interpolationType, "interpolationType must not be null");
        if (interpolationType == InterpolationType.SPLINE) {
            return withSplineInterpolation(measurements, interpolationPoints);
        }
        if (interpolationType == InterpolationType.LOESS) {
            return withLoessInterpolation(measurements, interpolationPoints);
        }
        if (interpolationType == InterpolationType.NEVILLE) {
            return withNevilleInterpolator(measurements, interpolationPoints);
        }
        if (interpolationType == InterpolationType.AKIMA) {
            return withAkimaSplineInterpolator(measurements, interpolationPoints);
        }
        if (interpolationType == InterpolationType.DIVIDED_DIFFERENCE) {
            return withDividedDifferenceInterpolator(measurements, interpolationPoints);
        }
        if (interpolationType == InterpolationType.LINEAR) {
            return withLinearInterpolator(measurements, interpolationPoints);
        }

        return measurements.stream()
                .map(m -> new InterpolatedMeasurement(m.timestamp(), m.value(), m.error(), m.min(), m.max(),
                        m.unit()))
                .toList();
    }

    private record MeasurementInterpolationData(double[] xTime, double[] yValue, double[] yMin, double[] yMax,
                                                double[] yError) {

        private MeasurementInterpolationData {
            Objects.requireNonNull(xTime, "xTime must not be null");
            Objects.requireNonNull(yValue, "yValue must not be null");
            Objects.requireNonNull(yMin, "yMin must not be null");
            Objects.requireNonNull(yMax, "yMax must not be null");
            Objects.requireNonNull(yError, "yError must not be null");
            if (xTime.length != yValue.length) {
                throw new IllegalArgumentException(
                        "xTime.length '" + xTime.length + "' must be equal to yValue.length '"
                                + yValue.length + "'");
            }
            if (xTime.length != yMin.length) {
                throw new IllegalArgumentException("xTime.length '" + xTime.length + "' must be equal to yMin.length '"
                        + yMin.length + "'");
            }
            if (xTime.length != yMax.length) {
                throw new IllegalArgumentException("xTime.length '" + xTime.length + "' must be equal to yMax.length '"
                        + yMax.length + "'");
            }
            if (xTime.length != yError.length) {
                throw new IllegalArgumentException(
                        "xTime.length '" + xTime.length + "' must be equal to yError.length '"
                                + yError.length + "'");
            }
        }
    }

    @NonNull
    private static MeasurementInterpolationData createMeasurementInterpolationData(
            @NonNull final List<Measurement> realMeasurements) {
        Objects.requireNonNull(realMeasurements, "realMeasurements must not be null");
        final List<Measurement> sortedList = new ArrayList<>(realMeasurements);
        sortedList.sort(Comparator.comparing(Measurement::timestamp));
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
                    .orElse(0d); //FUTURE: Should be null or Double.NaN
            yMax[i] = Optional.ofNullable(sortedList.get(i).max())
                    .map(m -> BenchmarkUnit.OPERATIONS_PER_MILLISECOND.convert(m, measurement.unit()))
                    .orElse(0d);//FUTURE: Should be null or Double.NaN
            yError[i] = Optional.ofNullable(sortedList.get(i).error())
                    .map(m -> BenchmarkUnit.OPERATIONS_PER_MILLISECOND.convert(m, measurement.unit()))
                    .orElse(0d);//FUTURE: Should be null or Double.NaN
        }
        return new MeasurementInterpolationData(xTime, yValue, yMin, yMax, yError);
    }

    @NonNull
    private static List<InterpolatedMeasurement> interpolate(@NonNull final List<Measurement> realMeasurements,
            @NonNull final UnivariateInterpolator interpolator, final int count) {
        Objects.requireNonNull(realMeasurements, "realMeasurements must not be null");
        Objects.requireNonNull(interpolator, "interpolator must not be null");
        if (count < 2) {
            throw new IllegalArgumentException("count must be greater than 1");
        }

        final MeasurementInterpolationData data = createMeasurementInterpolationData(realMeasurements);

        final UnivariateFunction valueFunction = interpolator.interpolate(data.xTime(), data.yValue());
        final UnivariateFunction minFunction = interpolator.interpolate(data.xTime(), data.yMin());
        final UnivariateFunction maxFunction = interpolator.interpolate(data.xTime(), data.yMax());
        final UnivariateFunction errorFunction = interpolator.interpolate(data.xTime(), data.yError());

        final double step = (data.xTime()[data.xTime().length - 1] - data.xTime()[0]) / (count - 1);
        final double start = data.xTime()[0];
        double millis = start;

        final List<InterpolatedMeasurement> results = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            final double valueCalc;
            final double minCalc;
            final double maxCalc;
            final double errorCalc;

            if (i == count - 1) {
                valueCalc = valueFunction.value(data.xTime()[data.xTime().length - 1]);
                minCalc = minFunction.value(data.xTime()[data.xTime().length - 1]);
                maxCalc = maxFunction.value(data.xTime()[data.xTime().length - 1]);
                errorCalc = errorFunction.value(data.xTime()[data.xTime().length - 1]);
            } else {
                valueCalc = valueFunction.value(millis);
                minCalc = minFunction.value(millis);
                maxCalc = maxFunction.value(millis);
                errorCalc = errorFunction.value(millis);
            }
            final InterpolatedMeasurement interpolatedMeasurement = new InterpolatedMeasurement(
                    Instant.ofEpochMilli((long) millis),
                    valueCalc,
                    errorCalc,
                    minCalc, maxCalc, BenchmarkUnit.OPERATIONS_PER_MILLISECOND);
            millis += step;
            results.add(interpolatedMeasurement);
        }
        return results;
    }

}
