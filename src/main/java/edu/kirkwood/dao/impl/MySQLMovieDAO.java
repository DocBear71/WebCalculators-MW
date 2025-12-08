package edu.kirkwood.dao.impl;

import edu.kirkwood.dao.MovieDAO;
import edu.kirkwood.model.Movie;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static edu.kirkwood.dao.MySQLConnection.getConnection;

public class MySQLMovieDAO implements MovieDAO {

    @Override
    public List<Movie> search(String title) {
        // try with resources
        // The object instantiated inside the parenthesis must be Autocloseable

        try (Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{ CALL sp_SearchMoviesByTitle(?) }");
            statement.setString(1, title);
            ResultSet resultSet =  statement.executeQuery();
            List<Movie> movies = new ArrayList<>();
            while(resultSet.next()){
                Movie movie = new Movie();
                movie.setId(resultSet.getString("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setReleaseYear(resultSet.getInt("year"));
                movie.setPlot(resultSet.getString("plot"));
                movies.add(movie);

            }
            return movies;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
