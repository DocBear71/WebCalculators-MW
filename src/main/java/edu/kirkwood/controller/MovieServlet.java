package edu.kirkwood.controller;

import edu.kirkwood.dao.MovieDAO;
import edu.kirkwood.dao.MovieDAOFactory;
import edu.kirkwood.dao.impl.JsonMovieDAO;
import edu.kirkwood.dao.impl.MySQLMovieDAO;
import edu.kirkwood.model.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet(value="/movies")
public class MovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String search = req.getParameter("movie");
        req.setAttribute("movie", search);

        if (search != null && !search.isEmpty()) {
            try {
                List<Movie> movies = getResults(search);
                if (movies.isEmpty()) {
                    req.setAttribute("searchError", "No results found");
                } else {
                    req.setAttribute("movies", movies);
                }
            } catch (RuntimeException e) {
                req.setAttribute("searchError", e.getMessage());
            }
        }
        req.getRequestDispatcher("WEB-INF/movies.jsp").forward(req, resp);
    }

    public static List<Movie> getResults(String search) {
        try {
            MovieDAO movieDAO = MovieDAOFactory.getMovieDAO();
            List<Movie> movies = new ArrayList<>();

            if(movieDAO instanceof MySQLMovieDAO) {
                movies.addAll(((MySQLMovieDAO)movieDAO).search(search));
            } else if(movieDAO instanceof JsonMovieDAO) {
                movies.addAll(((JsonMovieDAO)movieDAO).search(search));
            }

            return movies;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
