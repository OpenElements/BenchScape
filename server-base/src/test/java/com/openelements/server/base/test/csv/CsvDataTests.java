package com.openelements.server.base.test.csv;

import com.openelements.server.base.csv.CsvColumn;
import com.openelements.server.base.csv.CsvData;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CsvDataTests {

    @Test
    void testConstruction() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new CsvData(null);
        });
        Assertions.assertDoesNotThrow(() -> {
            new CsvData<>(List.of());
        });
    }

    @Test
    void testGetHeaderForEmpty() {
        //given
        final CsvData<String> csvData = new CsvData<>(List.of());

        //when
        final String[] header = csvData.getHeader();

        //then
        Assertions.assertNotNull(header);
        Assertions.assertEquals(0, header.length);
    }

    @Test
    void testGetHeader() {
        //given
        final CsvColumn<String> column1 = new CsvColumn<>("header1", (s, h) -> s);
        final CsvColumn<String> column2 = new CsvColumn<>("header2", (s, h) -> s);
        final CsvColumn<String> column3 = new CsvColumn<>("header3", (s, h) -> s);
        final CsvData<String> csvData = new CsvData<>(List.of(column1, column2, column3));

        //when
        final String[] header = csvData.getHeader();

        //then
        Assertions.assertNotNull(header);
        Assertions.assertEquals(3, header.length);
        Assertions.assertEquals("header1", header[0]);
        Assertions.assertEquals("header2", header[1]);
        Assertions.assertEquals("header3", header[2]);
    }

    @Test
    void testGetDataForEmpty() {
        //given
        final CsvData<LocalDateTime> csvData = new CsvData<>(List.of());

        //when
        final Stream<String> data = csvData.getData(LocalDateTime.now());

        //then
        Assertions.assertNotNull(data);
        List<String> list = data.toList();
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void testGetDataForNullObject() {
        //given
        final CsvData<LocalDateTime> csvData = new CsvData<>(List.of());

        //then
        Assertions.assertThrows(NullPointerException.class, () -> {
            csvData.getData(null);
        });
    }

    @Test
    void testGetData() {
        //given
        final CsvColumn<LocalDateTime> column1 = new CsvColumn<>("Year",
                (s, h) -> s.get(ChronoField.YEAR) + "");
        final CsvColumn<LocalDateTime> column2 = new CsvColumn<>("Month",
                (s, h) -> s.get(ChronoField.MONTH_OF_YEAR) + "");
        final CsvColumn<LocalDateTime> column3 = new CsvColumn<>("Day",
                (s, h) -> s.get(ChronoField.DAY_OF_MONTH) + "");
        final CsvData<LocalDateTime> csvData = new CsvData<>(List.of(column1, column2, column3));
        final LocalDateTime testData = LocalDateTime.of(2023, 12, 24, 12, 0);

        //when
        final Stream<String> data = csvData.getData(testData);

        //then
        Assertions.assertNotNull(data);
        List<String> list = data.toList();
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("2023", list.get(0));
        Assertions.assertEquals("12", list.get(1));
        Assertions.assertEquals("24", list.get(2));
    }

}
