Movie Ticket Booking System

A simple Java-based Movie Ticket Booking System that uses SQLite and JDBC to manage movies, theatres, shows, customers, seats, and ticket bookings through a console-based interface.

Features
Add and view movies
Manage theatres
Create and view movie shows
Manage customers
Manage seats
Book movie tickets
Cancel bookings
View existing bookings
Store all data using SQLite
Simple menu-driven console interface
Technologies Used
Java
JDBC
SQLite
SQL
IntelliJ IDEA
Project Structure
src/
├── Main.java
│
├── database/
│   └── Database.java
│
├── model/
│   ├── Booking.java
│   ├── Customer.java
│   ├── Movie.java
│   ├── Seat.java
│   ├── Show.java
│   └── Theatre.java
│
└── ui/
    └── ConsoleUI.java

lib/
└── sqlite-jdbc-3.53.4.0.jar
Architecture

The project uses a simple structure without unnecessary DAO or Service layers.

ConsoleUI
    |
    v
Database
    |
    v
SQLite Database
ConsoleUI handles user input and displays menus and results.
Database handles SQLite connections and SQL operations.
Model classes represent entities such as Movie, Customer, Show, Seat, and Booking.
Main starts the application.
Database

The application uses a local SQLite database:

movie_booking.db

The database stores information about:

Movies
Theatres
Shows
Customers
Seats
Bookings

The database is initialized automatically when the application starts.

How to Run
1. Clone the Repository
git clone <your-repository-url>
2. Open the Project

Open the project in IntelliJ IDEA.

3
