package com.example.java_spring_lab.service;

import org.springframework.core.SpringVersion;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Service;

import com.example.java_spring_lab.model.RuntimeInfo;

@Service
public class RuntimeInfoService {

    public RuntimeInfo getRuntimeInfo() {
        return new RuntimeInfo(
                System.getProperty("java.vm.vendor"),
                System.getProperty("java.vm.version"),
                SpringVersion.getVersion(),
                SpringBootVersion.getVersion());
    }
}
