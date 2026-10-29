package dev.frizio.lab.service;

import org.springframework.core.SpringVersion;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Service;

import dev.frizio.lab.dto.RuntimeInfo;

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
