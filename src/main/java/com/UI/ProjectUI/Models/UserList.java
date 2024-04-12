package com.UI.ProjectUI.Models;

import java.util.List;

public class UserList { //class to get list of users from api
	private List<User> UserList;

	public List<User> getUsers() {
		return UserList;
	}

	public void setUsers(List<User> UserList) {
		this.UserList = UserList;
	}
}
