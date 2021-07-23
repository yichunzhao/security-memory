package com.ynz.demo.securitymemory.util;

import org.springframework.security.core.Authentication;

public interface AccessingAuthentication {

    Authentication getAuthentication();
}
