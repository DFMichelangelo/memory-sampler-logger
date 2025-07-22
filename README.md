# Memory Sampler

Java agent for tracking memory allocations with stack traces.

## Quick Start

```bash
./gradlew runWithAgent
./gradlew runExample
```

## API

```java
MemorySampler.start();        // Begin tracking
// your allocation code here
MemorySampler.end();          // Stop tracking  
MemorySampler.printSituation(); // Print report
```

## Features

- Real-time allocation tracking
- Stack trace capture (5 levels)
- Thread-safe concurrent tracking
- Accurate object size measurement

## Build

```bash
./gradlew build
./gradlew agentJar
```