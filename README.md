# spring-boot-insights

Zero-config API monitoring library for Spring Boot.

Add one dependency — get a live dashboard showing response times, query counts, and N+1 detection for every API in your app. No setup. No configuration.

---

## What it does

- Tracks response time for every API endpoint automatically
- Counts database queries fired per request
- Detects suspected N+1 query problems
- Shows everything on a live dashboard that auto-refreshes every 5 seconds
- Logs every API call with method, status code, duration, and timestamp

---

## Installation

Add this to your `pom.xml`:

```xml
<dependency>
    <groupId>com.springboot.insights</groupId>
    <artifactId>spring-boot-insights</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

That's it. No configuration needed.

---

## Usage

Run your Spring Boot app and hit your endpoints normally.

Then open:http://localhost:8080/dashboard.html 
You will see a live dashboard with all your API metrics.

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
- Every API call with endpoint, method, status code, duration, query count, and time

The dashboard auto-refreshes every 5 seconds. Use the Clear Logs button to reset and start fresh.

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
