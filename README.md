   
# Overview

To retrieve the Product List which has the valid price reductions in a category using multiple usecases
### Requirements:

* Oracle Java Runtime 1.8 or higher
* Maven 3.5.0
* Eclipse / IntelliJ

### To Build the application
You don’t need to build from source to use Spring Boot (binaries in repo.spring.io), but if you want to try out the latest and greatest, Spring Boot can be easily built with the maven wrapper. You also need JDK 1.8.

```
mvn clean install
```

### To Run the application
Run the below command to start the application once "Build Successful" message is displayed

```
mvn spring-boot:run
```

## Execution URLs:

    Generic URL: http://localhost:8080/priceReductionList?labelType={labelType}


    Specific:

      - http://localhost:8080/priceReductionList?labelType=ShowWasNow

      - http://localhost:8080/priceReductionList?labelType=ShowWasThenNow

      - http://localhost:8080/priceReductionList?labelType=ShowPercDscount

