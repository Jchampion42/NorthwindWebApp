# Northwind Web App

## Contents

- [Project Description And Requirements](#project-description-and-requirements)
- [Project Management](#project-management)
- [Git Workflow](#git-workflow)
- [Technology](#technology)
- [Program Setup And Usage](#program-setup-and-usage)
- [Design](#design)
- [Testing](#testing)
- [Contributors](#contributors)

## Project Description And Requirements

In this project, a web application based upon the Northwind SQL Database was created using Java alognside various technologies such as Spring and JPA (Java Persistence API) and Hibernate. The web app was required to allow a user to log in using a set of credentials, view records from various tables in the Northwind database, update records, delete records and create new records. There are differing user privilages, where a user with admin privilages has access to all the tables and all their operations, whereas a standard user can only see the products in the shop section.

In regard to the shop section, products from the product table are displayed with their names and prices, along with a button to add a product to a basket. In the basket, a user can remove items or checkout. 


*This project is a part of our training at Sparta Global*




## Project Management

The following tools were used to manage project: 

- **Github** *to work on seperate sections of the project at the same time and version control*
- **Github Projects** *to manage and alocate tasks/user stories to team members and keep track of work done or work yet to be done*
- **Code With Me (IntelliJ Plugin)** *to carry out pair programming on tasks that were allocated to multiple people*
- **Microsoft Teams** *for frequent team communication*
- **Discord** *for team communication in situations were team members needed to be in seperate calls*

### Git Workflow

At the start of the project a "development" branch was created based of "master". From the development branch, multiple branches were created from it where seperate sections of the project could be worked on such as the controllers for each table. These branches are named after the section the project that was being worked.

This approach made it easy for team members to not step on each others toes, and there were minimal merge conflicts for the most part. This meant more time could be dedicated to work on the project instead of fixing git related issues.

When features were completed, these were pushed to the development branch while the master branch is preserved.

## Technology

- IntelliJ IDEA [2022.2]
- JDK 18.0.1
- Java 18
- MySQL
- Apache Maven
- Spring (Initializr)
- JPA Buddy
- JUnit Jupiter 

## Program Setup And Usage 

### Cloning The Repository

- The repository can be cloned using the command:

```shell
    git clone https://github.com/OTDZ/northwind-web-app.git
```

Alternatively a zip file of the project can be downloaded from Github

### Application Properties 

An ```application.properties``` file needs to be created in ```src/main/resources``` with the following content 

```shell

    spring.datasource.url=jdbc:mysql://localhost:3306/northwind
    spring.datasource.username=yourUsername
    spring.datasource.password=yourPassword
    spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    spring.jackson.serialization.FAIL_ON_EMPTY_BEANS=false
    
 ```
 
 *The Northwind database with MySQL is required here*
 
 ### Compiling 
 
 The application can be compiled with 
 
 ```shell
 
    javac src/main/java/com/example/northwindwebapp/NorthwindWebApplication.java
 
 ```
### Running 

Run the application with 

```shell

   java NorthwindWebApplication
```
Then type the url ```http://localhost:8080/home``` into your browser.

## Design
### Northwind Database
![image](/images/northwind.jpg)

We implemented CRUD operations for the following tables:
- Customers
- Orders
- Order Details
- Products
- Shippers

### Roles and Access Levels
We have 2 defined roles which are assigned by logging in:
- Admin
  - Can access all sections of the application
- User
  - Can access the shop, add items to basket and checkout

We also have an undefined "Guest" role which is for users who have not logged in, a Guest is able to browse the shop but can't add items to basket or checkout.

### __Login Details__
- __Admin__
  - Username: Michael
  - Password: password1
- __User__
  - Username: Marc
  - Password: password1
  - Username: Omar
  - Password: password1
  - Username: Jeffrey
  - Password: password1
  - Username: Ray
  - Password: password1
  - Username: Mustafa
  - Password: password1

### User Interface
All of our pages have a navigation bar at the top, allowing the user to easily and intuitively move around the application.

![image](/images/shop.JPG)

The Shop page displays all the items in the Product table with their name, price and quantity per unit, each product has a clear button which allows the user to add the product to their basket.

![image](/images/basketPreview.JPG)

Upon adding items to the basket, a basket preview is displayed at the top which shows the products that are currently in the basket and their quantities.

![image](/images/basket.JPG)

The Basket page presents the products in the basket in more detail and allows the user to remove products from the basket. When the user is happy with their basket they can press the Checkout button.

![image](/images/admin.JPG)

The admin has access to all areas of the application, as you can see in the navigation bar. The Products page displays information about all products and provides the options to update and delete individual products. The pages for Orders, Order Details, Shippers and Customers follow the same structure.

## Testing

We performed manual testing to validate our functionality.

![image](/images/update4.JPG)

For example, to test that update successfully works for Products we select the update option for the first product in the table.

![image](/images/update.JPG)
![image](/images/update2.JPG)

In this example we will modify the "Unit price" field by increasing the value to 25.

![image](/images/update3.JPG)

We can see on the Products page that the "Unit price" field has been updated and can therefore say the update for Products is functional.

We followed this procedure for all of the operations that can be performed on all of our entities. We tested the shop and basket functionality in the same way. 

## Contributors

- [Mustafa Nawaz](https://github.com/Typist01)
- [Michael Matson](https://github.com/M-Matson)
- [Jeff Champion](https://github.com/Jchampion42)
- [Marc Murray](https://github.com/Erratika)
- [Ray Azam](https://github.com/abdurshazam)
- [Omar Tehami](https://github.com/OTDZ)
