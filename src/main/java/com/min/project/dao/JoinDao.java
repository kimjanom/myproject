package com.min.project.dao;

import com.min.project.dto.UserDto;
import org.springframework.data.repository.CrudRepository;

public interface JoinDao extends CrudRepository<UserDto, Long> {

    UserDto findByEmail(String email);
}