package com.hindustantimes.SubscriptionPortal.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class CaseType {
    private String id;
    private String name;
    private List<CaseSubtype> subtype;
}

