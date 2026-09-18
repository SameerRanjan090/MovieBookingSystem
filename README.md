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

3. Add SQLite JDBC

Make sure the SQLite JDBC JAR is added to the project libraries:

lib/sqlite-jdbc-3.53.4.0.jar

In IntelliJ IDEA:

File → Project Structure → Libraries → + → Java

Select the SQLite JAR and apply the changes.

4. Run the Application

Run:

src/Main.java

The application will display the main menu:

=== MOVIE TICKET BOOKING ===

1. Movies
2. Theatres
3. Shows
4. Customers
5. Seats
6. Book Ticket
7. Cancel Booking
8. View Bookings
9. Exit
Purpose

This project demonstrates the practical use of Java, Object-Oriented Programming, JDBC, SQL, and database management by building a simple real-world movie ticket booking application.

Author

Sameer Ranjan

License

This project is available for educational and learning purposes.
