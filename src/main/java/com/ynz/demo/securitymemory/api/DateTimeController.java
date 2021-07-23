package com.ynz.demo.securitymemory.api;

import com.ynz.demo.securitymemory.util.AccessingAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("api")
@Slf4j
@RequiredArgsConstructor
public class DateTimeController {
    private final AccessingAuthentication accessingAuthentication;

    @GetMapping("dateTime")
    public String getCurrentDateTime(Principal principal, Authentication authentication) {
        log.info("login user: {} is authenticated? {}", principal.getName(), authentication.isAuthenticated());

        return OffsetDateTime.now().toString();
    }

    @GetMapping("date")
    public String getCurrentDate() {
        log.info("login user: {}", accessingAuthentication.getAuthentication().getName());
        return OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE);
    }

}
