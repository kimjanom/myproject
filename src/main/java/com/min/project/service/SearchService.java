package com.min.project.service;

import com.min.project.dao.SearchDao;
import com.min.project.dto.UploadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    SearchDao searchDao;
    public List<UploadDto> boardSearch(String data){
        List<UploadDto> searchList =searchDao.findBySearch(data);
        System.out.println("서치서비스에 서치리스트:"+searchList);
        return searchList;
    }
}
