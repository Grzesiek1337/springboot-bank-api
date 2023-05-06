# Bank API
This is a simple Spring Boot application that allows users to create bank accounts and transfer money between them. The application uses the following technologies:
* Java 18
* Spring Boot 2.6.2
* Spring Boot Starter Data JPA
* Spring Boot Starter Security
* Spring Boot Starter Validation
* Spring Boot Starter Web
* MySQL Connector/J
* Thymeleaf Extras Spring Security 5
* ModelMapper
# Installation:
* Clone the repository: git clone https://github.com/your-username/bank-api.git
* Import the project into your preferred IDE (e.g. IntelliJ IDEA or Eclipse)
* Configure your MySQL database connection in the application.properties file
* Run the application
# Content:
You can access the application at http://localhost:8080/. 
* As an unregistered user, you can browse through the bank's offers and services. You can contact the bank through the available contact tab and u can register new bank account. You can simulate loan payment schedule.
* As a registered user,at this moment you can make money transfers between your accounts as well as to other accounts within the bank. You can applicate for loan. You can receive notifications.
# Admin features
If you have admin privileges, you can perform the following actions:
* View a list of all registered users.
* Delete a user and all their associated client information and bank account.
* View a list of contacts that have not yet received a response.
* Respond to a contact request.
* View a list of contacts that have already received a response.
* Send notification to all users.
* Coming soon...
# Security:
I use BCryptEncoder to hash passwords for added security. This ensures that all passwords are encrypted and not stored in plain text.


