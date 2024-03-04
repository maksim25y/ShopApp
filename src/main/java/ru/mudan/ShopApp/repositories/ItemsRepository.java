package ru.mudan.ShopApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mudan.ShopApp.models.Item;

@Repository
public interface ItemsRepository extends JpaRepository<Item,Integer> {
}
