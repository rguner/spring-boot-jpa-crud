package com.example.jpacrud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

@Service
public class MemoryMonitoringService {
    private static final Logger logger = LoggerFactory.getLogger(MemoryMonitoringService.class);
    private final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

    public void logMemoryUsage(String operation) {
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();

        logger.info("Memory Usage {}: Heap: {} MB / {} MB, Non-Heap: {} MB / {} MB",
            operation,
            heapUsage.getUsed() / 1024 / 1024,
            heapUsage.getMax() / 1024 / 1024,
            nonHeapUsage.getUsed() / 1024 / 1024,
            nonHeapUsage.getMax() / 1024 / 1024);
    }

    public MemoryInfo getMemoryInfo() {
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();

        return new MemoryInfo(
            heapUsage.getUsed(),
            heapUsage.getMax(),
            nonHeapUsage.getUsed(),
            nonHeapUsage.getMax()
        );
    }

    public static class MemoryInfo {
        private final long heapUsed;
        private final long heapMax;
        private final long nonHeapUsed;
        private final long nonHeapMax;

        public MemoryInfo(long heapUsed, long heapMax, long nonHeapUsed, long nonHeapMax) {
            this.heapUsed = heapUsed;
            this.heapMax = heapMax;
            this.nonHeapUsed = nonHeapUsed;
            this.nonHeapMax = nonHeapMax;
        }

        // Getters
        public long getHeapUsed() { return heapUsed; }
        public long getHeapMax() { return heapMax; }
        public long getNonHeapUsed() { return nonHeapUsed; }
        public long getNonHeapMax() { return nonHeapMax; }
        public double getHeapUsedMB() { return heapUsed / 1024.0 / 1024.0; }
        public double getHeapMaxMB() { return heapMax / 1024.0 / 1024.0; }
        public double getNonHeapUsedMB() { return nonHeapUsed / 1024.0 / 1024.0; }
        public double getNonHeapMaxMB() { return nonHeapMax / 1024.0 / 1024.0; }
        public double getHeapUsagePercentage() { return (heapUsed * 100.0) / heapMax; }
    }
}
