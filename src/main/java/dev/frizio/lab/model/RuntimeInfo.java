package dev.frizio.lab.model;

import lombok.Builder;

@Builder
public record RuntimeInfo(
        String jvmVendor,
        String jvmVersion,
        String springVersion,
        String springBootVersion) {
}
