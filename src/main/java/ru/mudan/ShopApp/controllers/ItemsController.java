package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mudan.ShopApp.models.Item;
import ru.mudan.ShopApp.services.ItemsService;
import ru.mudan.ShopApp.util.AuthContext;
import ru.mudan.ShopApp.util.ImageSaver;

import javax.validation.Valid;

@Controller
@RequestMapping("/items")
public class ItemsController {
    private final ItemsService itemsService;
    private final ImageSaver imageSaver;
    @Autowired
    public ItemsController(ItemsService itemsService, ImageSaver imageSaver) {
        this.itemsService = itemsService;
        this.imageSaver = imageSaver;
    }
    @GetMapping
    public String getAllItems(Model model){
        model.addAttribute("items",itemsService.findAll());
        if(AuthContext.getPersonDetailsFromContext().getPerson().getRole().equals("ROLE_ADMIN")){
            model.addAttribute("admin",true);
        }
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
    public String addItemAdminPost(@ModelAttribute("item") @Valid Item item, @RequestParam("file") MultipartFile file, BindingResult bindingResult){
        imageSaver.saveImage(file, file.getOriginalFilename());
        item.setPhoto(file.getOriginalFilename());
        itemsService.addItem(item);
        return "redirect:/items";
    }
}
