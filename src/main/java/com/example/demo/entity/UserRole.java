package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
@Getter
@Setter
public class UserRole implements java.io.Serializable {
    @EmbeddedId
    private SocialProfileId id;
}
