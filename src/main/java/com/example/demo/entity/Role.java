package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import vn.com.itechcorp.base.persistence.model.BaseSerialIDEntry;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role extends BaseSerialIDEntry {
    private String name;
    @ManyToMany(mappedBy = "roleList")
    private Collection<User> roleList;
}
