package edu.kirkwood.model.json;

import java.time.LocalDate;

public class MovieSearchResult {
    private String id;
    private String title;
    private String overview;
    private LocalDate release_date;
    private String poster_path;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    @Override
    public String toString() {
        return "MovieSearchResult{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date=" + release_date +
                '}';
    }
}
