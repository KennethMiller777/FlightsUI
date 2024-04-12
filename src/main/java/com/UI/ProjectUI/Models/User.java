package com.UI.ProjectUI.Models;

import java.util.ArrayList;
import java.util.List;

public class User
{ //user class
	private int id;
	private String username;
	private String password;
	private List<Integer> flights;

	//region default constructors
	public User () {
		this.username = "";
		this.password = "";
		this.flights = new ArrayList<>();
	}

	public User (String username, String password) {
		this.username = username;
		this.password = password;
		this.flights = new ArrayList<>();
	}
	//endregion default constructors

	//region getter methods
	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public int getId()
	{
        return this.id;
    }

	public List<Integer> getFlights() 
	{
		return this.flights;
	}
	//endregion getter methods

	//region setter methods
	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

    public void setId(int id)
	{
        this.id = id;
    }

	public void setFlights(List<Integer> flights)
	{
		this.flights = flights;
	}
	//endregion setter methods
}
