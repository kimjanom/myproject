package com.min.project.controller;


import com.min.project.config.auth.MyUserDetails;
import com.min.project.dao.JoinDao;
import com.min.project.dao.UploadDao;
import com.min.project.dto.UploadDto;
import com.min.project.dto.UserDto;
import com.min.project.form.UserForm;
import com.min.project.service.UploadServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//import net.utility.UploadSaveManager;
//import net.utility.Utility;
@CrossOrigin(origins = "http://http://localhost:8080/")
@RestController
public class RestControllerTest {
    // 현재 날짜 구하기
    LocalDate now = LocalDate.now();
    // 포맷 정의
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    // 포맷 적용
    String formatedNow = now.format(formatter);



    @Value("C:/testupload/")
    String rootPath;

    @Autowired
    JoinDao joinDao;

//


    @Autowired
    UploadServiceImpl impl;
    @Autowired
    private UploadDao dao;
//    UploadDto updto=new UploadDto();
//    @RequestMapping(value = "upload/test", method = RequestMethod.POST)
//    public String uploadDataCheckController(UploadDto upload, MultipartHttpServletRequest multipartHttpServletRequest){
////        String basePath=req.getServletContext().getRealPath("/testupload/");
////        MultipartFile mf=form.getFile();
//        System.out.println("uploadDataCheckController");
//        System.out.println("dto data:"+upload);
//        System.out.println("multipartHttpServletRequest data:"+multipartHttpServletRequest.getFileNames());
//        System.out.println("multipartHttpServletRequest data:"+multipartHttpServletRequest);
////        System.out.println(mf);
////        String filename =dao.sa
//        impl.insertDataTest(upload,multipartHttpServletRequest);
//        return "boardupload";
//
//    }

    @SneakyThrows
    @RequestMapping(value = "upload/media", method = RequestMethod.POST)
    public ModelAndView uploadDataController(UploadDto upload,
                                             MultipartHttpServletRequest multipartHttpServletRequest,
                                             HttpServletRequest request )throws Exception {
//      세션에서 이메일 정보 가져오기
        System.out.println("IndexView");

        SecurityContext securityContext= SecurityContextHolder.getContext();
        System.out.println("securityContext.getAuthentication():"+securityContext.getAuthentication());
        System.out.println("securityContext.getAuthentication()2:"+securityContext.getAuthentication().getPrincipal());
        Object principal= securityContext.getAuthentication().getPrincipal();
        MyUserDetails userDto= (MyUserDetails)principal;
        UserDto name = joinDao.findByEmail(userDto.getUsername());

        System.out.println("name--"+name.getUsername());

        if (ObjectUtils.isEmpty(multipartHttpServletRequest)) {
            return null;
        }

        List<UploadDto> fileList = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        //폴더 만들기
        String path = rootPath + current.format(format);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        System.out.println("-------------------------type:"+multipartHttpServletRequest.getClass());
        System.out.println("-------------------------type2:"+iterator.getClass());

        String newFileName, originalFileExtension = null, contentType, uuid;

        while (iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());

            System.out.println("-------------------------List:"+list);
            for (MultipartFile multipartFile : list) {
                System.out.println("-------------------------");
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
                uuid=UUID.randomUUID().toString();
                newFileName = uuid + originalFileExtension;
//                UploadDto dto = new UploadDto();
                //-----------------------dto에 데이터 삽입
                upload.setFileSize(multipartFile.getSize());
                upload.setFileOriginalName(multipartFile.getOriginalFilename());
                upload.setFileSetName(newFileName);
                upload.setFilePath(path);

                upload.setEmail(name.getEmail());
                upload.setName(name.getUsername());
//                upload.setUploadDate(formatedNow);

                //----------------------dto에 데이터 삽입
                file = new File(path + "/" + newFileName);
                System.out.println("------------------dto 데이터:"+upload);
                System.out.println("------------------path데이터:"+path);
                System.out.println("------------------newfilename 데이터:"+newFileName);
                impl.insertData(upload,multipartHttpServletRequest);
                multipartFile.transferTo(file);
                dao.save(upload);



            }
        }
        return new ModelAndView("redirect:/board");
    }

//    @RequestMapping(value = "board")
//    public ModelAndView BoardView(){
//        System.out.println("BoardView");
//        return new ModelAndView("board");
//    }

//    @RequestMapping(value = "test1")
//    public int music(){
//
//        File file=new File("C:\\testupload\\202203101d71dbe8-5869-4c97-9182-ca8136e7b0f4");
//        System.out.print("music data:"+file);
//        return 1;
//    }
//
//    public String getExtension(String fileName) {
//        return fileName.substring(fileName.lastIndexOf(".") + 1);
//    }

//    @Value("${custom.server.audioPath}")
//    private String audioPath;
//    @GetMapping(value = "audio-link")
//    public ResponseEntity<byte[]> audioLink(@RequestParam("path") String path) {
//        FileInputStream fis = null;
//        ResponseEntity<byte[]> responseEntity = null;
//        try {
//            HttpHeaders responseHeaders = new HttpHeaders();
//            File file = new File(audioPath + path);
//            responseHeaders.add("Content-Type", "audio/mpeg");
//            if (!file.exists()) {
//                return new ResponseEntity<>(new byte[0], responseHeaders, HttpStatus.NOT_FOUND);
//            }
//
//            fis = new FileInputStream(file);
//            int size = (int) file.length();
//            byte[] buf = new byte[size];
//            fis.read(buf, 0, size);
//            responseEntity = new ResponseEntity<>(buf, responseHeaders, HttpStatus.CREATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fis != null) {
//                    fis.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return responseEntity;
//    }

