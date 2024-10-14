package com.micro.demo.okk;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;



@RestController
public class takingreq {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/add")
    public String see(@RequestParam String a ) {


        String sql = "select longurl from url1 where shorturl = ?";
        String longUrl = jdbcTemplate.queryForObject(sql, new Object[]{a}, String.class);


        String resopnse = restTemplate.getForObject(longUrl, String.class);
        return resopnse;

    }
}
*/
import java.io.IOException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class takingreq {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/add")
    public void redirectToLongUrl(@RequestParam("add") String c, HttpServletResponse response) throws IOException {
        // SQL query to fetch longurl from the database based on the shorturl
        String sql = "SELECT longurl FROM url1 WHERE shorturl = ?";

        // Execute the query and retrieve the longurl
        String longUrl = jdbcTemplate.queryForObject(sql, new Object[]{c}, String.class);

        // Redirect the user to the longUrl
        response.sendRedirect(longUrl);
    }
}



