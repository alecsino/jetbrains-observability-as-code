# Task – Grafana Dashboard for `cpu_usage` Metric

## Overview

This repository implements **Task #2** from the assignment:

> **Task:** Prepare a Grafana dashboard for the `cpu_usage` metric  
> **Base repository:** [grafana/demo-prometheus-and-grafana-alerts](https://github.com/grafana/demo-prometheus-and-grafana-alerts)

Instead of using the mock metrics generated via `k6` scripts from the original demo, I created a **real Java-based Prometheus exporter** that exposes an actual CPU usage metric (`cpu_usage`) via Spring Boot and Micrometer.  
Grafana visualizes this metric using a custom dashboard provided as a JSON definition.

---

## Project structure

```
.
├── docker-compose.yml
├── exporters/
│ └── cpu-exporter/ # Spring Boot application
│    └── Dockerfile
|── grafana/
|  └── dashboards/
|    └── dashboard.yaml # Dashboard provisioning config
|  └── definitions/
|    └── cpu-usage.json # The dashboard JSON definition
├── prometheus/
│ └── prometheus.yml # Added scrape config for cpu-exporter
| ... other files ...
```

---

## Components

### **CPU Exporter (Spring Boot / Java 17)**

A simple Spring Boot application that:
- Uses **Micrometer** + **Prometheus registry** to expose metrics at  
  **`/actuator/prometheus`**
- Runs inside Docker and is exposed on port 9900

#### Note:
Because this application runs in a Docker container, it reports container-level CPU usage, not the host machine’s full CPU utilization.
In real-world setups, Node Exporter or similars would be used for system-level CPU metrics instead of a Java app - I wanted to go with this approach to dust off my Java.

### **Grafana Dashboard**

A grafana dashboard visualizing the `cpu_usage` metric, automatically provisioned via the `dashboard.yaml` file.
- Dashboard JSON definition: `grafana/dashboards/definitions/cpu-usage.json`

### **Prometheus Configuration**

The Prometheus configuration file (`prometheus/prometheus.yml`) has been updated to include a scrape job for the `cpu-exporter` service.

## Running the Setup

1. Start the Docker containers:
   ```bash
   docker-compose up -d
   ```

2. Access the CPU Exporter:
   - Open your browser and go to `http://localhost:9900/actuator/prometheus` to see the exposed metrics.

3. Access Grafana:
   - Open your browser and go to `http://localhost:3000`
   - Login with the default credentials (`admin`/`admin`), and you should see the provisioned dashboard for `cpu_usage`.