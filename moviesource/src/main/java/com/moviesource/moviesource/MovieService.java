package com.moviesource.moviesource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.moviesource.moviesource.model.Movie;
import com.moviesource.moviesource.model.NowShowingRequest;
import com.moviesource.moviesource.model.NowShowingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by thinhda.
 * Date: 2019-08-18
 */

@Service
@Slf4j
public class MovieService {

    public NowShowingResponse getNowShowing(NowShowingRequest request) throws JsonProcessingException {
        log.info("Received now showing request {}", new Gson().toJson(request));

        RestTemplate restTemplate = new RestTemplate();
        String nowShowingUrl = "https://www.eventcinemas.com.au/Movies/GetNowShowing";
        ResponseEntity<String> response = restTemplate.getForEntity(nowShowingUrl, String.class);

        if (response.getStatusCode().isError()) {
            log.error("Got an error response");
        }

        ArrayList<Movie> movies = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String body = response.getBody();
            body = "}" + body;
            JsonNode jsonRoot = mapper.readTree(body);
            JsonNode jsonMovies = jsonRoot.path("Data").path("Movies");
            for (JsonNode jsonNode: jsonMovies) {
                movies.add(Movie.newBuilder()
                        .setTitle(jsonNode.get("Name").asText())
                        .setDirector(jsonNode.get("Director").asText())
                        .setGenre(jsonNode.path("MovieGenres").path(0).get("Name").asText())
                        .setImageUrl(jsonNode.get("LargePosterUrl").asText())
                        .build());
            }
        } catch (JsonProcessingException exception) {
            log.error("Error parsing JSON response from Event Cinemas");
            throw exception;
        }

        return NowShowingResponse.newBuilder()
                .setTimestamp(request.getTimestamp())
                .addAllMovies(movies)
                .build();
    }
}