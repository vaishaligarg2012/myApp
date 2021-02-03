package com.hindustantimes.SubscriptionPortal.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseCreateRequest {

    @JsonProperty("new_address")
    private Address address;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("subtype")
    private String subtype;
    @JsonProperty("publication")
    private String publication;
    @JsonProperty("description")
    private String description;
    @JsonProperty("type")
    private String type;
    @JsonProperty("source")
    private String source;

}
