import database.Database;
import ui.ConsoleUI;

/**
 * Starting point of the Movie Ticket Booking System.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Starting Movie Ticket Booking System...");

        // Create the database tables before opening the menu.
        Database.initializeDatabase();

        // Start the console-based user interface.
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.start();

        System.out.println("Application closed.");
    }
}

