package com.example.fxservice.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<ConversionEntity> conversions;

    public UserEntity() {
    }

    public UserEntity(Integer id, String username, String password, List<ConversionEntity> conversions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.conversions = conversions;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ConversionEntity> getConversions() {
        return this.conversions;
    }

    public void setConversions(List<ConversionEntity> conversions) {
        this.conversions = conversions;
    }
}
