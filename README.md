# ParaBank Automation Framework
💻 Built a Selenium WebDriver + TestNG automation framework for ParaBank, a demo online banking site. The framework implements Page Object Model, data-driven testing, and reporting, showcasing enterprise-level automation design.

---

## Contents

* [Features](#features)
* [Tech Stack](#tech-stack)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Configuration](#configuration)
* [How to Run Tests](#how-to-run-tests)
* [Reports & Screenshots](#reports--screenshots)
* [Parallel Execution & CI](#parallel-execution--ci)
* [Best Practices & Tips](#best-practices--tips)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)

---

## Features

* Page Object Model (POM) for maintainable page classes.
* Data-driven tests using TestNG `@DataProvider` and external data sources (CSV/Excel/JSON — pluggable).
* Thread-safe WebDriver management via `DriverManager` for parallel execution.
* ExtentReports-based HTML reporting with screenshot capture on failures.
* Suite and test listeners (`ISuiteListener`, `ITestListener`) for centralized reporting and lifecycle handling.
* Utilities for screenshots, waits, and common test helpers.
* Configurable via properties or environment variables for different environments (local/qa/stage).

---

## Tech Stack

* Java (11+ recommended)
* Selenium WebDriver
* TestNG
* ExtentReports (AventStack)
* Log4j2
* Maven (build & dependency management)

---

## Prerequisites

* Java JDK 11 or newer installed and `JAVA_HOME` configured.
* Maven 3.6+ installed.
* Chrome / Firefox browsers installed (or driver/docker grid available).
* Recommended IDE: IntelliJ IDEA or Eclipse.

---

## Installation

1. Clone the repository:

```bash
git clone <repository-url>
cd <repo-folder>
```

2. Build the project and download dependencies:

```bash
mvn clean compile
```

---

## Configuration

The framework uses a properties file for environment-specific config. Example files are under `resources/config/`.

Key configuration options:

* `base.url` — ParaBank base URL
* `browser` — `chrome` / `firefox` / `edge`
* `headless` — true/false (for CI)

You can override properties at runtime using Maven system properties, e.g.:

```bash
mvn test -Dbrowser=chrome -Dbase.url=https://parabank.parasoft.com
```

---

## How to Run Tests

### Run the full TestNG suite

If your project includes a TestNG suite file (e.g. `testng.xml`):

```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Run a single test class

```bash
mvn -Dtest=LoginTests test
```

### Run a specific test method

```bash
mvn -Dtest=LoginTests#shouldLoginSuccessfully test
```

### Pass configuration at runtime

```bash
mvn test -Dbrowser=chrome -Denv=qa
```

---

## Reports & Screenshots

* **Extent HTML report** is generated under: `target/ExtentReport.html` depending on your `ExtentReport` configuration.
* **Screenshots** are saved under: `target/screenshots/` with timestamped filenames.

### Common issues & solutions

* If screenshots do not appear in the report, please ensure that the screenshot method returns an **absolute path** and that the file exists at that path. Use the `MediaEntityBuilder.createScreenCaptureFromPath(absolutePath)` when attaching.
* If running tests multiple times and you want a clean report each run, delete the old report file in `onStart(ISuite suite)` or archive it to `target/reports/archive` before initialising Extent.

---

## Parallel Execution & CI

### Thread-safe driver

`DriverManager` holds a `ThreadLocal<WebDriver>` so each test thread gets its own WebDriver instance. Ensure that tests do not use shared mutable state.

### Running in parallel (TestNG)

Configure `testng.xml` or use Maven Surefire to enable parallel execution:

```xml
<suite name="Regression" parallel="tests" thread-count="4">
  ...
</suite>
```

### CI Integration (Jenkins/GitLab CI/GitHub Actions)

* Build with Maven: `mvn clean test`
* Archive artifacts: `target/reports/*`, `target/screenshots/*`
* Use headless mode for browsers in CI (set `-Dheadless=true`).

---

## Best Practices & Tips

* Keep page interactions simple and return either a `PageObject` or a value — do not mix assertions inside page methods.
* Use `@BeforeMethod` / `@AfterMethod` in test classes for per-test setup and teardown; keep expensive operations (like DB connections) at the suite level if needed.
* Avoid hard sleeps; use explicit waits (WebDriverWait / ExpectedConditions).
* Make screenshot capture robust: return absolute path, timestamp file names, and handle `driver == null` gracefully.

---

## Contributing

1. Fork the repo
2. Create a feature branch
3. Add tests & documentation
4. Open a pull request describing your changes

Please follow coding standards and keep methods small.

---

*Happy testing!* 🚀

