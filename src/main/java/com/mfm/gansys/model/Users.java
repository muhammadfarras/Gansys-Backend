package com.mfm.gansys.model;


import jakarta.persistence.*;

@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String first_name;

    @Column(length = 30)
    private String last_name;

    @Column(nullable = false)
    private Long isEnable;

    @ManyToOne
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Users setEmail(String email) {
        this.email = email;
        return this;
    }

    public Users() {
    }

    public String getPassword() {
        return password;
    }

    public Users setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirst_name() {
        return first_name;
    }

    public Users setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public String getLast_name() {
        return last_name;
    }

    public Users setLast_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public Long getIsEnable() {
        return isEnable;
    }

    public Users setIsEnable(Long isEnable) {
        this.isEnable = isEnable;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public Users setRole(Role role) {
        this.role = role;
        return this;
    }
}
