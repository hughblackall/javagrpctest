package com.moviepresenter.moviepresenter;


import com.moviesource.moviesource.model.Movie;
import com.moviesource.moviesource.model.NowShowingResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {

        MovieGrpcClient client = new MovieGrpcClient();
        NowShowingResponse response = client.getNowShowing();

        List<Movie> movies = response.getMoviesList();
        StringBuilder htmlResponse = new StringBuilder("<body><div>");
        for (Movie movie: movies) {
            htmlResponse.append("<div style=\"margin-top: 50px\">");
            htmlResponse.append("<img src=\"").append(movie.getImageUrl()).append("\">");
            htmlResponse.append("<h3>").append(movie.getTitle()).append("</h3>");
            htmlResponse.append("<p>Directed by: ").append(movie.getDirector()).append("</p>");
            htmlResponse.append("<p>Genre: ").append(movie.getGenre()).append("</p>");
            htmlResponse.append("</div>");
        }
        htmlResponse.append("</div></body>");
        return htmlResponse.toString();
    }

}