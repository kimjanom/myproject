package com.min.project.service;


import com.min.project.dao.UploadDao;
import com.min.project.dto.UploadDto;
import com.min.project.dto.UserDto;
import com.min.project.form.UploadForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    // 현재 날짜 구하기
    LocalDate now = LocalDate.now();
    // 포맷 정의
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    // 포맷 적용
    String formatedNow = now.format(formatter);
    @Autowired
    private UploadDao dao;
    @Autowired
    private UploadServiceImpl impl;
    @Value("C:/testupload/")
    String rootPath;
    @Autowired
    private UploadDao uploadDao;
    @Transactional
    @Override
    public Boolean upLoadAlbum(UserDto name, UploadDto uploadDto, MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {


        if (ObjectUtils.isEmpty(multipartHttpServletRequest)) {
            return false;
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();

        //폴더 만들기
        String path = rootPath + current.format(format);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        //이미지와 음원 폴더 분할
        String pathAudio = path + "/" + "Audio";
        File fileAudio = new File(pathAudio);
        if (!fileAudio.exists()) {
            fileAudio.mkdirs();
        }

        String pathImage = path + "/" + "Image";
        File fileImage = new File(pathImage);
        if (!fileImage.exists()) {
            fileImage.mkdirs();
        }
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        String newFileName;
        String originalFileExtension = null;
        String contentType;
        String uuid;

        while (iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list) {
                if (!multipartFile.isEmpty()) {
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) {
                        return false;
                    } else {
                        System.out.println("file content type : " + contentType);
                        if (contentType.contains("audio/mpeg")) {
                            originalFileExtension = ".mp3";
                            uuid = UUID.randomUUID().toString();
                            newFileName = uuid + originalFileExtension;
                            uploadDto.setFileSize(multipartFile.getSize());
                            uploadDto.setFileOriginalName(multipartFile.getOriginalFilename());
                            uploadDto.setFileSetName(newFileName);
                            uploadDto.setFilePath(pathAudio);
                            file = new File(pathAudio + "/" + newFileName);
                            multipartFile.transferTo(file);
                        } else if (contentType.contains("image/gif")) {
                            originalFileExtension = ".gif";
                            uuid= UUID.randomUUID().toString();
                            newFileName = uuid + originalFileExtension;
                            uploadDto.setFileSetNameImage(newFileName);
                            uploadDto.setImageContent(contentType);
                            uploadDto.setFilePathImage(pathImage);
                            file = new File(pathImage + "/" + newFileName);
                            multipartFile.transferTo(file);

                        }
                        else if(contentType.contains("image/png")){
                            originalFileExtension = ".png";
                            uuid= UUID.randomUUID().toString();
                            newFileName = uuid + originalFileExtension;
                            uploadDto.setFileSetNameImage(newFileName);
                            uploadDto.setImageContent(contentType);
                            uploadDto.setFilePathImage(pathImage);
                            file = new File(pathImage + "/" + newFileName);
                            multipartFile.transferTo(file);
                        }
                        else if(contentType.contains("image/jpeg")){
                            originalFileExtension = ".jpg";
                            uuid= UUID.randomUUID().toString();
                            newFileName = uuid + originalFileExtension;
                            uploadDto.setFileSetNameImage(newFileName);
                            uploadDto.setImageContent(contentType);
                            uploadDto.setFilePathImage(pathImage);
                            file = new File(pathImage + "/" + newFileName);
                            multipartFile.transferTo(file);
                        }
                    }
                }
            }

        }
        uploadDto.setCategory(uploadDto.getCategory());
        uploadDto.setViews(0);
        uploadDto.setEmail(name.getEmail());
        uploadDto.setName(name.getUsername());
        impl.insertData(uploadDto,multipartHttpServletRequest);
        dao.save(uploadDto);
        return true;
    }

    @Override
    public void insertDataTest(UploadDto dto, MultipartHttpServletRequest multipartHttpServletRequest) {
        System.out.println("insertData");
        System.out.println("checker" + ObjectUtils.isEmpty(multipartHttpServletRequest));
        if (!ObjectUtils.isEmpty(multipartHttpServletRequest)) {
            Iterator<String> interator = multipartHttpServletRequest.getFileNames();
            String name;
            while (interator.hasNext()) {
                name = interator.next();
                System.out.println("file tag name:" + name);

                List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
                for (MultipartFile multipartFile : list) {
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
    public List<UploadForm> getUploadlist() {
        List<UploadDto> boards = (List<UploadDto>) uploadDao.findAll();
        List<UploadForm> uploadDtoList = new ArrayList<>();

        for (UploadDto dto : boards) {
            UploadForm uploadForm = UploadForm.builder()
                    .id(dto.getId())

                    .email(dto.getEmail())
                    .name(dto.getName())
                    .title(dto.getTitle())
                    .fileOriginalName(dto.getFileOriginalName())
                    .content(dto.getContent())
                    .filePath(dto.getFilePath())
                    .uploadDate(dto.getUploadDate())
                    .views(dto.getViews())
                    .build();
            uploadDtoList.add(uploadForm);
        }
        return uploadDtoList;
    }

    @Override
    @Transactional
    public List<UploadForm> getVueUploadlist() {
        List<UploadDto> boards = (List<UploadDto>) uploadDao.findAllCategoryDesc();
        List<UploadForm> uploadDtoList = new ArrayList<>();
        for (UploadDto dto : boards) {
            UploadForm uploadForm = UploadForm.builder()
                    .id(dto.getId())
                    .email(dto.getEmail())
                    .name(dto.getName())
                    .title(dto.getTitle())
                    .fileOriginalName(dto.getFileOriginalName())
                    .content(dto.getContent())
                    .filePath(dto.getFilePath())
                    .uploadDate(dto.getUploadDate())
                    .views(dto.getViews())
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
