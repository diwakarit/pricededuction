# Project Name
  pricededuction
   
# Overview

Read provided url and parse it and based on conditions created new json output.

### Local development prerequisite:

* Make sure you have Java Version 1.8
* Make sure you have Maven 3.5.0

### How to build?

```
mvn clean install
```

### How to build and run?

```
mvn clean install;java -Dapplication.environment=dev -Dapplication.home=. -Dspring.profiles.active=dev -jar ./target/*.war
```

## Service Endpoints

Open a browser and hit [http://localhost:8080/api/readjson](http://localhost:8080/)


