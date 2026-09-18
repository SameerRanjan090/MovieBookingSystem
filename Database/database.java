
package database;

import model.Booking;
import model.Customer;
import model.Movie;
import model.Seat;
import model.Show;
import model.Theatre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Handles all database operations for the project.
 *
 * The project intentionally keeps this class simple. There are no DAO or
 * service classes. The console interface calls the methods in this class
 * directly, and this class communicates with the SQLite database.
 */
public class Database {

    private static final String URL = "jdbc:sqlite:movie_booking.db";

    /**
     * Opens a connection to the SQLite database.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Creates all tables needed by the application.
     */
    public static void initializeDatabase() {
        // Loading the driver explicitly makes the application easier to run
        // with a manually added SQLite JDBC jar in IntelliJ IDEA.
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver was not found.");
            System.out.println("Please add sqlite-jdbc.jar to the project libraries.");
            return;
        }

        String movieTable = "CREATE TABLE IF NOT EXISTS movies ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT NOT NULL, "
                + "genre TEXT NOT NULL, "
                + "language TEXT NOT NULL, "
                + "duration INTEGER NOT NULL, "
                + "rating REAL"
                + ")";

        String theatreTable = "CREATE TABLE IF NOT EXISTS theatres ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "location TEXT NOT NULL, "
                + "total_seats INTEGER NOT NULL"
                + ")";

        String showTable = "CREATE TABLE IF NOT EXISTS shows ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "movie_id INTEGER NOT NULL, "
                + "theatre_id INTEGER NOT NULL, "
                + "show_time TEXT NOT NULL, "
                + "ticket_price REAL NOT NULL"
                + ")";

        String customerTable = "CREATE TABLE IF NOT EXISTS customers ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "email TEXT NOT NULL UNIQUE, "
                + "phone TEXT NOT NULL"
                + ")";

        String seatTable = "CREATE TABLE IF NOT EXISTS seats ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "theatre_id INTEGER NOT NULL, "
                + "seat_number TEXT NOT NULL, "
                + "available INTEGER NOT NULL DEFAULT 1, "
                + "UNIQUE(theatre_id, seat_number)"
                + ")";

        String bookingTable = "CREATE TABLE IF NOT EXISTS bookings ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "customer_id INTEGER NOT NULL, "
                + "show_id INTEGER NOT NULL, "
                + "seat_id INTEGER NOT NULL, "
                + "booking_time TEXT NOT NULL, "
                + "total_price REAL NOT NULL, "
                + "UNIQUE(show_id, seat_id)"
                + ")";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(movieTable);
            statement.execute(theatreTable);
            statement.execute(showTable);
            statement.execute(customerTable);
            statement.execute(seatTable);
            statement.execute(bookingTable);

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    // MOVIE OPERATIONS
    // -----------------------------------------------------------------

    public static void addMovie(Movie movie) {
        String sql = "INSERT INTO movies "
                + "(title, genre, language, duration, rating) "
                + "VALUES (?, ?, ?, ?, ?)";

        executeUpdate(sql,
                movie.getTitle(),
                movie.getGenre(),
                movie.getLanguage(),
                movie.getDuration(),
                movie.getRating());
    }

