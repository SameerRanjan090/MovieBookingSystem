package model;

/**
 * Represents a movie available for booking in the cinema system.
 */
public class Movie {

    private int id;
    private String title;
    private String genre;
    private String language;
    private int duration;
    private double rating;

    public Movie(String title, String genre, String language,
                 int duration, double rating) {
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.duration = duration;
        this.rating = rating;
    }

    public Movie(int id, String title, String genre, String language,
                 int duration, double rating) {
        this(title, genre, language, duration, rating);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public int getDuration() {
        return duration;
    }

    public double getRating() {
        return rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie ID: " + id
                + " | Title: " + title
                + " | Genre: " + genre
                + " | Language: " + language
                + " | Duration: " + duration + " min"
                + " | Rating: " + rating;
    }
}

