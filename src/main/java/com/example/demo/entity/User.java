package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import vn.com.itechcorp.base.persistence.model.AuditableSerialIDEntry;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class User extends AuditableSerialIDEntry {
    @Column(name = "fullName")
    private String fullName;

    @Column(name = "passWork")
    private String passWork;

    @Column(name = "name")
    private String name;


    public User(String fullName, String passWork, String name, List<Role> roleList) {
        this.fullName = fullName;
        this.passWork = passWork;
        this.name = name;
        this.roleList = roleList;
    }

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Collection<Role> roleList;

    @Transient
    private Collection<GrantedAuthority> listRole;

    public User() {
    }
}
