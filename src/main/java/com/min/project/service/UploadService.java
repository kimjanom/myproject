package com.min.project.service;


import com.min.project.dto.UploadDto;
import com.min.project.form.UploadForm;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


public interface UploadService {
    public void insertDataTest(UploadDto dto, MultipartHttpServletRequest multipartHttpServletRequest);
    public void insertData(UploadDto dto, MultipartHttpServletRequest multipartHttpServletRequest);
    public List<UploadForm> getUploadlist();
//    public UploadDto findById(Long id);


}
