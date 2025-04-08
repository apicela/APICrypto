package com.apicela.apicrypto.models;

import com.apicela.apicrypto.models.requests.RegisterUserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table("users")
@Getter
@Setter
public class UserModel implements UserDetails {
    @Id
    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private boolean isDeleted = false;
    private List<String> roles = new ArrayList<>();

    public UserModel(RegisterUserDTO userDTO) {
        this.name = userDTO.name();
        this.lastName = userDTO.lastName();
        this.email = userDTO.email();
        this.password = new BCryptPasswordEncoder().encode(userDTO.password());
        this.roles.add(UserRole.ROLE_DEFAULT.name());
    }

    public UserModel() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
