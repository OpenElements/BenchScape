package com.openelements.benchscape.server.store.export;

import com.openelements.benchscape.server.store.data.Benchmark;
import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.data.Measurement;
import com.openelements.server.base.csv.CsvColumn;
import com.openelements.server.base.csv.CsvData;
import com.openelements.server.base.csv.CsvExportUtils;
import java.util.ArrayList;
import java.util.List;

public class CsvExport {


    public static void exportEnvironments(final Appendable appendable, List<Environment> environments) {
        final CsvData<Environment> csvData = getEnvironmentCsvData();
        CsvExportUtils.exportCsv(appendable, csvData, environments);
    }

    public static void exportBenchmarks(final Appendable appendable, List<Benchmark> benchmarks) {
        final CsvData<Benchmark> csvData = getBenchmarkCsvData();
        CsvExportUtils.exportCsv(appendable, csvData, benchmarks);
    }

    public static void exportMeasurements(final Appendable appendable, List<Measurement> benchmarks) {
        final CsvData<Measurement> csvData = getMeasurementCsvData();
        CsvExportUtils.exportCsv(appendable, csvData, benchmarks);
    }

    private static CsvData<Environment> getEnvironmentCsvData() {
        List<CsvColumn<Environment>> columns = new ArrayList<>();
        columns.add(new CsvColumn<>("Name", (d, h) -> d.name()));
        columns.add(new CsvColumn<>("Description", (d, h) -> d.description()));
        columns.add(new CsvColumn<>("OS", (d, h) -> d.osName() + " " + d.osVersion()));
        columns.add(new CsvColumn<>("Java", (d, h) -> d.jvmName() + " " + d.jvmVersion()));
        CsvData<Environment> csvData = new CsvData<>(columns);
        return csvData;
    }

    private static CsvData<Benchmark> getBenchmarkCsvData() {
        List<CsvColumn<Benchmark>> columns = new ArrayList<>();
        columns.add(new CsvColumn<>("Name", (d, h) -> d.name()));
        columns.add(new CsvColumn<>("Parameters", (d, h) -> d.params().toString()));
        CsvData<Benchmark> csvData = new CsvData<>(columns);
        return csvData;
    }

    private static CsvData<Measurement> getMeasurementCsvData() {
        List<CsvColumn<Measurement>> columns = new ArrayList<>();
        columns.add(new CsvColumn<>("Timestamp", (d, h) -> d.timestamp().toString()));
        columns.add(new CsvColumn<>("Value", (d, h) -> d.value() + ""));
        columns.add(new CsvColumn<>("Min", (d, h) -> d.min() + ""));
        columns.add(new CsvColumn<>("Max", (d, h) -> d.max() + ""));
        columns.add(new CsvColumn<>("Error", (d, h) -> d.error() + ""));
        columns.add(new CsvColumn<>("Unit", (d, h) -> d.unit() + ""));
        CsvData<Measurement> csvData = new CsvData<>(columns);
        return csvData;
    }

}
