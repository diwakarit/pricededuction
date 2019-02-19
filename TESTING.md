Overview
=========

This template comes with Junit for testing. 

Sample tests are located in the /src/test/java folder.

Testing the Application
======================

To test the app in dev environment, execute:

    mvn clean test


Unit Test Coverage
==================
Jacoco is provided to help gauge unit test coverage.

To create the Jacoco report, run the unit tests and then run:

    mvn jacoco:report
    
The report will be under the /target/site directory.
