package com.reboot.exerc.controller.testController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reboot.exerc.service.test.service.TestService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class TestController {
    private final TestService testService;

    @GetMapping("")
    public String testMethod() {
        return String.valueOf(testService.findAllUser());
    }
}
