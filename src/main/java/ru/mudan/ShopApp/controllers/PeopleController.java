package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.security.PersonDetails;
import ru.mudan.ShopApp.services.EmailSenderService;
import ru.mudan.ShopApp.services.PeopleService;
import ru.mudan.ShopApp.services.RegistrationService;
import ru.mudan.ShopApp.services.SessionService;
import ru.mudan.ShopApp.util.AuthContext;
import ru.mudan.ShopApp.util.PersonValidator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final RegistrationService registrationService;
    private final SessionService sessionService;
    private final PersonValidator personValidator;
    @Autowired
    private EmailSenderService senderService;

    @Autowired
    public PeopleController(PeopleService peopleService, RegistrationService registrationService, SessionService sessionService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.registrationService = registrationService;
        this.sessionService = sessionService;
        this.personValidator = personValidator;
    }
    //Добавление пользвателя в БД
    @PostMapping
    public String addUser(@ModelAttribute("person")@Valid Person person
    ,BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())return "views/auth/registration";
        peopleService.addPerson(person);
        return "redirect:/";
    }
    //Получение информации о пользователе со стороны пользователя
    @GetMapping("/{id}")
    public String getPerson(@PathVariable("id")int id, Model model,HttpSession session){
        PersonDetails personDetails = AuthContext.getPersonDetailsFromContext();
        if(personDetails!=null){
            if(personDetails.getPerson().getId()==id){
                Optional<Person> person = peopleService.findById(id);
                if(person.isEmpty())return "error";
                model.addAttribute("person",person.get());
                model.addAttribute("items",person.get().getItems());
                if(session.getAttribute("code")!=null){
                    model.addAttribute("wait",true);
                }
            }else {
                return "error";
            }
        }else{
            return "error";
        }
        return "views/people/show";
    }
    //Поулучение корзины пользователя
    @GetMapping("/{id}/basket")
    public String getUserBasket(@PathVariable("id")int id,Model model){
        model.addAttribute("items",peopleService.getAllItemsByPersonId(id));
        return "views/people/basket";
    }
    //Редактирование пользователя со стороны пользователя
    @GetMapping("/{id}/edit")
    public String getPageEditPerson(@PathVariable("id")int id, Model model){
        PersonDetails personDetails = AuthContext.getPersonDetailsFromContext();
        boolean good = false;
        if(personDetails!=null){
            if(personDetails.getPerson().getId()==id){
                good=true;
                model.addAttribute("person",personDetails.getPerson().getRole());
            }
        }
        if(!good){
            return "error";
        }
        return "views/people/show";
    }
    //Запрос на обновление из аккаунта пользователя
    @PatchMapping("/{id}/edit")
    public String updatePerson(@PathVariable("id")int id,
                               @ModelAttribute("person")@Valid Person person,
                               BindingResult bindingResult){
        PersonDetails personDetails = AuthContext.getPersonDetailsFromContext();
        if(personDetails!=null){
            personValidator.validate(person,bindingResult);
            if(personDetails.getPerson().getId()==id&&!bindingResult.hasErrors()){
                person.setId(id);
                registrationService.register(person,personDetails.getPerson().getRole().equals("ROLE_ADMIN"));
            }else {
                return "views/people/show";
            }
        }else {
            return "views/people/show";
        }
        return "redirect:/";
    }
    //Получение информации о пользователе у админа
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String userInfoFromAdmin(Model model, @PathVariable("id")int id) {
        model.addAttribute("person",peopleService.findById(id).get());
        return "views/other/user";
    }
    //Редактирование пользователя - со стороны админа
    @PostMapping("/admin/{id}/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editUserFromAdmin(@ModelAttribute("person")@Valid Person person,
                                    BindingResult bindingResult,
                                    @PathVariable("id")int id) {
        person.setId(id);
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors())return "views/other/user";
        registrationService.register(person);
        return "redirect:/admin";
    }
    //Удаление пользователя - доступно только админу
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
    @PostMapping("/{id}/email")
    public String sendEmailCode(@PathVariable("id")int id, Model model, HttpSession session){
        Optional<Person> personOptional = peopleService.findById(id);
        if(!personOptional.isEmpty()){
            Person person = personOptional.get();
            String code = UUID.randomUUID().toString();
            senderService.sendSimpleEmail(person.getEmail(), "Подтверждение почты", code);
            //Отправляем код на почту
            session.setAttribute("code",code);
            session.setAttribute("time",System.currentTimeMillis());
        }
        return String.format("redirect:/people/%s",id);
    }
    @PostMapping("/{id}/email/code")
    public String checkInputCode(@PathVariable("id")int id, @RequestParam("inputCode")String inputCode, HttpSession session){
        String sessionCode = (String) session.getAttribute("code");
        if(sessionCode.equals(inputCode)){
            peopleService.editEmailToGood(id);
        }
        session.removeAttribute("code");
        return String.format("redirect:/people/%s",id);
    }
}