//    @GetMapping("/onlystatus")
//    public ResponseEntity onlyStatus(){
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @RequestMapping("/handle")
//    public ResponseEntity<String> handle() {
//        URI location = null;
//        try {
//            location = new URI("http://localhost:8080/aa");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.setLocation(location);
//        responseHeaders.set("MyResponseHeader", "MyValue");
//        File file = new File("C:\\Users\\e2on\\Desktop\\SellBuyMusic - Ending.mp3");
//        return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
//    }

    //mp3 다운로드?
    //포스트 바꾸기
    @PostMapping("/Download/+{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Resource> mp3Download(ModelAndView modelAndView,
                                               @PathVariable Long id,
                                               HttpServletRequest request,
                                               HttpServletResponse response) throws Exception{

        Optional<UploadDto> data=dao.findById(id);
        UploadDto dto=data.get();
        System.out.println("-----------------type Optinal"+data.getClass());
        System.out.println("-----------------data"+data);
        System.out.println("-----------------dto"+dto);
        File initFile= new File(dto.getFilePath()+"/"+dto.getFileSetName());

        String downloadName = dto.getFileOriginalName();
        String browser = request.getHeader("User-Agent");
        System.out.println("---------------user Agent"+browser);
        System.out.println("---------------downloadName1:"+downloadName);
        //파일 인코딩
        if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
            //브라우저 확인 파일명 encode
            System.out.println("---------------user Agent1");
            downloadName = URLEncoder.encode(downloadName, "UTF-8").replaceAll("\\+", "%20");
            System.out.println("---------------downloadName2:"+downloadName);
        }else{
            System.out.println("---------------user Agent2");
            downloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");
        }

        InputStreamResource resource =new InputStreamResource(new FileInputStream(initFile));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "filename=\"" + downloadName +"\"");
        headers.set("Accept-Ranges", "bytes");
        headers.set("Content-Length", initFile.length() + "");
//        headers.set("Content-Type","audio/mp3");
        System.out.println("---------------Content-Length:"+initFile.length());
        headers.set("Content-Transfer-Encoding", "binary;");
        System.out.println("---------------end");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(initFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping(value = "/audioLink/+{id}.mp3")
    public ResponseEntity<byte[]> AudioLink(@PathVariable Long id) throws IOException {
        Optional<UploadDto> data=dao.findById(id);
        UploadDto dto=data.get();

        FileInputStream fis=null;
        ResponseEntity<byte[]> responseEntity = null;

        HttpHeaders responseHeaders = new HttpHeaders();
        File file =new File(dto.getFilePath()+"/"+dto.getFileSetName());
        responseHeaders.add("Content-Type","audio/mp3");

        try {
            fis=new FileInputStream(file);
            int initFileSize=(int)file.length();
            byte[] buf =new byte[initFileSize];
            System.out.println("-------------------------buf:"+buf);
            System.out.println("-------------------------fis:"+fis);
            fis.read(buf,0,initFileSize);
            responseEntity=new ResponseEntity<>(buf,responseHeaders,HttpStatus.OK);
            System.out.println("-------------------------responseEntity:"+responseEntity);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new byte[0], responseHeaders, HttpStatus.NOT_FOUND);
        }finally {

                fis.close();

        }
        return responseEntity;


    }



//    @GetMapping(value = "/audioLink/+{id}.mp3")
//    public ResponseEntity<FileInputStream> AudioLink(@PathVariable Long id) throws Exception{
//
//        Optional<UploadDto> data=dao.findById(id);
//        UploadDto dto=data.get();
//        System.out.println("-----------------type Optinal"+data.getClass());
//        System.out.println("-----------------data"+data);
//        System.out.println("-----------------dto"+dto);
//        File initFile= new File(dto.getFilePath());
//
//        String downloadName = dto.getFileOriginalName();
//        FileInputStream fis= new FileInputStream(initFile);
//
////        System.out.println("---------------user Agent"+browser);
////        System.out.println("---------------downloadName1:"+downloadName);
////        //파일 인코딩
////        if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){
////            //브라우저 확인 파일명 encode
////            System.out.println("---------------user Agent1");
////            downloadName = URLEncoder.encode(downloadName, "UTF-8").replaceAll("\\+", "%20");
////            System.out.println("---------------downloadName2:"+downloadName);
////        }else{
////            System.out.println("---------------user Agent2");
////            downloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");
////        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Disposition", "filename=\"" + downloadName +"\"");
//        headers.set("Accept-Ranges", "bytes");
//        headers.set("Content-Length", initFile.length() + "");
//        System.out.println("---------------Content-Length:"+initFile.length());
//        headers.set("Content-Transfer-Encoding", "binary;");
//        System.out.println("---------------fis:"+fis);
//        System.out.println("---------------end");
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(initFile.length())
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(fis);
//    }
//    @GetMapping(value = "/audioLink/+{id}.mp3")
//    public ResponseEntity<byte[]> AudioLink(@PathVariable Long id) throws IOException {
//        Optional<UploadDto> data=dao.findById(id);
//        UploadDto dto=data.get();
//        File f = new File(dto.getFilePath());
//        byte[] file = Files.readAllBytes(f.toPath());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Disposition", "attachment; filename=\"" + f.getName() +".wav\"");
//        System.out.println("------------------------------------------f:"+f.getName());
//        ResponseEntity<byte[]> response = new ResponseEntity(file, headers, HttpStatus.OK);
//        System.out.print("------------------------------------------");
//        return response;
//    }


}

