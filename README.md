# Merchant Service Through the Lens of Hexagonal Architecture
This is a Merchant Service rewrite following Hexagonal Architecture. The goal is to have the core application isolated from the input ports, as the architecture preaches.

## Project Bare Bones
This project can be reconstructed using the following command:
```
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.2.2&packaging=jar&jvmVersion=21&groupId=com.wilterson.cms&artifactId=cms&name=Merchant%20Service&description=Merchant%20Service&packageName=com.wilterson.cms&dependencies=lombok,devtools,web,data-jpa,h2,validation
```

## TODO: Improvements
1. Library (GitHub) to work with Records (builder, etc)
2. The semantic validation logic should be an extended use case. Check how it can be implemented as such so that it can be extended without violating the SOLID principles (mainly the OCP) 

## TODO: Requirements

1. Where and how will the merchant life-cycle be implemented? Maybe there should be an extended use case that gets called by the `CreateMerchantService` and `UpdatedMerchantService` (future) to decide 

2. The validations shouldn't just throw exceptions, but instead compile all the violations into a list
    - Each semantic exception must have an exception
    - The domain service objects (use case implementations) will throw the exceptions
    - The ApplicationService must catch the exception and categorize them into WARNING, SKIP, or ERROR.

3. Internationalization of the error messages
