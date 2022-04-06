package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SocialProfileId implements java.io.Serializable {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "role_id")
    private long roleId;
}
