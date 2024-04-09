package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {


        @GetMapping("/")
        public String homePage() {
            return "index";
        }

        @GetMapping("/user")
        public String userPage() {
            return "user";
        }

        @GetMapping("/admin")
        public String adminPage() {
            return "admin";
        }

        @GetMapping("/login")
        public String loginPage() {
            return "login";
        }
    }



