package com.example.demo.testing.services;

import com.example.demo.MongoDBTest;
import com.example.demo.testing.collection.UtTest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class UtTestServiceUtTestTestData extends MongoDBTest {
    private final UtTestService utTestService;
    @Test
    void createUtTest() throws IOException {
        //given
        File summeryFile = new File("src/test/data/S232/S232_UT.csv");
        MultipartFile summeryFileMultipart = new MockMultipartFile("S232_UT-TD1.csv",
                "S232_UT-TD1.csv", "text/plain", Files.readAllBytes(summeryFile.toPath()));
        int numberOfBatches=2;
        //when
        UtTest utTest=utTestService.createUtTest(numberOfBatches,summeryFileMultipart);
        //then
        assertNotNull(utTestService.get().size()==1);
    }

    @Test
    void addBatch() throws IOException {
        //given
        File dd1 = new File("src/test/data/S232/S232_UT-DD1.csv");
        MultipartFile dd1Multipart = new MockMultipartFile("S232_UT-DD1.csv",
                "S232_UT-DD1.csv", "text/plain", Files.readAllBytes(dd1.toPath()));
        File rd1 = new File("src/test/data/S232/S232_UT-RD1.csv");
        MultipartFile rd1Multipart = new MockMultipartFile("S232_UT-RD1.csv",
                "S232_UT-RD1.csv", "text/plain", Files.readAllBytes(dd1.toPath()));
        File td1 = new File("src/test/data/S232/S232_UT-TD1.csv");
        MultipartFile td1Multipart = new MockMultipartFile("S232_UT-TD1.csv",
                "S232_UT-TD1.csv", "text/plain", Files.readAllBytes(dd1.toPath()));
        File summeryFile = new File("src/test/data/S232/S232_UT.csv");
        MultipartFile summeryFileMultipart = new MockMultipartFile("S232_UT-TD1.csv",
                "S232_UT-TD1.csv", "text/plain", Files.readAllBytes(summeryFile.toPath()));
        int numberOfBatches=2;
        //when
        UtTest utTest=utTestService.createUtTest(numberOfBatches,summeryFileMultipart);
        utTest=utTestService.getOne(utTest.getId());
        //then

    }

    @Test
    void getOne() throws IOException {
        //given
        File summeryFile = new File("src/test/data/S232/S232_UT.csv");
        MultipartFile summeryFileMultipart = new MockMultipartFile("S232_UT-TD1.csv",
                "S232_UT-TD1.csv", "text/plain", Files.readAllBytes(summeryFile.toPath()));
        int numberOfBatches=2;
        //when
        UtTest utTest=utTestService.createUtTest(numberOfBatches,summeryFileMultipart);
        //then
        assertNotNull(utTestService.getOne(utTest.getId()));
    }
}