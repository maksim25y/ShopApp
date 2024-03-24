package ru.mudan.ShopApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mudan.ShopApp.models.Item;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    //Удаление пользователя по id
    @Transactional
    public void deleteById(int id) {
        peopleRepository.deleteById(id);
    }
    public List<Person> findAllByIdAndRole(String role) {
        return peopleRepository.findByRole(role);
    }
    //Добавление пользователя в БД
    @Transactional
    public void addPerson(Person person) {
        peopleRepository.save(person);
    }
    //Поиск пользователя по id
    public Optional<Person> findById(int id) {
        return peopleRepository.findById(id);
    }
    @Transactional
    //Проверка - пользователь с таким id есть в бд
    public boolean checkIsPerson(int id) {
        return !peopleRepository.findById(id).isEmpty();
    }

    public Optional<Person> findByUserName(String username) {
        return peopleRepository.findByUsername(username);
    }
    @Transactional
    public void addItem(int id, Item item) {
        Person person = peopleRepository.getById(id);
        person.getItems().add(item);
        item.setPerson(person);
    }

    @Transactional
    public void removeItem(int id, Item item) {
        Person person = peopleRepository.getById(id);
        person.getItems().remove(item);
        item.setPerson(null);
    }
    public List<Item>getAllItemsByPersonId(int id){
        Person person = peopleRepository.findById(id).get();
        return person.getItems();
    }
    @Transactional
    public void editEmailToGood(int id) {
        Person person = peopleRepository.findById(id).get();
        person.setEmailActive(true);
        peopleRepository.save(person);
    }
}
