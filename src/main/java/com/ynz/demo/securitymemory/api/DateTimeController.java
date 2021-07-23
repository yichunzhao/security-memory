package com.ynz.demo.securitymemory.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("api")
@Slf4j
public class DateTimeController {

    @GetMapping("dateTime")
    String getCurrentDateTime(Principal principal, Authentication authentication) {
        log.info("login user: {} is authenticated? {}", principal.getName(), authentication.isAuthenticated());

        return OffsetDateTime.now().toString();
    }

}
