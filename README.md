Student Management System

Introduction:
This application is a microservices-based application that utilizes Feign Client and Eureka Server. 
The application consists of two microservices: student-service and book-service.
The student-service manages the entities student and laptop with CRUD operations,
while the book-service handles the entity book and invokes student information from the student-service using Feign Client.

Prerequisites:
Before running the application, ensure that you have the following dependencies installed:

Java Development Kit (JDK) version 11,
Maven build tool,
Eureka Server,
Feign Client.

Getting Started:
1-To Run the application follow the below steps:
2-Set up Eureka Server:

3-Configure and start the Eureka Server. Ensure it is running on the specified port.

4-Build and Run the both micro services
5-verify the micro services 
6-Open a web browser and navigate to http://localhost:8675 to access the student-service APIs.
7-Similarly, navigate to http://localhost:8676 to access the book-service APIs.
