package com.openelements.benchscape.server.store.export;

import com.openelements.benchscape.server.store.data.Environment;
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

    private static CsvData<Environment> getEnvironmentCsvData() {
        List<CsvColumn<Environment>> columns = new ArrayList<>();
        columns.add(new CsvColumn<>("Name", (d, h) -> d.name()));
        columns.add(new CsvColumn<>("Description", (d, h) -> d.description()));
        columns.add(new CsvColumn<>("OS", (d, h) -> d.osName() + " " + d.osVersion()));
        columns.add(new CsvColumn<>("Java", (d, h) -> d.jvmName() + " " + d.jvmVersion()));
        CsvData<Environment> csvData = new CsvData<>(columns);
        return csvData;
    }

}
