package com.min.project.form;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UploadForm {
    private Long id;
    private String email;
    private String name;
    private String title;
    private String fileOriginalName;
    private String fileSetName;
    private String content;
    private String filePath;
    private LocalDateTime uploadDate;
    private int views;
    private String category;
}
//    public UploadDto toEntity(){
//        return new UploadDto(1l,email,name,title,uploadDate,content);
//    }
//}
