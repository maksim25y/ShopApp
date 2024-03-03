package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.security.PersonDetails;
import ru.mudan.ShopApp.services.PeopleService;
import ru.mudan.ShopApp.util.AuthContext;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
    @PostMapping
    public String addUser(@ModelAttribute("person")@Valid Person person){
        peopleService.addPerson(person);
        return "redirect:/";
    }
    @GetMapping("/{id}")
    public String getPerson(@PathVariable("id")int id, Model model){
        model.addAttribute("person",AuthContext.getPersonDetailsFromContext().getPerson());

        return "views/people/show";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id")int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        model.addAttribute("person",personDetails.getPerson().getRole());

        return "views/people/show";
    }
    @GetMapping("/admin/{id}")
    public String userInfoFromAdmin(Model model, @PathVariable("id")int id) {
        model.addAttribute("person",peopleService.findById(id).get());
        return "views/other/user";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id")int id){
        peopleService.deleteById(id);
        return "redirect:/";
    }
}
