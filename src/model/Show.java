package model;

/**
 * Connects a movie with a theatre and stores the show time and ticket price.
 */
public class Show {

    private int id;
    private int movieId;
    private int theatreId;
    private String showTime;
    private double ticketPrice;

    public Show(int movieId, int theatreId, String showTime, double ticketPrice) {
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }

    public Show(int id, int movieId, int theatreId,
                String showTime, double ticketPrice) {
        this(movieId, theatreId, showTime, ticketPrice);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public String getShowTime() {
        return showTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Show ID: " + id
                + " | Movie ID: " + movieId
                + " | Theatre ID: " + theatreId
                + " | Time: " + showTime
                + " | Ticket Price: ₹" + ticketPrice;
    }
}

