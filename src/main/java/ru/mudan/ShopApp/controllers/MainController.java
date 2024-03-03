package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.security.PersonDetails;
import ru.mudan.ShopApp.services.AdminService;
import ru.mudan.ShopApp.services.PeopleService;
import ru.mudan.ShopApp.util.AuthContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {
    private final AdminService adminService;
    private final PeopleService peopleService;

    @Autowired
    public MainController(AdminService adminService, PeopleService peopleService) {
        this.adminService = adminService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String showUserInfo(Model model, HttpServletRequest httpServletRequest) {
        PersonDetails person = AuthContext.getPersonDetailsFromContext();
        if(person!=null){
            if(peopleService.checkIsPeeson(person.getPerson().getId())){
                model.addAttribute("person",person.getPerson());
            }
        }
        return "views/index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("people",peopleService.findAllByIdAndRole("ROLE_USER"));
        return "views/other/admin";
    }
}
