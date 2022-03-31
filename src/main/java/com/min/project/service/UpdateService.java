package com.min.project.service;


import com.min.project.dao.UploadDao;
import com.min.project.dto.UploadDto;
import com.min.project.form.UploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateService {
    @Value("C:/testupload/")
    private String rootPath;
    @Autowired
    private UploadDao uploadDao;

    @Transactional
    public void update(Long id,
                       UploadForm uploadForm,
                       MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {


        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();

        //폴더 만들기
        String path = rootPath + current.format(format);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        System.out.println("-------------------------type:" + multipartHttpServletRequest.getClass());
        System.out.println("-------------------------type2:" + iterator.getClass());

        String newFileName;
        String originalFileExtension = null;
        String contentType;
        String uuid;

        while (iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());

            System.out.println("-------------------------List:" + list);
            for (MultipartFile multipartFile : list) {

                if (!multipartFile.isEmpty()) {
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) {
                        break;
                    } else {
                        System.out.println("file content type : " + contentType);
                        if (contentType.contains("audio/mpeg")) {
                            originalFileExtension = ".mp3";
                        } else {
                            break;
                        }
                    }
                }


                uuid = UUID.randomUUID().toString();
                newFileName = uuid + originalFileExtension;
                file = new File(path + "/" + newFileName);

                multipartFile.transferTo(file);

                try {


                    Optional<UploadDto> dao = uploadDao.findById(id);

                    UploadDto uploadDto = dao.get();
                    uploadDto.setFileSetName(newFileName);
                    uploadDto.setFilePath(path);
                    uploadDto.setTitle(uploadForm.getTitle());
                    uploadDto.setContent(uploadForm.getContent());
                    uploadDao.save(uploadDto);
                    System.out.println("---------업데이트 완료-------");

                } catch (NullPointerException e) {
                    e.printStackTrace();
                    System.out.println("으악 널값");

                }
            }
        }
    }
}
