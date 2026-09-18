package model;

/**
 * Represents one seat inside a theatre.
 */
public class Seat {

    private int id;
    private int theatreId;
    private String seatNumber;
    private boolean available;

    public Seat(int id, int theatreId, String seatNumber, boolean available) {
        this.id = id;
        this.theatreId = theatreId;
        this.seatNumber = seatNumber;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        String status;

        if (available) {
            status = "Available";
        } else {
            status = "Booked";
        }

        return "Seat ID: " + id
                + " | Seat: " + seatNumber
                + " | Status: " + status;
    }
}

