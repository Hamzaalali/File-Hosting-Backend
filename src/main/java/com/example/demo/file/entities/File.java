package com.example.demo.file.entities;
import com.example.demo.auth.entities.User;
import com.example.demo.file.requests.UpdateFileRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private String url;
    private String updatedAt;
    private String createdAt;
    private boolean deleted;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User createdBy;
}
