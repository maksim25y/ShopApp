package ru.mudan.ShopApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mudan.ShopApp.models.Item;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.repositories.ItemsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> findAll() {
        return itemsRepository.findAll();
    }
    public List<Item>findAllDontBookedItems(){
        return itemsRepository.findAllByPersonIsNull();
    };

    @Transactional
    public void addItem(Item item) {
        itemsRepository.save(item);
    }

    public Optional<Item> findById(int id) {
        return itemsRepository.findById(id);
    }
    @Transactional
    public void deleteById(int id) {
        itemsRepository.deleteById(id);
    }
    @Transactional
    public void updateItem(Item item,int id) {
        item.setId(id);
        itemsRepository.save(item);
    }
}
