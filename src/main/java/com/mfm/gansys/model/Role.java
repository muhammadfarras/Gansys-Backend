package com.mfm.gansys.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "role_name")
})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false,length = 50)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(  name = "user_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id"))
    private Set<Authorities> authoritiesSet = new HashSet<>();


    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Authorities> getAuthoritiesSet() {
        return authoritiesSet;
    }

    public void setAuthoritiesSet(Set<Authorities> authoritiesSet) {
        this.authoritiesSet = authoritiesSet;
    }
}
