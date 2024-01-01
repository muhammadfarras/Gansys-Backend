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
}
