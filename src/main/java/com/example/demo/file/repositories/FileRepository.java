package com.example.demo.file.repositories;

import com.example.demo.auth.entities.User;
import com.example.demo.file.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {
    List<File> getFilesByCreatedByAndDeletedIs(User user,boolean isDeleted);
}
