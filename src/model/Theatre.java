package model;

/**
 * Represents a theatre where movies are shown.
 */
public class Theatre {

    private int id;
    private String name;
    private String location;
    private int totalSeats;

    public Theatre(String name, String location, int totalSeats) {
        this.name = name;
        this.location = location;
        this.totalSeats = totalSeats;
    }

    public Theatre(int id, String name, String location, int totalSeats) {
        this(name, location, totalSeats);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return "Theatre ID: " + id
                + " | Name: " + name
                + " | Location: " + location
                + " | Seats: " + totalSeats;
    }
}

