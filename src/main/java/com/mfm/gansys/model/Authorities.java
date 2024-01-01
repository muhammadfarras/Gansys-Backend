package com.mfm.gansys.model;

import jakarta.persistence.*;


@Entity
@Table(name = "authorities")
public class Authorities {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "authority_name")
    @Enumerated(EnumType.STRING)
    private EnumAuthorities name;


    public Authorities() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumAuthorities getName() {
        return name;
    }

    public void setName(EnumAuthorities name) {
        this.name = name;
    }
}
