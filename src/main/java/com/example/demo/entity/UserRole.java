package com.example.demo.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole implements java.io.Serializable{
    @EmbeddedId
    private SocialProfileId id;
}
