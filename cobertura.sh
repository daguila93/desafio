#!/usr/bin/env bash
mvn test jacoco:report
cd target/site/jacoco/
python -mwebbrowser index.html
