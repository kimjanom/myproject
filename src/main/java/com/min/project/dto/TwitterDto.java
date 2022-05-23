package com.min.project.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
public class TwitterDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 500)
    private String url;
    @Column(nullable = false, length = 500)
    private String imageUrl;
    @Column(nullable = false, length = 500)
    private String hash;
    @Column(nullable = false, length = 500)
    private String contect;
    @Column(nullable = false, length = 500)
    private String twitId;
}
