package com.example.java_spring_lab.model;

public record RuntimeInfo(
        String jvmVendor,
        String jvmVersion,
        String springVersion,
        String springBootVersion) {
}
