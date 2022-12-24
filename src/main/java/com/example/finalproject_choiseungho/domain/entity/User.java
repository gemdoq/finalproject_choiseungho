package com.example.finalproject_choiseungho.domain.entity;

import com.example.finalproject_choiseungho.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserDto toUserDto() {
        return new UserDto(this.id, this.userName, this.password, this.role);
    }
}