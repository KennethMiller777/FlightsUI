package com.UI.ProjectUI.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.UI.ProjectUI.Models.User;

@RestController
@RequestMapping("/Search")
public class SearchController {
    
    @GetMapping
    public ModelAndView index() { //return view
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Search");

        return mv;
    }

    @RequestMapping(value = "/LoadFlights", method = RequestMethod.GET, produces="application/json") //grab flights from the api
    public @ResponseBody String LoadFlights() {
        String URL = "http://localhost:4000/API/Flights";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(URL, String.class);

        return result;
    }

    @RequestMapping(value = "/BookFlight/{FlightID}", method = RequestMethod.GET, produces="application/json")
    public boolean BookFlight(@PathVariable int FlightID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        String URL = String.format("http://localhost:4000/API/Users/BookUserFlight/%s/%s", username, FlightID);

        RestTemplate restTemplate = new RestTemplate();
        User result = restTemplate.getForObject(URL, User.class);

        for (int userFlight : result.getFlights()) {
            if (userFlight == (int)FlightID) {
                return true;
            }
        }

        return false;
    }
}
