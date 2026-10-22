package com.example.java_spring_lab.service;

import org.springframework.core.SpringVersion;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Service;

import com.example.java_spring_lab.model.RuntimeInfo;

@Service
public class RuntimeInfoService {

    public RuntimeInfo getRuntimeInfo() {
        return RuntimeInfo.builder()
                .jvmVendor(System.getProperty("java.vm.vendor"))
                .jvmVersion(System.getProperty("java.vm.version"))
                .springVersion(SpringVersion.getVersion())
                .springBootVersion(SpringBootVersion.getVersion())
                .build();
    }
}
