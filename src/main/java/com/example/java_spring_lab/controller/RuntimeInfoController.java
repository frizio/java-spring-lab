package com.example.java_spring_lab.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/runtime-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RuntimeInfo> getRuntimeInfo() {
        return ResponseEntity.ok(runtimeInfoService.getRuntimeInfo());
    }
}
