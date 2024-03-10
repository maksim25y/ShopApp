package ru.mudan.ShopApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mudan.ShopApp.models.Item;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item,Integer> {
    List<Item> findAllByPersonIsNull();
}
