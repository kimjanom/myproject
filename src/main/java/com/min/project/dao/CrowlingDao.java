package com.min.project.dao;

import com.min.project.dto.CrowlingDto;
import com.min.project.dto.UserDto;
import org.springframework.data.repository.CrudRepository;

public interface CrowlingDao extends CrudRepository<CrowlingDto, Long> {
}
