package com.min.project.dao;

import com.min.project.dto.UploadDto;
import com.min.project.dto.UserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchDao extends CrudRepository<UploadDto, Long> {
    @Query("SELECT dto FROM UploadDto dto WHERE dto.title LIKE concat('%',upper(:search) ,'%') ")
    List<UploadDto> findBySearch(@Param("search") String search);

}
