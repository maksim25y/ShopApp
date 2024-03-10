package ru.mudan.ShopApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty
    @Size(min = 2, message = "Название товара должно содержать минимум 2 символа")
    private String name;
    @Column(name = "description")
    @NotEmpty
    @Size(min = 2, message = "Описание должно содержать минимум 2 символа")
    private String description;
    @Column(name = "photo")
    private String photo;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private Person person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Item(int id, String name, String description, String photo, Person person) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.person = person;
    }

    public Item() {
    }
}
