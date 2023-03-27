package com.example.demo.testing.services;
import com.example.demo.MongoDBTest;
import com.example.demo.testing.collection.Material;
import com.example.demo.testing.collection.MaterialGrade;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MaterialServiceTest  extends MongoDBTest {
    private final MaterialService materialService;
    @Test
    void itShouldCreateAMaterial() {
        //given
        Material material=new Material();
        material.setName("TEST");
        //when
        material=materialService.create(material);
        //then
        assertNotNull(materialService.getOne(material.getId()));
    }
    @Test
    void get() {
        //given
        Material material=new Material();
        material.setName("TEST");
        Material material1=new Material();
        material1.setName("TEST");
        //when
        materialService.create(material);
        materialService.create(material1);
        //then
        assertTrue(materialService.get().size()==2);
    }

    @Test
    void getOne() {
        //given
        Material material=new Material();
        material.setName("TEST");
        //when
        material=materialService.create(material);
        //then
        assertTrue(materialService.get().size()==1);
    }

    @Test
    void getGrades() {
        //given
        Material material=new Material();
        material.setName("TEST");
        MaterialGrade materialGrade=new MaterialGrade();
        materialGrade.setProvider("TEST");
        materialGrade.setName("TEST");
        //when
        material=materialService.create(material);
        materialGrade=materialService.createGrade(material.getId(),materialGrade);
        //then
        assertTrue(materialService.getGrades(material.getId()).size()==1);
    }

    @Test
    void createGrade() {
        //given
        Material material=new Material();
        material.setName("TEST");
        MaterialGrade materialGrade=new MaterialGrade();
        materialGrade.setProvider("TEST");
        materialGrade.setName("TEST");
        //when
        material=materialService.create(material);
        materialGrade=materialService.createGrade(material.getId(),materialGrade);
        //then
        assertNotNull(materialGrade);
    }
}