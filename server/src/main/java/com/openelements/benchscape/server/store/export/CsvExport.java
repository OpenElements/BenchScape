package com.openelements.benchscape.server.store.export;

import com.openelements.benchscape.server.store.data.Environment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvExport {

    private record CsvColumn<D>(String header, BiFunction<D, String, String> extractForHeader) {
    }

    private record CsvData<D>(List<CsvColumn<D>> columns) {
        public String[] getHeader() {
            return columns().stream()
                    .map(CsvColumn::header)
                    .collect(Collectors.toList())
                    .toArray(new String[0]);
        }

        public Stream<String> getData(D data) {
            return columns().stream()
                    .map(c -> c.extractForHeader().apply(data, c.header()));
        }
    }

    public static void exportEnvironments(final Appendable appendable, List<Environment> environments) {
        final CsvData<Environment> csvData = getEnvironmentCsvData();
        exportCsv(appendable, csvData, environments);
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

    private static <D> void exportCsv(final Appendable appendable, final CsvData<D> csvData, final List<D> data) {
        Objects.requireNonNull(appendable, "appendable must not be null");
        Objects.requireNonNull(csvData, "csvData must not be null");
        Objects.requireNonNull(data, "data must not be null");
        final CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(csvData.getHeader())
                .build();
        try (final CSVPrinter printer = new CSVPrinter(appendable, csvFormat)) {
            data.forEach(d -> {
                try {
                    printer.printRecord(csvData.getData(d));
                } catch (IOException e) {
                    throw new RuntimeException("Error printing entry  '" + d + "'", e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error printing CSV", e);
        }
    }

}
