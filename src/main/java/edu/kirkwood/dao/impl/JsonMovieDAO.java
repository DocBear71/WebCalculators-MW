package edu.kirkwood.dao.impl;

import com.google.gson.*;
import edu.kirkwood.dao.JsonTypeAdapter;
import edu.kirkwood.dao.MovieDAO;
import edu.kirkwood.model.Movie;
import edu.kirkwood.model.json.TmdbMovieResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonMovieDAO implements MovieDAO {
    private String apiURL;
    private String apiKey;
    public JsonMovieDAO(String apiURL, String apiKey) {
        this.apiURL = apiURL;
        this.apiKey = apiKey;
    }

    public String fetchRawData(String title, int page) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiURL + "query=" + title + "&page=" + page) // Todo: add pagination
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("accept", "application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String responseBody = "";
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseBody;
    }

    /**
     * Outputs an unformatted json string a human-readable format
     * @param json A valid json string
     */
    public void prettyPrint(String json) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        JsonElement jsonElement = new JsonParser().parse(json);
        String formattedJson = gson.toJson(jsonElement);
        System.out.println(formattedJson);
    }

    /**
     * Retrieves all movies from the data source that matches the title
     * @param title The movie title a user is searching for
     * @return A List&lt;Movie&gt; movies that matches the search title
     */
    @Override
    public List<Movie> search(String title) {
        List<Movie> movies = new ArrayList<>();
        int currentPage = 1;
        while (true) {
            String rawData = fetchRawData(title, currentPage);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new JsonTypeAdapter.LocalDateDeserializer())
                    .create();
            // prettyPrint(rawData);
            TmdbMovieResponse movieResponse = null;
            try {
                movieResponse = gson.fromJson(rawData, TmdbMovieResponse.class);
            } catch (JsonSyntaxException e) {
                throw new RuntimeException(e);
            }
            // System.out:: printlin is an example of a method response
            // movieResponse.getResults().forEach(System.out::println);
            // result -> {} is an example of a lambda expression
            movieResponse.getResults().forEach(result -> {
                Movie movie = new Movie();
                movie.setId(result.getId());
                movie.setTitle(result.getTitle());
                if (result.getRelease_date() != null) {
                    movie.setReleaseYear(result.getRelease_date().getYear());
                }
                movie.setPlot(result.getOverview());
                movie.setPoster(result.getPoster_path());
                movies.add(movie);
            });
            if(movieResponse.getTotal_pages() > currentPage) {
                currentPage++;
            } else {
                break;
            }

        }
        return movies;
    }
}
