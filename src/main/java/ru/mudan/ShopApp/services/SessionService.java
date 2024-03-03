package ru.mudan.ShopApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
    @Autowired
    private SessionRegistry sessionRegistry;

    public void logoutUser(String username) {
        List<Object> principals = sessionRegistry.getAllPrincipals();

        for (Object principal : principals) {
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);

                    for (SessionInformation session : sessions) {
                        session.expireNow(); // Разлогиниваем сессию пользователя
                    }
                }
            }
        }
    }

}