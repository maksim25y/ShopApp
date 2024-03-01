package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.services.PeopleService;

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

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id")int id){
        peopleService.deleteById(id);
        return "redirect:/admin";
    }
}
