package com.min.project.dao;


import com.min.project.dto.UploadDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UploadDao extends CrudRepository<UploadDto, Long> {

    Optional<UploadDto> findById(Long a);
    Optional<UploadDto> findByFileSetNameAndFilePath(String FileSetName, String FilePath);
    @Query("SELECT dto FROM UploadDto dto ORDER BY dto.views DESC")
    Iterable<UploadDto> findAllCategoryDesc();
    @Transactional
    @Modifying
    @Query("UPDATE UploadDto dto SET dto.views = dto.views+1 WHERE dto.id = (:id)")
    void updateVisit(@Param("id") Long id);

}
