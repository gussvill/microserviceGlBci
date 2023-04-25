package com.globallogic.microserviceglbci.controller;

import com.globallogic.microserviceglbci.service.MockUserGenerateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mockuser")
public class MockUserController {
    private final MockUserGenerateService dummyUserGenerateService;

    public MockUserController(MockUserGenerateService dummyAccountGenerateService) {
        this.dummyUserGenerateService = dummyAccountGenerateService;
    }

    @GetMapping("/generatedummyusers")
    public void generateDummyUsers() {
        dummyUserGenerateService.generateUsers();
    }

}