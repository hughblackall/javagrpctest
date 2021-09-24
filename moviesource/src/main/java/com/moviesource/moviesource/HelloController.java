package com.moviesource.moviesource;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.moviesource.moviesource.model.NowShowingRequest;
import com.moviesource.moviesource.model.NowShowingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    private MovieService movieService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot! I am the movie data source!";
    }

    @RequestMapping("/consumeNowShowing")
    public String consumeNowShowing(HttpServletResponse springResponse) {

        NowShowingResponse nowShowingResponse = null;
        try {
            nowShowingResponse = movieService.getNowShowing(NowShowingRequest.newBuilder().setTimestamp(123456789L).build());
        } catch (JsonProcessingException exception) {
            springResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return exception.getMessage();
        }
        return nowShowingResponse.toString();
    }
}