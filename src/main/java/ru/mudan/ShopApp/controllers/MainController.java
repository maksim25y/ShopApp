package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mudan.ShopApp.security.PersonDetails;
import ru.mudan.ShopApp.services.PeopleService;
import ru.mudan.ShopApp.util.AuthContext;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    private final PeopleService peopleService;
    @Autowired
    public MainController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
    //Главная страница - если юзер вошел в акк, то кнопка редактирования
    @GetMapping
    public String showUserInfo(Model model, HttpServletRequest httpServletRequest) {
        PersonDetails person = AuthContext.getPersonDetailsFromContext();
        if(person!=null){
            if(peopleService.checkIsPerson(person.getPerson().getId())){
                model.addAttribute("person",person.getPerson());
            }
        }
        return "views/index";
    }
    //Страница админка - можно удалять пользователей и редачить
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("people",peopleService.findAllByIdAndRole("ROLE_USER"));
        return "views/other/admin";
    }
}
