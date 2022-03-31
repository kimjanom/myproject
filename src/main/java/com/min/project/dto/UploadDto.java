package com.min.project.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
public class UploadDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 400)
    private String title;
    @Column(nullable = true, length = 200)
    private String filePath;
    @Column(nullable = true, length = 200)
    private long fileSize;
    @Column(nullable = true, length = 200)
    private String fileSetName;
    @Column(nullable = false, length = 600)
    private String fileOriginalName;
    @Column(nullable = false, length = 600)
    @CreationTimestamp
    private LocalDateTime uploadDate;
    @Column(nullable = false, length = 200)
    private String content;


    public UploadDto(Long id, String email, String name, String title, LocalDateTime uploadDate, String content) {
    }
}
