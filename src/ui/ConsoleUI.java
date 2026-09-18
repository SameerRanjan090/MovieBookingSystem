package ui;

import database.Database;
import model.Booking;
import model.Customer;
import model.Movie;
import model.Seat;
import model.Show;
import model.Theatre;

import java.util.List;
import java.util.Scanner;

/**
 * Provides the menu-driven console interface for the application.
 *
 * The UI is kept separate from the database class so that the user can
 * interact with the program without having to see SQL queries.
 */
public class ConsoleUI {

    private final Scanner scanner;

    public ConsoleUI() {
        scanner = new Scanner(System.in);
    }

    /**
     * Starts the main menu of the application.
     */
    public void start() {
        boolean running = true;

        while (running) {
            printMainMenu();

            int choice = readInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    movieMenu();
                    break;
                case 2:
                    theatreMenu();
                    break;
                case 3:
                    showMenu();
                    break;
                case 4:
                    customerMenu();
                    break;
                case 5:
                    displaySeats();
                    break;
                case 6:
                    bookTicket();
                    break;
                case 7:
                    cancelBooking();
                    break;
                case 8:
                    displayBookings();
                    break;
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       MOVIE TICKET BOOKING SYSTEM      ");
        System.out.println("========================================");
        System.out.println("1. Manage Movies");
        System.out.println("2. Manage Theatres");
        System.out.println("3. Manage Shows");
        System.out.println("4. Manage Customers");
        System.out.println("5. View Seats");
        System.out.println("6. Book Ticket");
        System.out.println("7. Cancel Booking");
        System.out.println("8. View Bookings");
        System.out.println("9. Exit");
        System.out.println("----------------------------------------");
    }

    // ================================================================
    // MOVIE MENU
    // ================================================================

    private void movieMenu() {
        boolean goBack = false;

        while (!goBack) {
            System.out.println();
            System.out.println("------------- MOVIE MENU ---------------");
            System.out.println("1. Add Movie");
            System.out.println("2. View All Movies");
            System.out.println("3. Find Movie");
            System.out.println("4. Update Movie");
            System.out.println("5. Delete Movie");
            System.out.println("6. Back to Main Menu");

            int choice = readInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    displayMovies();
                    break;
                case 3:
                    findMovie();
                    break;
                case 4:
                    updateMovie();
                    break;
                case 5:
                    deleteMovie();
                    break;
                case 6:
                    goBack = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addMovie() {
        System.out.println();
        System.out.println("Add a New Movie");

        String title = readText("Movie title: ");
        String genre = readText("Genre: ");
        String language = readText("Language: ");
        int duration = readInteger("Duration in minutes: ");
        double rating = readDouble("Rating: ");

        Movie movie = new Movie(title, genre, language, duration, rating);
        Database.addMovie(movie);
    }

    private void displayMovies() {
        List<Movie> movies = Database.getMovies();

        System.out.println();
        System.out.println("Available Movies");
        System.out.println("----------------");

        if (movies.isEmpty()) {
            System.out.println("No movies have been added yet.");
            return;
        }

        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    private void findMovie() {
        int id = readInteger("Enter movie ID: ");
        Movie movie = Database.getMovie(id);

        if (movie == null) {
            System.out.println("Movie not found.");
        } else {
            System.out.println("Movie found:");
            System.out.println(movie);
        }
    }

    private void updateMovie() {
        int id = readInteger("Enter movie ID to update: ");
        Movie movie = Database.getMovie(id);

        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        System.out.println("Enter the new movie details.");

        String title = readText("New title: ");
        String genre = readText("New genre: ");
        String language = readText("New language: ");
        int duration = readInteger("New duration: ");
        double rating = readDouble("New rating: ");

        Database.updateMovie(id, title, genre, language, duration, rating);
    }

    private void deleteMovie() {
        int id = readInteger("Enter movie ID to delete: ");
        Movie movie = Database.getMovie(id);

        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        Database.deleteMovie(id);
    }

    // ================================================================
    // THEATRE MENU
    // ================================================================

    private void theatreMenu() {
        boolean goBack = false;

        while (!goBack) {
            System.out.println();
            System.out.println("------------ THEATRE MENU --------------");
            System.out.println("1. Add Theatre");
            System.out.println("2. View All Theatres");
            System.out.println("3. Find Theatre");
            System.out.println("4. Update Theatre");
            System.out.println("5. Delete Theatre");
            System.out.println("6. Back to Main Menu");

            int choice = readInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    addTheatre();
                    break;
                case 2:
                    displayTheatres();
                    break;
                case 3:
                    findTheatre();
                    break;
                case 4:
                    updateTheatre();
                    break;
                case 5:
                    deleteTheatre();
                    break;
                case 6:
                    goBack = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addTheatre() {
        System.out.println();
        System.out.println("Add a New Theatre");

        String name = readText("Theatre name: ");
        String location = readText("Location: ");
        int seats = readInteger("Total number of seats: ");

        Theatre theatre = new Theatre(name, location, seats);
        Database.addTheatre(theatre);
    }

    private void displayTheatres() {
        List<Theatre> theatres = Database.getTheatres();

        System.out.println();
        System.out.println("Available Theatres");
        System.out.println("------------------");

        if (theatres.isEmpty()) {
            System.out.println("No theatres have been added yet.");
            return;
        }

        for (Theatre theatre : theatres) {
            System.out.println(theatre);
        }
    }

    private void findTheatre() {
        int id = readInteger("Enter theatre ID: ");
        Theatre theatre = Database.getTheatre(id);

        if (theatre == null) {
            System.out.println("Theatre not found.");
        } else {
            System.out.println(theatre);
        }
    }

    private void updateTheatre() {
        int id = readInteger("Enter theatre ID to update: ");
        Theatre theatre = Database.getTheatre(id);

        if (theatre == null) {
            System.out.println("Theatre not found.");
            return;
        }

        String name = readText("New theatre name: ");
        String location = readText("New location: ");
        int seats = readInteger("New total seats: ");

        Database.updateTheatre(id, name, location, seats);
    }

    private void deleteTheatre() {
        int id = readInteger("Enter theatre ID to delete: ");
        Theatre theatre = Database.getTheatre(id);

        if (theatre == null) {
            System.out.println("Theatre not found.");
            return;
        }

        Database.deleteTheatre(id);
    }

    // ================================================================
    // SHOW MENU
    // ================================================================

    private void showMenu() {
        boolean goBack = false;

        while (!goBack) {
            System.out.println();
            System.out.println("-------------- SHOW MENU ---------------");
            System.out.println("1. Add Show");
            System.out.println("2. View All Shows");
            System.out.println("3. Find Show");
            System.out.println("4. Update Show");
            System.out.println("5. Delete Show");
            System.out.println("6. Back to Main Menu");

            int choice = readInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    addShow();
                    break;
                case 2:
                    displayShows();
                    break;
                case 3:
                    findShow();
                    break;
                case 4:
                    updateShow();
                    break;
                case 5:
                    deleteShow();
                    break;
                case 6:
                    goBack = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addShow() {
        System.out.println();
        System.out.println("Add a New Show");

        int movieId = readInteger("Movie ID: ");
        int theatreId = readInteger("Theatre ID: ");

        if (Database.getMovie(movieId) == null) {
            System.out.println("Movie not found. Add the movie first.");
            return;
        }

        if (Database.getTheatre(theatreId) == null) {
            System.out.println("Theatre not found. Add the theatre first.");
            return;
        }

        String time = readText("Show time: ");
        double price = readDouble("Ticket price: ");

        Show show = new Show(movieId, theatreId, time, price);
        Database.addShow(show);
    }

    private void displayShows() {
        List<Show> shows = Database.getShows();

        System.out.println();
        System.out.println("Available Shows");
        System.out.println("---------------");

        if (shows.isEmpty()) {
            System.out.println("No shows have been added yet.");
            return;
        }

        for (Show show : shows) {
            System.out.println(show);
        }
    }

    private void findShow() {
        int id = readInteger("Enter show ID: ");
        Show show = Database.getShow(id);

        if (show == null) {
            System.out.println("Show not found.");
        } else {
            System.out.println(show);
        }
    }

    private void updateShow() {
        int id = readInteger("Enter show ID to update: ");
        Show show = Database.getShow(id);

        if (show == null) {
            System.out.println("Show not found.");
            return;
        }

        int movieId = readInteger("New movie ID: ");
        int theatreId = readInteger("New theatre ID: ");
        String time = readText("New show time: ");
        double price = readDouble("New ticket price: ");

        Database.updateShow(id, movieId, theatreId, time, price);
    }

    private void deleteShow() {
        int id = readInteger("Enter show ID to delete: ");
        Show show = Database.getShow(id);

        if (show == null) {
            System.out.println("Show not found.");
            return;
        }

        Database.deleteShow(id);
    }

    // ================================================================
    // CUSTOMER MENU
    // ================================================================

    private void customerMenu() {
        boolean goBack = false;

        while (!goBack) {
            System.out.println();
            System.out.println("----------- CUSTOMER MENU --------------");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Find Customer");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. Back to Main Menu");

            int choice = readInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    displayCustomers();
                    break;
                case 3:
                    findCustomer();
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    deleteCustomer();
                    break;
                case 6:
                    goBack = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addCustomer() {
        System.out.println();
        System.out.println("Add a New Customer");

        String name = readText("Name: ");
        String email = readText("Email: ");
        String phone = readText("Phone: ");

        Customer customer = new Customer(name, email, phone);
        Database.addCustomer(customer);
    }

    private void displayCustomers() {
        List<Customer> customers = Database.getCustomers();

        System.out.println();
        System.out.println("Registered Customers");
        System.out.println("--------------------");

        if (customers.isEmpty()) {
            System.out.println("No customers have been added yet.");
            return;
        }

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private void findCustomer() {
        int id = readInteger("Enter customer ID: ");
        Customer customer = Database.getCustomer(id);

        if (customer == null) {
            System.out.println("Customer not found.");
        } else {
            System.out.println(customer);
        }
    }

    private void updateCustomer() {
        int id = readInteger("Enter customer ID to update: ");
        Customer customer = Database.getCustomer(id);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        String name = readText("New name: ");
        String email = readText("New email: ");
        String phone = readText("New phone: ");

        Database.updateCustomer(id, name, email, phone);
    }

    private void deleteCustomer() {
        int id = readInteger("Enter customer ID to delete: ");
        Customer customer = Database.getCustomer(id);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        Database.deleteCustomer(id);
    }

    // ================================================================
    // SEATS AND BOOKINGS
    // ================================================================

    private void displaySeats() {
        System.out.println();
        System.out.println("View Theatre Seats");

        int theatreId = readInteger("Enter theatre ID: ");

        if (Database.getTheatre(theatreId) == null) {
            System.out.println("Theatre not found.");
            return;
        }

        List<Seat> seats = Database.getSeats(theatreId);

        if (seats.isEmpty()) {
            System.out.println("No seats found for this theatre.");
            return;
        }

        for (Seat seat : seats) {
            System.out.println(seat);
        }
    }

    private void bookTicket() {
        System.out.println();
        System.out.println("------------- BOOK TICKET --------------");

        int customerId = readInteger("Customer ID: ");
        int showId = readInteger("Show ID: ");
        int seatId = readInteger("Seat ID: ");

        Database.bookTicket(customerId, showId, seatId);
    }

    private void cancelBooking() {
        System.out.println();
        System.out.println("------------ CANCEL BOOKING ------------");

        int bookingId = readInteger("Booking ID: ");
        Database.cancelBooking(bookingId);
    }

    private void displayBookings() {
        List<Booking> bookings = Database.getBookings();

        System.out.println();
        System.out.println("Current Bookings");
        System.out.println("----------------");

        if (bookings.isEmpty()) {
            System.out.println("No bookings have been made yet.");
            return;
        }

        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    // ================================================================
    // INPUT METHODS
    // ================================================================

    /**
     * Reads an integer and keeps asking until the user enters a valid one.
     */
    private int readInteger(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a decimal number from the console.
     */
    private double readDouble(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid decimal number.");
            }
        }
    }

    /**
     * Reads normal text input from the user.
     */
    private String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}

