package com.epam;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.db.AdminAndUserDao;
import com.epam.entity.AdminAndUser;
import com.epam.sl.AdminAndUserValidationService;

@ExtendWith(MockitoExtension.class)
 class AdminAndUserValidationServiceTest {
    @Mock
    AdminAndUserDao adminAndUserDao;
    @InjectMocks
    AdminAndUserValidationService adminAndUserValidationService;
   
   AdminAndUser admin;
   AdminAndUser user;
        @BeforeEach
        public void setData()
       {
            admin=new AdminAndUser("admin","pinky","123");
            user=new AdminAndUser("user","sree","abc");
       }
    @Test
     void validateAdminTest()
    {
        Mockito.when( adminAndUserDao.findMatchingUser(admin.getUserType(), admin.getUserName(), admin.getPassword())).thenReturn(admin);
        boolean result =adminAndUserValidationService.validateAdminAndUser(admin);
        assertTrue(result);
    }
    @Test
     void validateAdminWithInvalidCredentials()
    {
        Mockito.when( adminAndUserDao.findMatchingUser(admin.getUserType(), admin.getUserName(), admin.getPassword())).thenReturn(null);
        boolean result =adminAndUserValidationService.validateAdminAndUser(admin);
        assertFalse(result);
    }
    @Test
     void validateAdminWithInvalidAdminData()
    {

        Mockito.when( adminAndUserDao.findMatchingUser(admin.getUserType(), admin.getUserName(), admin.getPassword())).thenReturn(null);
        boolean result =adminAndUserValidationService.validateAdminAndUser(admin);
        assertFalse(result);
    }
    @Test
     void userSignUpTest()
    {
        Mockito.when(adminAndUserDao.saveUsers(user)).thenReturn(user);
        boolean result =adminAndUserValidationService.userSignUp(user);
        assertTrue(result);
    }
    @Test
     void userSignInWithNotExistingUser()
    {
        AdminAndUser user=new AdminAndUser("user","pandu","1234");
        Mockito.when(adminAndUserDao.findMatchingUser(user.getUserType(), user.getUserName(), user.getPassword())).thenReturn(null);
        boolean result =adminAndUserValidationService.validateAdminAndUser(user);
        assertFalse(result);
    }
    @Test
     void userSignInWithExistingUser()
     {
        Mockito.when(adminAndUserDao.findMatchingUser(user.getUserType(), user.getUserName(), user.getPassword())).thenReturn(user);
        boolean result =adminAndUserValidationService.validateAdminAndUser(user);
        assertTrue(result);
    }




}
