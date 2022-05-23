package com.min.project.dao;

import com.min.project.dto.TwitterDto;
import com.min.project.dto.UploadDto;
import com.min.project.dto.UserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TwitterDao extends CrudRepository<TwitterDto, Long> {
    TwitterDto findByTwitId(String twit);

}
