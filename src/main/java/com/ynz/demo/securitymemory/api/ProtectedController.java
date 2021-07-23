package com.ynz.demo.securitymemory.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin")
@Slf4j
@RequiredArgsConstructor
public class ProtectedController {

    @GetMapping("data")
    String getProtectedData(Authentication authentication) {
        String userName = authentication.getName();
        List<String> auths = authentication.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList());

        log.info("login user: {}, with authorities {}", userName, auths);

        return "visible to admin alone";
    }

}
