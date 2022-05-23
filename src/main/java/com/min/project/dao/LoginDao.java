package com.min.project.dao;

import com.min.project.dto.UserDto;
import com.min.project.form.UserForm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginDao extends CrudRepository<UserDto, Long> {

    Optional<UserDto> findByEmail(String email);
//    Optional<UserForm> findByEmailAndPassword(String email, String password);
    @Query("SELECT dto FROM UserDto dto WHERE dto.email = upper(:email) ")
    Optional<UserDto> findByEmai1(@Param("email") String email);
}
