package com.epam.sl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.db.AdminAndUserDao;
import com.epam.entity.AdminAndUser;

@Service
public class AdminAndUserValidationService {
	@Autowired
	AdminAndUserDao au;

	public boolean validateAdminAndUser(AdminAndUser role) {
		boolean result = false;
		if (au.findMatchingUser(role.getUserType(), role.getUserName(), role.getPassword()) != null) {
			result = true;
		}
		return result;
	}

	public boolean userSignUp(AdminAndUser user) {
		boolean result = false;
		if (au.checkExistingUserName(user.getUserName()) == null) {
			au.saveUsers(user);
			result = true;
		}
		return result;
	}
}
