package edu.kirkwood.dao;

import edu.kirkwood.dao.impl.JsonMovieDAO;
import edu.kirkwood.dao.impl.MySQLMovieDAO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MovieDAOFactory {
    private static Properties properties =  new Properties();

    static {
        try(InputStream input = MovieDAOFactory.class.getClassLoader()
                                .getResourceAsStream("application.properties");) {
            if(input == null) {
                throw new RuntimeException("application.properties file not found");
            }
            properties.load(input);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To get the data access object specified in the application.properties file
     * @return a MovieDAO (XMLMovieDAO, MySQLMovieDAO, etc.)
     */
    public static MovieDAO getMovieDAO() {
        String sourceType = properties.getProperty("datasource.type");
        if(sourceType == null || sourceType.isEmpty()) {
            throw new RuntimeException("datasource.type not set");
        }
        switch (sourceType.toUpperCase()) {
//            case "XML":
//                String apiURL = properties.getProperty("xml.apiurl");
//                if(apiURL == null || apiURL.isEmpty()) {
//                    throw new RuntimeException("xml.apiurl is empty");
//                }
//                return new XmlMovieDAO(apiURL);
            case "MYSQL":
                return new MySQLMovieDAO();
            case "JSON":
                String jsonApiURL = properties.getProperty("json.apiURL");
                if(jsonApiURL == null || jsonApiURL.isEmpty()) {
                    throw new IllegalArgumentException("json.apiURL is required");
                }
                String jsonReadAccessToken = properties.getProperty("json.apiReadAccessToken");
                if(jsonReadAccessToken == null || jsonReadAccessToken.isEmpty()) {
                    throw new IllegalArgumentException("json.apiReadAccessToken");
                }
                return new JsonMovieDAO(jsonApiURL, jsonReadAccessToken);
//            case "MONGODB":
//                break;

        }
        return null;
    }

}
