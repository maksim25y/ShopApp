package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mudan.ShopApp.security.PersonDetails;
import ru.mudan.ShopApp.services.AdminService;
import ru.mudan.ShopApp.services.PeopleService;

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
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("role",personDetails.getPerson().getRole());

        return "views/index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        adminService.doAdminStuff();
        model.addAttribute("people",peopleService.getAll());
        return "views/other/admin";
    }
}
