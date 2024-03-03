package ru.mudan.ShopApp.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.mudan.ShopApp.security.PersonDetails;

public class AuthContext {
    public static PersonDetails getPersonDetailsFromContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (PersonDetails) authentication.getPrincipal();
    }
}
