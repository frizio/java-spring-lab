package com.example.java_spring_lab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_spring_lab.model.RuntimeInfo;
import com.example.java_spring_lab.service.RuntimeInfoService;

@RestController
public class RuntimeInfoController {

    private final RuntimeInfoService runtimeInfoService;

    public RuntimeInfoController(RuntimeInfoService runtimeInfoService) {
        this.runtimeInfoService = runtimeInfoService;
    }

    @GetMapping("/runtime-info")
    public RuntimeInfo getRuntimeInfo() {
        return runtimeInfoService.getRuntimeInfo();
    }
}
