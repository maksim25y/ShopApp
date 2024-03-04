package ru.mudan.ShopApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.security.PersonDetails;
import ru.mudan.ShopApp.services.PeopleService;
import ru.mudan.ShopApp.services.PersonDetailsService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService, PeopleService peopleService) {
        this.personDetailsService = personDetailsService;
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        try {
            UserDetails personFromDB = personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; // все ок, пользователь не найден
        }
        Optional<Person>person1 = peopleService.findByUserName(person.getUsername());
        if(person1.get().getId()!=person.getId()){
            errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
        }
    }
}
