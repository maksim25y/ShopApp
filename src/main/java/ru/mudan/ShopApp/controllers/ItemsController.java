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
import ru.mudan.ShopApp.services.ItemsService;
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
    @Value("${images.path}")
    private String imagesPath;
    @Autowired
    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
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
    public String addItemAdminPost(@ModelAttribute("item") @Valid Item item, @RequestParam("file") MultipartFile file, BindingResult bindingResult) throws IOException {

        if (file != null && !file.isEmpty()) {
            File uploadDir = new File(imagesPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "_" + file.getOriginalFilename();
            File uploadedFile = new File(uploadDir.getAbsolutePath(), resultFilename);
            FileUtils.writeByteArrayToFile(uploadedFile, file.getBytes());
            item.setPhoto(resultFilename);
        }
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
        if(AuthContext.getPersonDetailsFromContext().getPerson().getRole().equals("ROLE_ADMIN")){
            model.addAttribute("admin",true);
        }
        return "views/items/show";
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteItemById(@PathVariable("id")int id){
        Item item = itemsService.findById(id).get();
        String photoPath = item.getPhoto();
        // Удаление фотографии из директории
        if (photoPath != null && !photoPath.isEmpty()) {
            File photoFile = new File(imagesPath + "/" + photoPath);
            if (photoFile.exists()) {
                photoFile.delete();
            }
        }
        itemsService.deleteById(id);
        return "redirect:/items";
    }

}
