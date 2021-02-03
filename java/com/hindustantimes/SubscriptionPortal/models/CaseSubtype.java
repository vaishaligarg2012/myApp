package com.hindustantimes.SubscriptionPortal.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseSubtype {
    private String id;
    private String name;
    private String identifier;
    private Boolean isPublicationRequired;
}
