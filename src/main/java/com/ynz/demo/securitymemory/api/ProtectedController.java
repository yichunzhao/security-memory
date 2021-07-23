package com.ynz.demo.securitymemory.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("admin")
@Slf4j
@RequiredArgsConstructor
public class ProtectedController {

    @GetMapping("data")
    String getProtectedData(Authentication authentication) {
        String userName = authentication.getName();
        List<String> auths = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList());

        log.info("login user: {}, with authorities {}", userName, auths);

        return "visible to admin alone";
    }

}
