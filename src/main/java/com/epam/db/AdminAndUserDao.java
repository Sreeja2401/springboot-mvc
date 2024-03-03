package com.epam.db;

import com.epam.entity.AdminAndUser;

public interface AdminAndUserDao {
	public AdminAndUser findMatchingUser(String userType, String userName, String password);

	public AdminAndUser saveUsers(AdminAndUser t);

	public AdminAndUser checkExistingUserName(String userName);
}
