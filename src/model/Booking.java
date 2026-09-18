package model;

/**
 * Stores the details of a ticket booking.
 */
public class Booking {

    private int id;
    private int customerId;
    private int showId;
    private int seatId;
    private String bookingTime;
    private double totalPrice;

    public Booking(int id, int customerId, int showId, int seatId,
                   String bookingTime, double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.showId = showId;
        this.seatId = seatId;
        this.bookingTime = bookingTime;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getShowId() {
        return showId;
    }

    public int getSeatId() {
        return seatId;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Booking ID: " + id
                + " | Customer ID: " + customerId
                + " | Show ID: " + showId
                + " | Seat ID: " + seatId
                + " | Time: " + bookingTime
                + " | Total: ₹" + totalPrice;
    }
}

