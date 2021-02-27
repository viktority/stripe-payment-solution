# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Quick summary: Demo of a stripe-powered payment solution.
* Version: 1.0-SNAPSHOT
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

Summary of set up: 

- Clone Repository with "git clone https://github.com/viktority/stripe-payment-solution.git"

- Navigate to application.properties file and replace STRIPE_PUBLIC_KEY ,STRIPE_SECRET_KEY and destination_account with live keys.

- Build and run application with 'mvn clean install'

- Goto target folder of application and run the application with 'java -jar payment-solution-1.0-SNAPSHOT.war'

- After app starts up, go to http://localhost:8080

- Click on any item to purchase and input card details.

- Also, the service provides a means to register an account to be able to receive money with http://localhost:8080/account/signup


### Contribution guidelines ###

* Project Roadmap and documents are on Jira and confluence respectively.
- Requirements can be found here: https://dexteriovic.medium.com/test-demo-working-with-stripe-api-c1447cca8a06

### Who do I talk to? ###

* chukwukaonyekachukwu@gmail.com
- viktority@yahoo.com
