package com.example.demo.testing.csv;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;
class CsvByColumnsTest {
    @Test
    void of() throws IOException {
        //given
        File testingData = new File("src/test/data/TESTING.csv");
        MultipartFile testingDataMultipart = new MockMultipartFile("S232_UT-TD1.csv",
                "TESTING.csv", "text/plain", Files.readAllBytes(testingData.toPath()));
        int numberOfColumnNamesColumns=1;
        //when
        CsvByColumns csvByColumns=CsvByColumns.of(testingDataMultipart,numberOfColumnNamesColumns);
        //then
        assertTrue(csvByColumns.getColumnsNames().size()==1);
        assertTrue(csvByColumns.getColumns().size()==2);
        assertTrue(Objects.equals(csvByColumns.getColumns().get(0).get(0), "Data1"));
        assertTrue(Objects.equals(csvByColumns.getColumns().get(0).get(1), "Data3"));
        assertTrue(Objects.equals(csvByColumns.getColumns().get(1).get(0), "Data2"));
        assertTrue(Objects.equals(csvByColumns.getColumns().get(1).get(1), "Data4"));
    }
}