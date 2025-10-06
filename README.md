# Overview

As a software engineer focused on expanding my programming language expertise, I developed this Kotlin Task Manager to deepen my understanding of modern JVM languages and their unique features. This project demonstrates my commitment to learning new technologies and applying them to solve real-world problems.

This software is a comprehensive console-based task management application built entirely in Kotlin. It provides users with a complete task management solution including adding, removing, completing, searching, and organizing tasks with priority levels. The application showcases advanced Kotlin language features including data classes, when expressions, collections manipulation, and object-oriented programming principles.

The purpose of this project was to master Kotlin's modern language features while building a practical application that demonstrates both basic and advanced programming concepts. Through this implementation, I gained hands-on experience with Kotlin's concise syntax, null safety, extension functions, and functional programming capabilities that make it a powerful alternative to Java for JVM development.

[Software Demo Video - https://youtu.be/QmwiUW3ovVw](https://youtu.be/QmwiUW3ovVw)

# Development Environment

I developed this software using IntelliJ IDEA Community Edition, which provides excellent Kotlin support with syntax highlighting, code completion, and debugging capabilities. The project was built using the Kotlin JVM target with Java 11 compatibility.

**Programming Language:** Kotlin 1.9.0
**Libraries Used:** 
- Java Time API (java.time.LocalDateTime) for timestamp functionality
- Standard Kotlin collections (MutableList, List)
- Kotlin standard library functions

**Development Tools:**
- IntelliJ IDEA Community Edition 2023.3
- Kotlin compiler with JVM target
- Built-in terminal for console execution

# How to Run the Project

## Prerequisites

Before running the project, ensure you have the following installed:

1. **Java Development Kit (JDK) 11 or higher**
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - Verify installation: `java -version`

2. **Kotlin Compiler**
   - Install via Homebrew: `brew install kotlin`
   - Or download from [Kotlin Official Website](https://kotlinlang.org/docs/command-line.html)
   - Verify installation: `kotlin -version`

## Running in IDE (IntelliJ IDEA)

### Method 1: Direct Execution
1. Open IntelliJ IDEA Community Edition
2. Open the project folder: `File > Open > Select the project folder`
3. Open `TaskManager.kt` file
4. Right-click on the `main()` function
5. Select `Run 'TaskManagerKt'`
6. The application will start in the built-in terminal

### Method 2: Using Run Configuration
1. Open the project in IntelliJ IDEA
2. Go to `Run > Edit Configurations`
3. Click `+` and select `Kotlin`
4. Set the following:
   - **Name:** Task Manager
   - **Main class:** TaskManagerKt
   - **Module:** Select your project module
5. Click `OK` and then `Run > Run 'Task Manager'`

## Running from Command Line

### Method 1: Compile and Run JAR
```bash
# Navigate to project directory

# Compile the Kotlin file with runtime included
kotlinc TaskManager.kt -include-runtime -d TaskManager.jar

# Run the application
java -jar TaskManager.jar
```

### Method 2: Direct Kotlin Execution
```bash
# Navigate to project directory

# Compile to class files
kotlinc TaskManager.kt -d .

# Run with Kotlin runtime
kotlin TaskManagerKt
```

## Packaging for Deployment

### Creating a Standalone JAR

1. **Compile with runtime included (recommended for distribution):**
   ```bash
   kotlinc TaskManager.kt -include-runtime -d TaskManager.jar
   ```

2. **Verify the JAR file:**
   ```bash
   java -jar TaskManager.jar
   ```

3. **Check JAR contents (optional):**
   ```bash
   jar -tf TaskManager.jar
   ```

### Creating a Lightweight JAR (requires Kotlin runtime)

1. **Compile without runtime:**
   ```bash
   kotlinc TaskManager.kt -d TaskManager-light.jar
   ```

2. **Run with Kotlin runtime:**
   ```bash
   kotlin -cp TaskManager-light.jar TaskManagerKt
   ```

### Deployment Options

#### Option 1: Standalone JAR (Recommended)
- **Pros:** Self-contained, runs on any machine with Java
- **Cons:** Larger file size (~15MB)
- **Use case:** Distribution to end users

#### Option 2: Lightweight JAR + Kotlin Runtime
- **Pros:** Smaller file size (~50KB)
- **Cons:** Requires Kotlin runtime on target machine
- **Use case:** Development environments with Kotlin installed

#### Option 3: Executable Script
Create a shell script for easy execution:

**run.sh (Linux/macOS):**
```bash
#!/bin/bash
cd "$(dirname "$0")"
java -jar TaskManager.jar
```

**run.bat (Windows):**
```batch
@echo off
cd /d "%~dp0"
java -jar TaskManager.jar
```

### Cross-Platform Deployment

1. **For Windows:**
   - Ensure target machine has Java 11+ installed
   - Distribute the JAR file
   - Create a batch file for easy execution

2. **For macOS:**
   - Same as Windows, but use shell script instead of batch file

3. **For Linux:**
   - Same as Windows, but use shell script

### Docker Deployment (Advanced)

Create a `Dockerfile` for containerized deployment:

```dockerfile
FROM openjdk:11-jre-slim
COPY TaskManager.jar /app/TaskManager.jar
WORKDIR /app
CMD ["java", "-jar", "TaskManager.jar"]
```

Build and run:
```bash
docker build -t task-manager .
docker run -it task-manager
```

# Useful Websites

- [Kotlin Official Documentation](https://kotlinlang.org/docs/)
- [Kotlin Data Classes Guide](https://kotlinlang.org/docs/data-classes.html)
- [Kotlin When Expressions](https://kotlinlang.org/docs/control-flow.html#when-expression)
- [Kotlin Collections Overview](https://kotlinlang.org/docs/collections-overview.html)
- [Kotlin Extension Functions](https://kotlinlang.org/docs/extensions.html)

# Future Work

- Add task persistence using file I/O or database integration
- Implement task categories and tags for better organization
- Add task due dates and reminder functionality
- Create a graphical user interface using Kotlin Multiplatform
- Add task export/import functionality (JSON/CSV formats)
- Implement user authentication and multi-user support
- Add task templates and recurring task functionality
- Create unit tests using Kotlin testing frameworks
