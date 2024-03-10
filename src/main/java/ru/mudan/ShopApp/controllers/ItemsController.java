package ru.mudan.ShopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mudan.ShopApp.models.Item;
import ru.mudan.ShopApp.models.Person;
import ru.mudan.ShopApp.services.ItemsService;
import ru.mudan.ShopApp.services.PeopleService;
import ru.mudan.ShopApp.util.AuthContext;
import org.apache.commons.io.FileUtils;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/items")
public class ItemsController {
    private final ItemsService itemsService;
    private final PeopleService peopleService;
    @Value("${images.path}")
    private String imagesPath;
    @Autowired
    public ItemsController(ItemsService itemsService, PeopleService peopleService, PeopleService peopleService1) {
        this.itemsService = itemsService;
        this.peopleService = peopleService1;
    }
    @GetMapping
    public String getAllItems(Model model){
        model.addAttribute("items",itemsService.findAllDontBookedItems());
        if(AuthContext.getPersonDetailsFromContext().getPerson().getRole().equals("ROLE_ADMIN")){
            model.addAttribute("admin",true);
        }
        return "views/items/index";
    }
    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addItemAdmin(@ModelAttribute("item") Item item){
        return "views/items/add";
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addItemAdminPost(@ModelAttribute("item") @Valid Item item, BindingResult bindingResult,@RequestParam("file") MultipartFile file) {
        if(bindingResult.hasErrors())return "views/items/add";
        addPhoto(item, file);
        itemsService.addItem(item);
        return "redirect:/items";
    }
    @GetMapping("/{id}")
    public String getItemById(@PathVariable("id")int id,Model model){
        Optional<Item>item = itemsService.findById(id);
        if(item.isEmpty()){
            return "error";
        }
        model.addAttribute("item",item.get());
        Person person = item.get().getPerson();
        if(person!=null){
            model.addAttribute("owner",person.getUsername());
        }
        if(AuthContext.getPersonDetailsFromContext().getPerson().getRole().equals("ROLE_ADMIN")){
            model.addAttribute("admin",true);
        }
        return "views/items/show";
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteItemById(@PathVariable("id")int id){
        deletePhoto(id);
        itemsService.deleteById(id);
        return "redirect:/items";
    }
    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editItem(@PathVariable("id")int id,Model model){
        Item item = itemsService.findById(id).get();
        model.addAttribute("item",item);
        return "views/items/edit";
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateItem(@PathVariable("id") int id,
                             @ModelAttribute("item") @Valid Item item,
                             BindingResult bindingResult,
                             @RequestParam("file") MultipartFile file) {
        if(bindingResult.hasErrors())return "views/items/edit";
        deletePhoto(id);
        addPhoto(item, file);
        itemsService.updateItem(item,id);

        return "redirect:/items";
    }
    @PostMapping("/{id}/booking")
    public String bookingOfItem(@PathVariable("id")int id){
        Item item = itemsService.findById(id).get();
        Person person = peopleService.findById(AuthContext.getPersonDetailsFromContext().getPerson().getId()).get();
        peopleService.addItem(AuthContext.getPersonDetailsFromContext().getPerson().getId(),item);
        return "redirect:/items";
    }
    @DeleteMapping("/{id}/booking")
    public String cancellationOfBooking(@PathVariable("id")int id){
        Item item = itemsService.findById(id).get();
        Person person = peopleService.findById(AuthContext.getPersonDetailsFromContext().getPerson().getId()).get();
        peopleService.removeItem(AuthContext.getPersonDetailsFromContext().getPerson().getId(),item);
        return "redirect:/items";
    }

    private void deletePhoto(@PathVariable("id") int id) {
        Item itemFromDb = itemsService.findById(id).get();
        String photoPath = itemFromDb.getPhoto();

        if (photoPath != null && !photoPath.isEmpty()) {
            File photoFile = new File(imagesPath + "/" + photoPath);
            if (photoFile.exists()) {
                photoFile.delete();
            }
        }
    }

    private void addPhoto(@ModelAttribute("item") @Valid Item item, @RequestParam("file") MultipartFile file){
        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(imagesPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "_" + file.getOriginalFilename();
            File uploadedFile = new File(uploadDir.getAbsolutePath(), resultFilename);
            try {
                FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
            } catch (IOException e) {
            }
            item.setPhoto(resultFilename);
        }
    }

}
