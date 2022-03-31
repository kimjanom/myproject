package com.min.project.dao;


import com.min.project.dto.UploadDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadDao extends CrudRepository<UploadDto, Long> {

    Optional<UploadDto> findById(Long a);
    Optional<UploadDto> findByFileSetNameAndFilePath(String FileSetName, String FilePath);

}
