package ru.mudan.ShopApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mudan.ShopApp.models.Item;
import ru.mudan.ShopApp.repositories.ItemsRepository;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;
    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }
    @Transactional
    public List<Item>findAll(){
        return itemsRepository.findAll();
    }
}
