package edu.kirkwood.model;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

public class Movie implements Comparable<Movie>{
    private String id;
    private String title;
    private int releaseYear;
    private String plot;
    private String poster;

    public Movie() {
    }

    public Movie(String id, String title, int releaseYear, String undefined) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.plot = plot;
        this.poster = poster;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", plot='" + plot + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }


    /**
     * Compares two movie objects by their ID
     * @param o The other Movie object to be compared
     * @return an int indicating the order of two objects
     */
    @Override
    public int compareTo(@NotNull Movie o) {
        if(this.id.length() != o.id.length()) {
            return Integer.compare(this.id.length(), o.id.length());
        }
        return this.id.compareToIgnoreCase(o.id);
    }

    public static Comparator<Movie> compareTitle = (m1, m2) -> m1.title.compareToIgnoreCase(m2.title);
    public static Comparator<Movie> compareYear = Comparator.comparing(Movie::getReleaseYear); // Comparator.comparingInt(m -> m.releaseYear);

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return releaseYear == movie.releaseYear && Objects.equals(id, movie.id) && Objects.equals(title, movie.title) && Objects.equals(plot, movie.plot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseYear, plot);
    }
}
