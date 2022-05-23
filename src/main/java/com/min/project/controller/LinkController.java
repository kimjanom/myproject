package com.min.project.controller;

import com.min.project.config.auth.MyUserDetails;
import com.min.project.dao.UploadDao;
import com.min.project.dto.UploadDto;
import com.min.project.dto.UserDto;
import com.min.project.form.UploadForm;
import com.min.project.form.UserForm;
import com.min.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://http://localhost:8080/")
@Controller
public class LinkController {

    @Autowired
    SearchService searchService;
    @Autowired
    private UpdateService updateService;
    @Autowired
    private DeleteService deleteService;
    @Autowired
    private UploadDao dao;

    @Autowired
    private UploadServiceImpl uploadService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public String home(){


        return "index";
    }

    @GetMapping(value = "/loginview")
    public String loginview(){
        return "login";
    }

    @GetMapping(value = "/register")
    public String registerview(){
        return "register";
    }
    
    @GetMapping(value = "/admin")
    public String adminHome(){
        System.out.println("어드민 권한 페이지");
        return "admin";
    }

    @PostMapping(value = "/auth/register1")
    public String register(UserForm form){
        System.out.println("폼 파라미터 확인:"+form);
        boolean a=userService.register(form);
        if(a){
            return "redirect:/";
        }
        System.out.println("이미 존재하는 이메일 입니다");
        return "redirect:/register";
    }



//    -------------------------------- 게시판 관련
    @RequestMapping(value = "board")
    public String BoardView(Model model) {
        System.out.println("BoardView");
        List upLoadList = uploadService.getUploadlist();
        ModelAndView view = new ModelAndView();
//        view.setViewName("board");
//        view.addObject("upLoadList",upLoadList);
        model.addAttribute("upLoadList", upLoadList);
        System.out.println("게시판 정보:" + upLoadList.getClass());
//        UploadForm a=upLoadList.get(1).getClass();
//        model.addAttribute("upLoadList",)
        return "board";
    }

    @RequestMapping(value = "boardUpload")
    public ModelAndView BoardUploadView(HttpServletRequest request) {
        System.out.println("IndexView");
        System.out.println("BoardUploadView");
        return new ModelAndView("boardUpload");
    }

    @GetMapping(value = "/mp3Player/+{id}")
    public ModelAndView mp3PlayerView(@PathVariable Long id, HttpServletRequest request) {
        request.setAttribute("id", id);
        System.out.println("mp3PlayerView");
        System.out.println("id--------------" + id);
        SecurityContext securityContext= SecurityContextHolder.getContext();
        Object principal= securityContext.getAuthentication().getPrincipal();
        MyUserDetails myUserDetails = (MyUserDetails)principal;
        Optional<UploadDto> loginOpt = dao.findById(id);
        UploadDto uploadDto = loginOpt.get();
        System.out.println("--------------1");
        ModelAndView view = new ModelAndView();
        view.setViewName("mp3Player");
        view.addObject("name", uploadDto.getName());
        view.addObject("id", uploadDto.getId());
        view.addObject("title", uploadDto.getTitle());
        view.addObject("date", uploadDto.getUploadDate());
        view.addObject("content", uploadDto.getContent());
        view.addObject("email", uploadDto.getEmail());
        view.addObject("session",myUserDetails.getUsername());
        return view;
    }

    @PostMapping("/delete/+{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("id----------" + id);
        deleteService.delete(id);
        System.out.print("delete 컨트롤러 입니다");
        return "redirect:/board/";
    }

    @PostMapping("/update/+{id}")
    public String update(@PathVariable Long id,
                         UploadForm uploadForm,
                         MultipartHttpServletRequest multipartHttpServletRequest,
                         HttpServletRequest request) throws IOException {
        System.out.println("id값 확인:"+id);
        updateService.update(id,uploadForm, multipartHttpServletRequest);
        return "redirect:/board/";

    }
    @PostMapping("/updateview/+{id}")
    public String updateview(@PathVariable Long id){
        System.out.println("-------업데이트 뷰 입니다-----");
        System.out.println("id값 확인:"+id);
        return "/updatePage";
    }

    @GetMapping("/boardSearch")
    public void searcher(@RequestParam String search){
        System.out.println("서치 컨트롤러 파라미터 확인:"+search);
        List searchData=searchService.boardSearch(search);
    }
}
