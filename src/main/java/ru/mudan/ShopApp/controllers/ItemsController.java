package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mudan.ShopApp.services.ItemsService;

@Controller
@RequestMapping("/items")
public class ItemsController {
    private final ItemsService itemsService;
    @Autowired
    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }
    @GetMapping
    public String getAllItems(Model model){
        model.addAttribute("items",itemsService.findAll());
        return "views/items/index";
    }
}
