#!/bin/bash

# Build project
cd ci
mvn clean package

# Run
cd target
java -jar ci-0.0.1-SNAPSHOT.jar