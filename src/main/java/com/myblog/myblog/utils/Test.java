package com.myblog.myblog.utils;

import ch.qos.logback.core.CoreConstants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Test {
    public static void main(String[] args) {
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        System.out.println(encoder.encode("testing"));
    }
}
