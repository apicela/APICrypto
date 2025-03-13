package com.apicela.apicrypto.models;

import com.apicela.apicrypto.models.dtos.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("users")
public class User {
    @Id
    private UUID id;
    private String name;
    private String lastName;
    private String mail;
    private String password;
    private boolean isDeleted = false;

    public User(UserDTO userDTO) {
        this.name = userDTO.name();
        this.lastName = userDTO.lastName();
        this.mail = userDTO.mail();
        this.password = "123";
    }

    public User() {
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
