package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.security.PersonDetails;
import ru.mudan.ShopApp.services.PeopleService;
import ru.mudan.ShopApp.services.RegistrationService;
import ru.mudan.ShopApp.services.SessionService;
import ru.mudan.ShopApp.util.AuthContext;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final RegistrationService registrationService;
    private final SessionService sessionService;

    @Autowired
    public PeopleController(PeopleService peopleService, RegistrationService registrationService, SessionService sessionService) {
        this.peopleService = peopleService;
        this.registrationService = registrationService;
        this.sessionService = sessionService;
    }
    @PostMapping
    public String addUser(@ModelAttribute("person")@Valid Person person){
        peopleService.addPerson(person);
        return "redirect:/";
    }
    @GetMapping("/{id}")
    public String getPerson(@PathVariable("id")int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> person = peopleService.findById(id);
        if(person.isEmpty()){
            return "error";
        }else {
            if(person.get().getId()==id){
                PersonDetails newPrincipal = new PersonDetails(person.get());
                UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(newPrincipal, authentication.getCredentials(), authentication.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(newAuthentication);
                model.addAttribute("person",person.get());
            }else {
                return "error";
            }
        }
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String userInfoFromAdmin(Model model, @PathVariable("id")int id) {
        model.addAttribute("person",peopleService.findById(id).get());
        return "views/other/user";
    }
    @PostMapping("/admin/{id}/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editUserFromAdmin(@ModelAttribute("person")@Valid Person person,
                                    @PathVariable("id")int id, BindingResult bindingResult) {
        person.setId(id);
        registrationService.register(person);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePerson(@PathVariable("id")int id){
        Optional<Person> person = peopleService.findById(id);
        if(!person.isEmpty()){
            sessionService.logoutUser(person.get().getUsername());
            peopleService.deleteById(id);
        }
        return "redirect:/";
    }
}
