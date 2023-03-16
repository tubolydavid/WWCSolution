# Getting Started

#### This application has an entrypoint where the spring application execution starts and a service which consumes wise's sandbox APIs, 

**Service has the following functionalities defined for your reference**
* Create a quote for your transfer
* Create your sender account
* Create a recipient account

**To achieve the objective of completing a transfer, you need to complete the following**

* Update the quote you created to have details of your recipient
* Create a transfer

## Some useful info to get you through this
- All the apis need authorisation, please get the accessToken and add it to your API
- In many steps you need input currency info, these are the valid currency codes you can use
  - GBP
  - USD
  - EUR
  - INR
  - SGD
  - JPY
- Account numbers, you can a random long number 
- This java application uses lombok library to avoid boilerplate code like getter, setters and constructor
  - For more information you can check this [Lombok](https://www.baeldung.com/intro-to-project-lombok)
- Many object have some default values defined to avoid getting everything from command line input, Make sure you change those appropriate to your input

### Additional Info
The following guides illustrate how to use some features concretely:

* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Everything you need to know about spring boot](https://www.baeldung.com/spring-tutorial)

### Additional Tips
Reach out your mentor and have fun coding!

