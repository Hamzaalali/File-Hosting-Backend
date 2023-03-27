package com.example.demo.testing.services;

import com.example.demo.MongoDBTest;
import com.example.demo.testing.collection.UtTestTestData;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class TestDataServiceUtTestTest extends MongoDBTest {
    private final TestDataService testDataService;
    @Test
    void create() throws IOException {
        //given
        File dd1 = new File("src/test/data/S232/S232_UT-DD1.csv");
        MultipartFile dd1Multipart = new MockMultipartFile("S232_UT-DD1.csv",
                "S232_UT-DD1.csv", "text/plain", Files.readAllBytes(dd1.toPath()));
        int numberOfColumnNamesColumns=1;
        //when
        UtTestTestData utTestTestData =testDataService.create(dd1Multipart,1);
        //then
        assertNotNull(testDataService.getOne(utTestTestData.getId()));
    }
}