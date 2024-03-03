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

    public void deleteById(int id) {
        peopleRepository.deleteById(id);
    }
    public List<Person> findAllByIdAndRole(String role) {
        return peopleRepository.findByRole(role);
    }

    public void addPerson(Person person) {
        peopleRepository.save(person);
    }

    public Optional<Person> findById(int id) {
        return peopleRepository.findById(id);
    }

    public void editPerson(Person person) {
        peopleRepository.save(person);
    }

    public boolean checkIsPeeson(int id) {
        return !peopleRepository.findById(id).isEmpty();
    }
}
