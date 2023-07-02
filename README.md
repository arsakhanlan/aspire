
# Aspire Mini

The Aspire Mini Project is a simplified loan application system built with Spring Boot. It allows customers to apply for loans, view their loan details, and make loan repayments. The application assumes a weekly repayment frequency for loans.

## Features

* Customer can create a loan by submitting the loan amount and term.
* Admin can approve or reject loan applications.
* Customers can view their own loans.
* Customers can make repayments towards their scheduled loan repayments.
* Loans and repayments have different status states (e.g., PENDING, APPROVED, PAID).


## Tech Stack

* Java 8
* Spring Boot
* Hibernate ORM
* H2 Database
* JUnit 5 and Mockito (for testing)
* Maven (for build and dependency management)

# Getting Started
## Prerequisites
* Java 8 or higher
* Maven
## Installation
1. Clone the repository: git clone https://github.com/your-username/aspire-mini-project.git
2. Navigate to the project directory: cd aspire-mini-project
3. Build the project: mvn clean install
4. Run the application: mvn spring-boot:run
5. The application will start running on http://localhost:8080.

