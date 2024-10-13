package com.micro.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class register {  // Updated class name to follow conventions

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static String base62Encode(int deci) {
        String s = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder hashStr = new StringBuilder();

        while (deci > 0) {
            hashStr.insert(0, s.charAt(deci % 62)); // prepend the character
            deci /= 62;
        }

        return hashStr.toString();
    }
    private static final Random random = new Random();
    @PostMapping("/register")
    //public String handleContact(@RequestBody ContactRequest contactRequest) {
    public String handleContact(String longurl) {
        //String longUrl = contactRequest.getLongurl();

        // Check if the longUrl exists
        String sql = "SELECT shorturl FROM url1 WHERE longurl = ?";

        try {
            String existingShortUrl = jdbcTemplate.queryForObject(sql, new Object[]{longurl}, String.class);
            return "{\"shorturl\":\"" + existingShortUrl + "\"}"; // Return shorturl if URL exists
        } catch (Exception e) {
            // If longUrl doesn't exist, insert it into the table and get the auto-generated id
            sql = "INSERT INTO url1 (longurl) VALUES (?)";
            String d = sql;
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(d, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, longurl);
                return ps;
            }, keyHolder);

            // Get the auto-incremented id
            int id = 7;

            // Generate base62 short URL using the id
            ArrayList<Integer> list = new ArrayList<>();
            int randomNum = random.nextInt(1000) + 1;

            String shortUrl = base62Encode(randomNum);

            // Update the table with the generated short URL
            String updateSql = "UPDATE url1 SET shorturl = ? WHERE longurl = ?";
            jdbcTemplate.update(updateSql, shortUrl,longurl);

            return "{\"message\":\"URL inserted successfully\", \"shorturl\":\"" + shortUrl + "\"}";
        }
    }

    // Static inner class for request body
    public static class ContactRequest {
        private String longurl;

        public String getLongurl() {
            return longurl;
        }

        public void setLongurl(String longurl)
        {
            this.longurl = longurl;
        }
    }
}
