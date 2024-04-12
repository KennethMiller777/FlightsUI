package com.UI.ProjectUI;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import com.UI.ProjectUI.Models.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //what pages are allowed to anonymous users
		http
			.authorizeHttpRequests((requests) -> requests
			.requestMatchers("/Register").permitAll()			
			.requestMatchers("/CreateUser/{username}/{password}").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

	@Bean
	static public UserDetailsService userDetailsService() {
        String URL = String.format("http://localhost:4000/API/Users/getUsers"); //api endpoint to get users from api
        RestTemplate restTemplate = new RestTemplate();
        UserList result = restTemplate.getForObject(URL, UserList.class); //grab list users object from api

        Collection<UserDetails> userdetails = new ArrayList<UserDetails>();

        for (com.UI.ProjectUI.Models.User user : result.getUsers()) { //turn userlist into acceptable users to login
            UserDetails addUser =
			 User.withDefaultPasswordEncoder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles("USER")
				.build();

                userdetails.add(addUser);
        }

		return new InMemoryUserDetailsManager(userdetails);
	}
}