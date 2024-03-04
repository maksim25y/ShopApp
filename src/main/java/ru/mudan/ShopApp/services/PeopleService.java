package ru.mudan.ShopApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    //Удаление пользователя по id
    public void deleteById(int id) {
        peopleRepository.deleteById(id);
    }
    public List<Person> findAllByIdAndRole(String role) {
        return peopleRepository.findByRole(role);
    }
    //Добавление пользователя в БД
    public void addPerson(Person person) {
        peopleRepository.save(person);
    }
    //Поиск пользователя по id
    public Optional<Person> findById(int id) {
        return peopleRepository.findById(id);
    }
    //Обновление пользователя в БД
    public void editPerson(Person person) {
        peopleRepository.save(person);
    }
    //Проверка - пользователь с таким id есть в бд
    public boolean checkIsPerson(int id) {
        return !peopleRepository.findById(id).isEmpty();
    }

    public Optional<Person> findByUserName(String username) {
        return peopleRepository.findByUsername(username);
    }
}
