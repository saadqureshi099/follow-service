package com.instagram.Follow.clients;


import com.instagram.Follow.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service" , url = "http://user-service:8085/auth")
public interface UserServiceClient {
    @GetMapping(value = "/user/{userid}")
    ResponseEntity<UserDto> findByUserId(@PathVariable long userid);
}