TODO: Describe your app here. This information will appear in Git

Prerequisites
=============

* Java 1.8
* Maven 3.1.1+
* Lombok IDE plugin



Building
========

From IDE (Eclipse, Intellij)
----------------------------

Open as a Maven project and compile.

From Command Line
-----------------

    mvn clean package

Running the Application
======================

Run or debug the app with the ```Starter``` main class at the root of your Java package hierarchy

You must add the following VM options in order for logging to work properly:


Alternately, you may use either of the following Maven targets to run the application from either the command line or 
your IDE:

    mvn spring-boot:run

or:

    mvn exec:exec

Open a browser and visit [http://localhost:8080/](http://localhost:8080/) for Swagger documentation, or 
[http://localhost:8080/api/hello](http://localhost:8080/api/hello) for the sample API.


