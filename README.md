# spring-boot-insights

[![Maven Central](https://img.shields.io/maven-central/v/io.github.nishithsai00/spring-boot-insights)](https://central.sonatype.com/artifact/io.github.nishithsai00/spring-boot-insights)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Zero-config API monitoring library for Spring Boot.

Add one dependency — get a live dashboard showing response times, query counts, and N+1 detection for every API in your app. No setup. No configuration.

---

## What it does

- Tracks response time for every API endpoint automatically
- Counts database queries fired per request via Hibernate `StatementInspector`
- Detects suspected N+1 query problems (configurable threshold)
- Shows everything on a live dashboard that auto-refreshes every 5 seconds
- Logs every API call with method, status code, duration, query count, and timestamp

---

## Installation

Add this to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.nishithsai00</groupId>
    <artifactId>spring-boot-insights</artifactId>
    <version>1.0.1</version>
</dependency>
```

That's it. No configuration needed.

---

## Usage

Run your Spring Boot app and hit your endpoints normally.

Then open:

```
http://localhost:8080/dashboard.html
```

You will see a live dashboard with all your API metrics.

---

## Configuration

By default, any endpoint firing more than **10 queries per request** is flagged as a suspected N+1.

To change this threshold, add this line in your main application class:

```java
@SpringBootApplication
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
        RunTimeInterceptor.setSuspectedQueryCountForN1(15); // change threshold to 15
    }
}
```

---

## Dashboard

The dashboard shows two sections:

**Summary Table**
- Endpoint name
- Total calls
- Average response time
- Slowest response time
- Total queries fired
- N+1 suspected flag

**Recent API Logs**
- Every API call with endpoint, method, status code, duration, query count, and timestamp

The dashboard auto-refreshes every 5 seconds. Use the **Clear Logs** button to reset.

---

## Endpoints

| Endpoint | Description |
|---|---|
| `/dashboard.html` | Live monitoring dashboard |
| `/insights` | Raw JSON list of all API logs |
| `/insights/summary` | Grouped summary stats per endpoint |
| `/insights/clear` | Clears all stored logs (DELETE) |

---

## Requirements

- Java 17+
- Spring Boot 3.x
- Spring Web dependency in your project

---

## Built by

Nishith Sai — [LinkedIn](https://linkedin.com/in/nishith-sai-62a60b378) · [GitHub](https://github.com/nishithsai00)
