package com.exporters.cpuexporter;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CpuExporterConfig {
    public CpuExporterConfig(MeterRegistry meterRegistry) {
        OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        Gauge.builder("cpu_usage", () -> {
            double cpuUsage = os.getCpuLoad();
            return cpuUsage * 100;
        }).description("CPU usage").register(meterRegistry);
    }
}
