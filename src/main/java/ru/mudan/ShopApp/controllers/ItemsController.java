package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mudan.ShopApp.models.Item;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.services.ItemsService;
import ru.mudan.ShopApp.util.ImageSaver;

import javax.validation.Valid;

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
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/add")
    public String addItemAdmin(@ModelAttribute("item") Item item){
        return "views/items/add";
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping
    public String addItemAdminPost(@ModelAttribute("item") @Valid Item item, BindingResult bindingResult){
        //TODO Validation
        System.out.println(item.getPhoto());
        String imageUrl = item.getPhoto(); // URL вашего изображения
        String folderPath = "D:\\NewIdeaApps\\ShopApp\\src\\main\\resources\\static\\images"; // Путь к папке для сохранения

        ImageSaver imageSaver = new ImageSaver();
        imageSaver.saveImageToFolder(imageUrl, folderPath);
        //itemsService.addItem(item);
        return "views/items/add";
    }
}
