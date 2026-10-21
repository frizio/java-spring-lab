package com.example.java_spring_lab.model;

public record RuntimeInfo(
        String jvmVersion,
        String jvmVendor,
        String springVersion,
        String springBootVersion,
        String vendor) {
}
