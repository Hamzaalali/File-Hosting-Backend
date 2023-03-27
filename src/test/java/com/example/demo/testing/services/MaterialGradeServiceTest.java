package com.example.demo.testing.services;
import com.example.demo.MongoDBTest;
import com.example.demo.testing.collection.MaterialGrade;
import com.example.demo.testing.repository.MaterialGradeRepo;
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
class MaterialGradeServiceTest extends MongoDBTest {
    private final MaterialGradeService materialGradeService;
    private final MaterialGradeRepo materialGradeRepo;
    @Test
    void addUtTest() throws IOException {

        File summeryFile = new File("src/test/data/S232/S232_UT.csv");
        MultipartFile summeryFileMultipart = new MockMultipartFile("S232_UT-TD1.csv",
                "S232_UT-TD1.csv", "text/plain", Files.readAllBytes(summeryFile.toPath()));
        //given
        MaterialGrade materialGrade=new MaterialGrade();
        materialGrade.setProvider("TEST");
        materialGrade.setName("TEST");
        //when
        MaterialGrade materialGrade1=materialGradeRepo.save(materialGrade);
        materialGradeService.addUtTest(materialGrade1.getId(),1,summeryFileMultipart);
        //then
        assertNotNull(materialGradeService.getUtTest(materialGrade1.getId()));
    }
}