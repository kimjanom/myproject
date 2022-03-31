package com.min.project.controller;

import com.min.project.form.UploadForm;
import com.min.project.service.UploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class VueController {

    @Autowired
    UploadServiceImpl uploadService;
    @GetMapping("/vue/board")
    public List<UploadForm> getBoardList(){
        System.out.println("getBoardList 도착");
        List<UploadForm> list=  uploadService.getUploadlist();
        return list;
    }
}
