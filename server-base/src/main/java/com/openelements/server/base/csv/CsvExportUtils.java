package com.openelements.server.base.csv;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvExportUtils {

    private CsvExportUtils() {

    }

    public static <D> void exportCsv(@NonNull final Appendable appendable, @NonNull final CsvData<D> csvData,
            @NonNull final List<D> data) {
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
                } catch (final Exception e) {
                    throw new RuntimeException("Error printing csv for entry '" + d + "'", e);
                }
            });
        } catch (final Exception e) {
            throw new RuntimeException("Error printing CSV", e);
        }
    }
}
