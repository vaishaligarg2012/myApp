package com.hindustantimes.SubscriptionPortal.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@Table(name = "users_customtoken")
public class CustomToken {

    @Id
    @Column(name = "primary_token")
    private String primaryToken;

    @Column(name = "secondary_token")
    private String secondaryToken;

}