    public static List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies ORDER BY id";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Movie movie = new Movie(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("genre"),
                        result.getString("language"),
                        result.getInt("duration"),
                        result.getDouble("rating")
                );

                movies.add(movie);
            }

        } catch (SQLException e) {
            printDatabaseError(e);
        }

        return movies;
    }

    public static Movie getMovie(int id) {
        String sql = "SELECT * FROM movies WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Movie(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("genre"),
                        result.getString("language"),
                        result.getInt("duration"),
                        result.getDouble("rating")
                );
            }

        } catch (SQLException e) {
            printDatabaseError(e);
        }

        return null;
    }

    public static void updateMovie(int id, String title, String genre,
                                   String language, int duration,
                                   double rating) {
        String sql = "UPDATE movies SET title = ?, genre = ?, language = ?, "
                + "duration = ?, rating = ? WHERE id = ?";

        executeUpdate(sql, title, genre, language, duration, rating, id);
    }

    public static void deleteMovie(int id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        executeUpdate(sql, id);
    }

    // -----------------------------------------------------------------
    // THEATRE OPERATIONS
    // -----------------------------------------------------------------

    public static void addTheatre(Theatre theatre) {
        String sql = "INSERT INTO theatres (name, location, total_seats) "
                + "VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, theatre.getName());
            statement.setString(2, theatre.getLocation());
            statement.setInt(3, theatre.getTotalSeats());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                int theatreId = keys.getInt(1);
                generateSeats(theatreId, theatre.getTotalSeats());
            }

            System.out.println("Theatre added successfully.");

        } catch (SQLException e) {
            printDatabaseError(e);
        }
    }

    /**
     * Creates seats automatically when a new theatre is added.
     * For example: A1, A2 ... A10, B1, B2 ...
     */
    private static void generateSeats(int theatreId, int totalSeats) {
        String sql = "INSERT OR IGNORE INTO seats "
                + "(theatre_id, seat_number, available) VALUES (?, ?, 1)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < totalSeats; i++) {
                char row = (char) ('A' + (i / 10));
                int seatNumber = (i % 10) + 1;
                String seatName = row + String.valueOf(seatNumber);

                statement.setInt(1, theatreId);
                statement.setString(2, seatName);
                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            printDatabaseError(e);
        }
    }

    public static List<Theatre> getTheatres() {
        List<Theatre> theatres = new ArrayList<>();
        String sql = "SELECT * FROM theatres ORDER BY id";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Theatre theatre = new Theatre(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("location"),
                        result.getInt("total_seats")
                );

                theatres.add(theatre);
            }

        } catch (SQLException e) {
            printDatabaseError(e);
        }

        return theatres;
    }

    public static Theatre getTheatre(int id) {
        String sql = "SELECT * FROM theatres WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Theatre(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("location"),
                        result.getInt("total_seats")
                );
            }

        } catch (SQLException e) {
            printDatabaseError(e);
        }

        return null;
    }

    public static void updateTheatre(int id, String name,
                                     String location, int totalSeats) {
        String sql = "UPDATE theatres SET name = ?, location = ?, "
                + "total_seats = ? WHERE id = ?";

        executeUpdate(sql, name, location, totalSeats, id);
    }

    public static void deleteTheatre(int id) {
        String sql = "DELETE FROM theatres WHERE id = ?";
        executeUpdate(sql, id);
    }

    // -----------------------------------------------------------------
    // SEAT OPERATIONS
    // -----------------------------------------------------------------

    public static List<Seat> getSeats(int theatreId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE theatre_id = ? ORDER BY id";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, theatreId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Seat seat = new Seat(
                        result.getInt("id"),
                        result.getInt("theatre_id"),
                        result.getString("seat_number"),
                        result.getInt("available") == 1
                );

                seats.add(seat);
            }

        } catch (SQLException e) {
            printDatabaseError(e);
        }

        return seats;
    }

    // -----------------------------------------------------------------
    // SHOW OPERATIONS
    // -----------------------------------------------------------------

    public static void addShow(Show show) {
        String sql = "INSERT INTO shows "
                + "(movie_id, theatre_id, show_time, ticket_price) "
                + "VALUES (?, ?, ?, ?)";

        executeUpdate(sql,
                show.getMovieId(),
                show.getTheatreId(),
                show.getShowTime(),
                show.getTicketPrice());
    }

    public static List<Show> getShows() {
        List<Show> shows = new ArrayList<>();
        String sql = "SELECT * FROM shows ORDER BY id";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Show show = new Show(
                        result.getInt("id"),
                        result.getInt("movie_id"),
                        result.getInt("theatre_id"),
                        result.getString("show_time"),
                        result.getDouble("ticket_price")
                );

                shows.add(show);
            }

        } catch (SQLException e) {
            printDatabaseError(e);
        }

        return shows;
    }

    public static Show getShow(int id) {
        String sql = "SELECT * FROM shows WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Show(
                        result.getInt("id"),
                        result.getInt("movie_id"),
                        result.getInt("theatre_id"),
                        result.getString("show_time"),
                        result.getDouble("ticket_price")
                );
            }

        } catch (SQLException e) {
            printDatabaseError(e);
        }

        return null;
    }

    public static void updateShow(int id, int movieId, int theatreId,
                                  String showTime, double ticketPrice) {
        String sql = "UPDATE shows SET movie_id = ?, theatre_id = ?, "
                + "show_time = ?, ticket_price = ? WHERE id = ?";

        executeUpdate(sql, movieId, theatreId, showTime, ticketPrice, id);
    }

    public static void deleteShow(int id) {
        String sql = "DELETE FROM shows WHERE id = ?";
        executeUpdate(sql, id);
    }

    // -----------------------------------------------------------------
    // CUSTOMER OPERATIONS
    // -----------------------------------------------------------------

    public static void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, email, phone) "
                + "VALUES (?, ?, ?)";

        executeUpdate(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getPhone());
    }

    public static List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers ORDER BY id";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Customer customer = new Customer(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("phone")
                );

                customers.add(customer);
            }

        } catch (SQLException e) {
            printDatabaseError(e);
        }

        return customers;
    }

    public static Customer getCustomer(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Customer(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("phone")
                );
            }

        } catch (SQLException e) {
            printDatabaseError(e);
        }

        return null;
    }

    public static void updateCustomer(int id, String name,
                                      String email, String phone) {
        String sql = "UPDATE customers SET name = ?, email = ?, "
                + "phone = ? WHERE id = ?";

        executeUpdate(sql, name, email, phone, id);
    }

    public static void deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        executeUpdate(sql, id);
    }

    // -----------------------------------------------------------------
    // BOOKING OPERATIONS
    // -----------------------------------------------------------------

    public static boolean bookTicket(int customerId, int showId, int seatId) {
        Show show = getShow(showId);
        Customer customer = getCustomer(customerId);

        if (show == null) {
            System.out.println("Show not found.");
            return false;
        }

        if (customer == null) {
            System.out.println("Customer not found.");
            return false;
        }

        String seatCheck = "SELECT theatre_id FROM seats "
                + "WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(seatCheck)) {

            statement.setInt(1, seatId);
            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                System.out.println("Seat not found.");
                return false;
            }

            int seatTheatreId = result.getInt("theatre_id");

            if (seatTheatreId != show.getTheatreId()) {
                System.out.println("This seat does not belong to the show theatre.");
                return false;
            }

        } catch (SQLException e) {
            printDatabaseError(e);
            return false;
        }

        // A seat can be used again for another show. The unique constraint
        // prevents the same seat from being booked twice for one show.
        String sql = "INSERT INTO bookings "
                + "(customer_id, show_id, seat_id, booking_time, total_price) "
                + "VALUES (?, ?, ?, ?, ?)";

        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            statement.setInt(2, showId);
            statement.setInt(3, seatId);
            statement.setString(4, currentTime);
            statement.setDouble(5, show.getTicketPrice());

            statement.executeUpdate();

            System.out.println("Booking successful.");
            return true;

        } catch (SQLException e) {
            System.out.println("Booking failed. The seat may already be booked for this show.");
            return false;
        }
    }

    public static List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY id";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Booking booking = new Booking(
                        result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getInt("show_id"),
                        result.getInt("seat_id"),
                        result.getString("booking_time"),
                        result.getDouble("total_price")
                );

                bookings.add(booking);
            }

        } catch (SQLException e) {
            printDatabaseError(e);
        }

        return bookings;
    }

    public static void cancelBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        executeUpdate(sql, bookingId);
    }

    // -----------------------------------------------------------------
    // COMMON DATABASE METHODS
    // -----------------------------------------------------------------

    /**
     * Runs a simple INSERT, UPDATE or DELETE query.
     */
    private static void executeUpdate(String sql, Object... values) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }

            int rowsChanged = statement.executeUpdate();

            if (rowsChanged > 0) {
                System.out.println("Operation completed successfully.");
            } else {
                System.out.println("No record was changed.");
            }

        } catch (SQLException e) {
            System.out.println("Operation failed: " + e.getMessage());
        }
    }

    private static void printDatabaseError(SQLException e) {
        System.out.println("Database error: " + e.getMessage());
    }
}
