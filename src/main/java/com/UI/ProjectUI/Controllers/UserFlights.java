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
@RequestMapping("/UserFlights")
public class UserFlights {
    
    @GetMapping
    public ModelAndView index() { //return view
        ModelAndView mv = new ModelAndView();
        mv.setViewName("UserFlights");

        return mv;
    }

    @RequestMapping(value = "/LoadFlights", method = RequestMethod.GET, produces="application/json") //
    public @ResponseBody String LoadFlights() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        String BaseURL = String.format("http://localhost:4000/API/Users/getUser/%s", username);

        RestTemplate restTemplate = new RestTemplate();
        User objUser = restTemplate.getForObject(BaseURL, User.class);

        String returnstring = "";

        for (int userFlight : objUser.getFlights()) {
            String flightURL = String.format("http://localhost:4000/API/Flights/%s", userFlight);

            RestTemplate flightRestTemplate = new RestTemplate();
            String flightString = flightRestTemplate.getForObject(flightURL, String.class);

            

            returnstring += (returnstring.equals("") ? flightString : "," + flightString);
        }

        returnstring = "[" + returnstring + "]";

        return returnstring;
    }

    @RequestMapping(value = "/UnbookFlight/{FlightID}", method = RequestMethod.GET)
    public boolean BookFlight(@PathVariable int FlightID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        String URL = String.format("http://localhost:4000/API/Users/UnbookUserFlight/%s/%s", username, FlightID);

        RestTemplate restTemplate = new RestTemplate();
        User result = restTemplate.getForObject(URL, User.class);

        for (int userFlight : result.getFlights()) {
            if (userFlight == FlightID) {
                return false;
            }
        }

        return true;
    }
}