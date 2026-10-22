package dev.frizio.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.JavaVersion;
import org.springframework.core.SpringVersion;

@SuppressWarnings("unused")
@SpringBootApplication
public class JavaSpringLabApplication {

    private static final Logger log = LoggerFactory.getLogger(JavaSpringLabApplication.class);

    private static final String PREFIX = "startup ";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SpringApplication.run(JavaSpringLabApplication.class, args);
        long end = System.currentTimeMillis();
        startupLog();
        log.info("{} Application started in {} ms", PREFIX, (end - start));
    }

    private static void startupLog() {
        log.info("\n");
        // memoryLogs();
        javaExecutionLogs();
        //springLogs();
        // envVarsLogs();
        // envLog();
    }

    private static void javaExecutionLogs() {
        log.info("{} ====================== JAVA Execution Info ======================", PREFIX);
        log.info("{} java.vendor: {}", PREFIX, System.getProperty("java.vendor"));
        log.info("{} java.vm.version: {}", PREFIX, System.getProperty("java.vm.version"));
        log.info("{} java.home: {}", PREFIX, System.getProperty("java.home"));
        log.info("{} =================================================================\n", PREFIX);
    }

    private static void springLogs() {
        log.info("{} ===================== Java & Spring Info ====================", PREFIX);
        log.info("{} Vendor: {}, Runtime: {}", PREFIX, System.getProperty("java.vendor"),
                System.getProperty("java.vm.version"));
        log.info("{} Java Version: {}", PREFIX, JavaVersion.getJavaVersion());
        log.info("{} Spring Version: {}", PREFIX, SpringVersion.getVersion());
        log.info("{} Spring Boot Version: {}", PREFIX, SpringBootVersion.getVersion());
        log.info("{} ================================================================\n", PREFIX);
    }

}
