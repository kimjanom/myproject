package com.min.project.service;


import com.min.project.dao.UploadDao;
import com.min.project.dto.UploadDto;
import com.min.project.form.UploadForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    @Autowired
    UploadDao uploadDao;
    @Override
    public void insertDataTest(UploadDto dto, MultipartHttpServletRequest multipartHttpServletRequest)  {
        System.out.println("insertData");
        System.out.println("checker"+ObjectUtils.isEmpty(multipartHttpServletRequest));
        if(!ObjectUtils.isEmpty(multipartHttpServletRequest)){
            Iterator<String> interator = multipartHttpServletRequest.getFileNames();
            String name;
            while (interator.hasNext()){
                name=interator.next();
                System.out.println("file tag name:"+name);

                List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
                for(MultipartFile multipartFile : list) {
                    System.out.println("start file information");
                    System.out.println("file name : " + multipartFile.getOriginalFilename());
                    System.out.println("file size : " + multipartFile.getSize());
                    System.out.println("file content type : " + multipartFile.getContentType());
                    System.out.println("end file information.\n");
                }

            }
        }
    }

    @Override
    @Transactional(rollbackFor = NullPointerException.class)
    public void insertData(UploadDto dto, MultipartHttpServletRequest multipartHttpServletRequest) {

    }

    @Override
    @Transactional
    public List<UploadForm> getUploadlist(){
        List<UploadDto> boards = (List<UploadDto>) uploadDao.findAll();
        List<UploadForm> uploadDtoList =new ArrayList<>();

        for(UploadDto dto: boards){
            UploadForm uploadForm =UploadForm.builder()
                    .id(dto.getId())
                    .email(dto.getEmail())
                    .name(dto.getName())
                    .title(dto.getTitle())
                    .fileOriginalName(dto.getFileOriginalName())
                    .content(dto.getContent())
                    .filePath(dto.getFilePath())
                    .uploadDate(dto.getUploadDate())
                    .build();
            uploadDtoList.add(uploadForm);
        }
        return uploadDtoList;
    }

//    @Override
//    public UploadDto findById(Long id){
//        Optional<UploadDto> data=uploadDao.findById(id);
//        UploadDto dto=data.get();
//        System.out.println("-----------------type Optinal"+data.getClass());
//        System.out.println("-----------------data"+data);
//
//    }

}
