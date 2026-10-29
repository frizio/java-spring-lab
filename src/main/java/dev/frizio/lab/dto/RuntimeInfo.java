package dev.frizio.lab.dto;

import lombok.Builder;

@Builder
public record RuntimeInfo(
        String jvmVendor,
        String jvmVersion,
        String springVersion,
        String springBootVersion) {
}
