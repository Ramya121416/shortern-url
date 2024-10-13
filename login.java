package com.micro.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class login {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    register re;
    @PostMapping("/contact")
    public String login(@RequestBody ContactRequest1 contactRequest1) {
        // SQL query to check if both firstname and password are correct
        String sql = "SELECT COUNT(*) FROM url1 WHERE longurl=?";

        // Execute the query and get the count of matching users
        int count = jdbcTemplate.queryForObject(sql, new Object[]{contactRequest1.getLongurl()}, Integer.class);

        // If count > 0, return "Correct login", otherwise return "Wrong login"

        if (count > 0) {
            System.out.println("already exist");
            System.out.println(re.handleContact(contactRequest1.getLongurl()));
            return re.handleContact(contactRequest1.getLongurl());
        }
        else{
            System.out.println("new url");
            return re.handleContact(contactRequest1.getLongurl());
        }
    }
}

class ContactRequest1 {
    private String longurl;
  //  private String shorturl;
    // Getters and Setters

    public String getLongurl() {
        return longurl;
    }
    public void setLongurl(String longurl) {
        this.longurl = longurl;
    }
   /* public String getShorturl() {
        return shorturl;
    }

    public void setShorturl(String shorturl) {
        this.shorturl = shorturl;
    }
     */


}
