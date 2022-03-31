package com.min.project.dao;

import com.min.project.dto.UserDto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LoginDao extends CrudRepository<UserDto, Long> {

    Optional<UserDto> findByEmail(String email);

}
