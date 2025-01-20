# Crafting API Contracts with Pact. 

This project serves as a demonstration of consumer contract testing using the Pact framework and acts as an example for the article [Crafting API Contracts with Pact](https://github.com/g-terra/pact-quest-demo/blob/main/article.md).

The demo illustrates how to create and consume API contracts with Pact, leveraging Java, Spring Boot, and Maven.


## Prerequisites

- Java 21 or higher
- Maven 3.6.0 or higher

## Project Structure

- `src/main/java`: Contains the main application code.
- `src/test/java`: Contains the test code.
- `src/test/java/dev/terralab/blog/examples/pactquestdemo/contract/consumer`: For sake of simplicity , the consumer contract tests are located in this package.This includes the consumer tests for the `QuestManagerClient` class.

## Building the Project

To build the project, run the following command:

```sh
make build
```

## Running the Tests

To run only the tests, use the following command:

```sh
make test
```

