package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mudan.ShopApp.services.PeopleService;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id")int id){
        peopleService.deleteById(id);
        return "redirect:/admin";
    }
}
